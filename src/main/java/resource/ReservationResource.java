package resource;

import client.print.bottomup.PrintServiceAdapter;
import core.mapper.ReservationMapper;
import core.query.WhereBuilder;
import core.resource.AbstractFacade;
import core.utils.RandomString;
import model.Flight;
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
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

@Path(ResourceType.RESERVATION)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class ReservationResource extends AbstractFacade<Reservation> {

    public ReservationResource() {
        super(Reservation.class);
    }

    @Context
    UriInfo uriInfo;

    @Inject
    private EntityManager em;

    @GET
    @Path("/")
    public Response getReservations(@HeaderParam("X-Order") String order,
                                    @HeaderParam("X-Base") Integer base,
                                    @HeaderParam("X-Offset") Integer offset,
                                    @HeaderParam("X-Filter") String filter,
                                    @PathParam("flightId") Long flightId) {

        Flight flight = em.find(Flight.class, flightId);
        testExistence(flight);

        Collection<Reservation> reservations = super.findAll(order, base,
                offset, createWhere(flight));

        GenericEntity<Collection<Reservation>> entity = new GenericEntity<Collection<Reservation>>(
                reservations) {
        };
        return Response.ok().header("X-Count-records", super.count(createWhere(flight)))
                .entity(entity).build();
    }

    @GET
    @Path("/{id}")
    public Response getReservation(@HeaderParam("X-Password") String password,
                                   @PathParam("id") Long id) {
        Reservation item = super.find(id);
        testAuthorization(item, password);
        return Response.status(Status.OK).entity(item).build();
    }

    @GET
    @Path("/{id}/print")
    @Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_OCTET_STREAM})
    public InputStream print(@HeaderParam("X-Password") String password,
                                   @PathParam("id") Long id) {
        Reservation item = super.find(id);
        testAuthorization(item, password);
        InputStream is = null;
        PrintServiceAdapter ps = new PrintServiceAdapter();
        ps.setServiceUrl("http://127.0.0.1:8080/osmanvlastic/PrintService?Wsdl");
        is = ps.getFileIS(item);

        return is;
    }

    @POST
    @Path("/")
    @RolesAllowed({"admin"})
    public Response add(@PathParam("flightId") Long flightId,
                        ReservationMapper mapper) {
        Flight flight = em.find(Flight.class, flightId, LockModeType.PESSIMISTIC_READ);
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
        reservation.setCreated(new Date());
        reservation.setPassword(RandomString.generateRandom(32));
        reservation.setState(StateChoices.NEW);
        super.create(reservation);
        return Response.status(Status.CREATED)
                .header("Locale", reservation.getUrl())
                .header("X-Password", reservation.getPassword()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response edit(@HeaderParam("X-Password") String password,
                         @PathParam("flightId") Long flightId, @PathParam("id") Long id,
                         ReservationMapper mapper) {
        Reservation reservation = super.find(id);
        Flight flight = em.find(Flight.class, flightId, LockModeType.PESSIMISTIC_READ);
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
    @RolesAllowed({"admin"})
    public Response delete(@HeaderParam("X-Password") String password,
                           @PathParam("id") Long id) {
        Reservation reservation = super.find(id);
        testAuthorization(reservation, password);
        if (reservation.getState().equals(StateChoices.NEW)) {
            super.remove(reservation);
            return Response.status(Status.NO_CONTENT).build();
        }

        return Response.status(Status.BAD_REQUEST).build();

    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private void testAuthorization(Reservation reservation, String password) {
        testExistence(reservation);
        if (!reservation.getPassword().equals(password)) {
            throw new UnauthorizedException();
        }
    }

    private WhereBuilder<Reservation> createWhere(final Flight flight) {
        return new WhereBuilder<Reservation>() {

            public Predicate build(CriteriaQuery<Reservation> cq,
                                   CriteriaBuilder cb, Root<Reservation> root) {
                return cb.equal(root.get("flight"), flight);
            }
        };

    }
}
