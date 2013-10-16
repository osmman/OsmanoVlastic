package resource;

import java.net.URI;
import java.util.Collection;
import java.util.List;

import javax.annotation.security.RolesAllowed;
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
import javax.ws.rs.Consumes;
import javax.xml.bind.JAXBElement;

import core.mapper.FlightMapper;
import core.resource.AbstractFacade;
import model.Destination;
import model.Flight;

@Path("/flight")
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

	@Inject
	private UserTransaction userTransaction;

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
		super.create(mapper.getValue());
		return Response.ok().build();
	}
	
	@PUT
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response edit(@PathParam("id") Long id, FlightMapper mapper) {
		Flight flight = mapper.getValue();
		flight.setId(id);
		super.edit(flight);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response deleten(@PathParam("id") Long id) {
		super.remove(super.find(id));
		return Response.ok().build();
	}
	
	@Path("/{id}/reservation")
	public ReservationResource getReservationResource(@PathParam("id") Long id){
		return new ReservationResource(super.find(id));
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
