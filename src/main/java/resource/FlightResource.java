package resource;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
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

import core.mapper.FlightMapper;
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
	
	@EJB
	private ReservationResource reservation;
	
	//@Inject
	//private UserTransaction userTransaction;

	@GET
	@Path("/")
	public Response getFlights(@HeaderParam("X-Order") String order,
			@HeaderParam("X-Base") Integer base,
			@HeaderParam("X-Offset") Integer offset,
			@HeaderParam("X-Filter") String filter) {
		Collection<Flight> flight = super.findAll(order, base, offset);
		GenericEntity<Collection<Flight>> entity = new GenericEntity<Collection<Flight>>(flight) {};  
		return Response.ok().header("X-Count-records", super.count())
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
	public Response add(FlightMapper mapper){
		Flight flight = mapper.getValue();
		super.create(flight);
		return Response.status(Status.CREATED)
				.header("Locale", flight.getUrl()).build();
	}
	
	@PUT
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response edit(@PathParam("id") Long id, FlightMapper mapper) {
		Flight flight = mapper.getValue();
		flight.setId(id);
		super.edit(flight);
		flight.loadUrl();
		return Response.status(Status.NO_CONTENT)
				.header("Locale:", flight.getUrl()).build();
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
	
	@Path("/{flightId}/reservation")
	public ReservationResource getReservationResource(){
		return reservation;
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
