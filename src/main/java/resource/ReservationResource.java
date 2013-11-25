package resource;

import client.print.bottomup.PrintServiceAdapter;
import core.mapper.ReservationMapper;
import core.resource.AbstractFacade;
import core.validation.Validator;
import model.Flight;
import model.Payment;
import model.Reservation;
import model.StateChoices;
import org.jboss.resteasy.spi.UnauthorizedException;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.io.InputStream;
import java.util.Collection;

@Path(ResourceType.RESERVATION)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ReservationResource extends AbstractFacade<Reservation>
{

    @Context
    UriInfo uriInfo;

    @Inject
    private EntityManager em;

    @Inject
    private Validator validator;

    public ReservationResource()
    {
        super(Reservation.class);
    }

    @GET
    @Path("/")
    @RolesAllowed({"admin", "manager"})
    public Response getReservations(@HeaderParam("X-Order") String order,
                                    @HeaderParam("X-Base") Integer base,
                                    @HeaderParam("X-Offset") Integer offset)
    {


        Collection<Reservation> reservations = super.findAll(order, base, offset);

        GenericEntity<Collection<Reservation>> entity = new GenericEntity<Collection<Reservation>>(
                reservations)
        {
        };
        return Response.ok().header("X-Count-records", super.count())
                .entity(entity).build();
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@HeaderParam("X-Password") String password,
                                   @PathParam("id") Long id)
    {
        Reservation item = super.find(id);
        testAuthorization(item, password);
        return Response.status(Status.OK).entity(item).build();
    }

    @PUT
    @Path("/{id}/cancel")
    public Response cancel(@HeaderParam("X-Password") String password, @PathParam("id") Long id)
    {
        Reservation reservation = super.find(id);
        testAuthorization(reservation, password);
        testState(reservation, StateChoices.NEW);

        reservation.setState(StateChoices.CANCELED);
        super.edit(reservation);

        return Response.status(Status.NO_CONTENT).build();
    }

    @POST
    @Path("/{id}/payment")
    public Response payment(@HeaderParam("X-Password") String password, @PathParam("id") Long id, Payment payment)
    {

        Reservation reservation = super.find(id);
        testAuthorization(reservation, password);
        testState(reservation, StateChoices.NEW);

        validator.validate(payment);

//        if (!payment.getAccountNumber().matches("^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$") || !payment.getCode().matches("^[0-9]{3}$")) {
//            new org.jboss.resteasy.spi.BadRequestException("Wrong credit card information.");
//        }

        reservation.setState(StateChoices.PAID);
        super.edit(reservation);

        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/{id}/print")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_OCTET_STREAM})
    public InputStream print(@HeaderParam("X-Password") String password,
                             @PathParam("id") Long id)
    {
        Reservation item = super.find(id);
        testAuthorization(item, password);
        PrintServiceAdapter ps = new PrintServiceAdapter();
        ps.setServiceUrl("http://127.0.0.1:8080/osmanvlastic/PrintService?Wsdl");
        InputStream is = ps.getFileIS(item);

        return is;
    }

    @POST
    @Path("/")
    public Response add(ReservationMapper mapper)
    {
        Flight flight = em.find(Flight.class, mapper.getFlight(), LockModeType.PESSIMISTIC_READ);
        int count = 0;
        for (Reservation r : flight.getReservations()) {
            count += r.getSeats();
        }
        if (flight.getSeats() < count + mapper.getSeats()) {
            return Response.status(Status.BAD_REQUEST).build();
        }

        Reservation reservation = new Reservation();
        mapper.map(reservation);
        reservation.setFlight(flight);
        super.create(reservation);
        return Response.status(Status.CREATED)
                .header("Locale", reservation.getUrl())
                .header("X-Password", reservation.getPassword()).build();
    }

    @PUT
    @Path("/{id}")
    public Response edit(@HeaderParam("X-Password") String password,
                         @PathParam("id") Long id,
                         ReservationMapper mapper)
    {
        Reservation reservation = super.find(id);
        Flight flight = em.find(Flight.class, mapper.getFlight(), LockModeType.PESSIMISTIC_READ);
        int count = 0;
        for (Reservation r : flight.getReservations()) {
            if (r.getId().equals(id)) continue;
            count += r.getSeats();
        }
        if (flight.getSeats() < count + mapper.getSeats()) {
            return Response.status(Status.BAD_REQUEST).build();
        }
        testAuthorization(reservation, password);
        mapper.map(reservation);
        super.edit(reservation);
        return Response.status(Status.NO_CONTENT)
                .header("Locale", reservation.getUrl()).build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@HeaderParam("X-Password") String password,
                           @PathParam("id") Long id)
    {
        Reservation reservation = super.find(id);
        testAuthorization(reservation, password);
        testState(reservation, StateChoices.NEW, StateChoices.CANCELED);

        super.remove(reservation);
        return Response.status(Status.NO_CONTENT).build();

    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    private void testAuthorization(Reservation reservation, String password)
    {
        testExistence(reservation);
        if (!reservation.getPassword().equals(password)) {
            throw new UnauthorizedException();
        }
    }

    private void testState(Reservation reservation, StateChoices... states)
    {
        boolean exist = false;

        for (StateChoices state : states) {
            if (reservation.getState() == state) {
                exist = true;
            }
        }

        if (!exist) {
            throw new org.jboss.resteasy.spi.BadRequestException(String.format("Reservation is %s.", reservation.getState()));
        }
    }
}
