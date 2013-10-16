package resource;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
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

import model.Flight;
import model.Reservation;
import core.resource.AbstractFacade;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
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
		/**
		 * @todo filtrovat dotaz 
		 * findAll(order, base, offset);
		 */
		Flight flight = em.find(Flight.class, flightId);
		Collection<Reservation> reservations = flight.getReservations();
		GenericEntity<Collection<Reservation>> entity = new GenericEntity<Collection<Reservation>>(reservations) {};  
		return Response.ok().header("X-Count-records", super.count())
				.entity(entity).build();
	}

	@GET
	@Path("/{id}")
	public Reservation getReservation(@PathParam("id") Long id) {
		Reservation item = super.find(id);
		return item;
	}

	@POST
	@Path("/")
	@RolesAllowed({ "admin" })
	public Response add(Reservation destination){
		super.create(destination);
		return Response.ok().build();
	}

	@PUT
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response edit(@PathParam("id") Long id, Reservation values) {
		Reservation orig = super.find(id);
		super.edit(orig);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response delete(@PathParam("id") Long id) {
		super.remove(super.find(id));
		return Response.ok().build();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	@PostConstruct
	private void loadFlight(){
	}

}
