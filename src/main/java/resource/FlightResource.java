package resource;

import client.ClientException;
import client.flight.FlightDistanceClient;
import core.ejb.*;
import core.mapper.FlightMapper;
import core.query.WhereBuilder;
import core.resource.AbstractFacade;
import model.Flight;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.DatatypeConverter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path(ResourceType.FLIGHT)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class FlightResource extends AbstractFacade<Flight> {

    public FlightResource() {
        super(Flight.class);
    }

    @Context
    UriInfo uriInfo;

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

    @Inject
    private FlightDistanceClient flightDistanceClient;

    @GET
    @Path("/")
    public Response getFlights(@HeaderParam("X-Order") String order,
                               @HeaderParam("X-Base") Integer base,
                               @HeaderParam("X-Offset") Integer offset,
                               @HeaderParam("X-Filter") String filter) {
        Collection<Flight> flight = super.findAll(order, base, offset,
                createWhere(filter));
        GenericEntity<Collection<Flight>> entity = new GenericEntity<Collection<Flight>>(
                flight) {
        };
        return Response.ok()
                .header("X-Count-records", super.count(createWhere(filter)))
                .entity(entity).build();
    }

    @GET
    @Path("/{id}")
    public Flight getFlight(@PathParam("id") Long id) {
        return super.find(id);
    }

    @POST
    @Path("/")
    @RolesAllowed({"admin"})
    public Response add(FlightMapper mapper) throws SystemException {
        Flight flight = mapper.map(new Flight());

        super.create(flight);

        try {
            Double distance = flightDistanceClient.getDistance(flight.getFrom().getGeocode(), flight.getTo().getGeocode());
            flight.setDistance(distance.floatValue());
            flight.setPrice(distance.floatValue() * 10);

        } catch (ClientException e) {
            userTransaction.setRollbackOnly();
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.status(Status.CREATED)
                .header("Locale", flight.getUrl()).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response edit(@PathParam("id") Long id, FlightMapper mapper) throws SystemException {
        Flight flight = super.find(id);
        mapper.map(flight);
        super.edit(flight);

        try {
            Double distance = flightDistanceClient.getDistance(flight.getFrom().getGeocode(), flight.getTo().getGeocode());
            flight.setDistance(distance.floatValue());
            flight.setPrice(distance.floatValue() * 10);

        } catch (ClientException e) {
            userTransaction.setRollbackOnly();
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

        return Response.status(Status.NO_CONTENT)
                .header("Locale", flight.getUrl()).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response delete(@PathParam("id") Long id) {
        Flight item = super.find(id);
        if (item == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        super.remove(item);
        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @SuppressWarnings("restriction")
    private WhereBuilder<Flight> createWhere(final String filter) {
        if (filter == null || filter.isEmpty())
            return null;

        Pattern fromPattern = Pattern.compile("dateOfDepartureFrom=([^,]{1,25})");
        Pattern toPattern = Pattern.compile("dateOfDepartureTo=([^,]{1,25})");

        Matcher fromMatcher = fromPattern.matcher(filter);
        Matcher toMatcher = toPattern.matcher(filter);

        Date fromDate = null;
        if (fromMatcher.find()) {
            fromDate = DatatypeConverter.parseDateTime(fromMatcher.group(1)).getTime();
        }

        Date toDate = null;
        if (toMatcher.find()) {
            toDate = DatatypeConverter.parseDateTime(toMatcher.group(1)).getTime();
        }

        final Date from = fromDate;

        final Date to = toDate;

        WhereBuilder<Flight> whereBuilder = new WhereBuilder<Flight>() {

            public Predicate build(CriteriaQuery<Flight> cq,
                                   CriteriaBuilder cb, Root<Flight> root) {

                List<Predicate> predicates = new ArrayList<Predicate>();

                if (from != null) {
                    predicates.add(cb.greaterThanOrEqualTo(
                            root.<Date>get("dateOfDeparture"), from));
                }

                if (to != null) {
                    predicates.add(cb.lessThanOrEqualTo(
                            root.<Date>get("dateOfDeparture"), to));
                }

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        return whereBuilder;

    }

}
