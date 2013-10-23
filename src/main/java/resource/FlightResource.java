package resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Collection;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.Consumes;
import javax.xml.bind.DatatypeConverter;

import org.jboss.resteasy.spi.BadRequestException;

import core.mapper.FlightMapper;
import core.query.WhereBuilder;
import core.resource.AbstractFacade;
import model.Flight;

@Path(ResourceType.FLIGHT)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class FlightResource extends AbstractFacade<Flight> {

	public FlightResource() {
		super(Flight.class);
	}

	@Context
	UriInfo uriInfo;

	@Inject
	private EntityManager em;

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
	@RolesAllowed({ "admin" })
	public Response add(FlightMapper mapper) {
		Flight flight = mapper.map(new Flight());
		super.create(flight);
		return Response.status(Status.CREATED)
				.header("Locale", flight.getUrl()).build();
	}

	@PUT
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response edit(@PathParam("id") Long id, FlightMapper mapper) {
		Flight flight = super.find(id);
		mapper.map(flight);
		super.edit(flight);
		return Response.status(Status.NO_CONTENT)
				.header("Locale", flight.getUrl()).build();
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response deleten(@PathParam("id") Long id) {
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
		
		Pattern fromPatern = Pattern.compile("dateOfDepartureFrom=([^,]{1,25})");
		Pattern toPatern = Pattern.compile("dateOfDepartureTo=([^,]{1,25})");

		Matcher fromMatcher = fromPatern.matcher(filter);
		Matcher toMatcher = toPatern.matcher(filter);

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
							root.<Date> get("dateOfDeparture"), from));
				}

				if (to != null) {
					predicates.add(cb.lessThanOrEqualTo(
							root.<Date> get("dateOfDeparture"), to));
				}

				return cb.and(predicates.toArray(new Predicate[] {}));
			}
		};

		return whereBuilder;

	}

}
