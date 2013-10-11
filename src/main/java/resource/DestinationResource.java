package resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;

import model.Destination;

@Path("/destination")
@Stateless
public class DestinationResource extends AbstractFacade<Destination> {

	public DestinationResource() {
		super(Destination.class);
	}

	@Context
	UriInfo uriInfo;

	@PersistenceContext(name = "primary")
	private EntityManager em;

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response getDestinations(@HeaderParam("X-Order") String order,
			@HeaderParam("X-Base") Integer base,
			@HeaderParam("X-Offset") Integer offset,
			@HeaderParam("X-Filter") String filter) {
		List<Destination> destinations = super.findAll(order, base, offset);
		return Response.ok().header("X-Count-records", super.count())
				.entity(destinations).build();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Destination getDestination(@PathParam("id") Long id) {
		return super.find(id);
	}

	@POST
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({"admin"})
	public Response addDestination(Destination destination) {
		super.create(destination);
		return Response.ok().build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({"admin"})
	public Response editDestination(@PathParam("id") Long id,
			Destination destination) {
		super.edit(destination);
		return Response.ok().build();
	}

	@DELETE
	@Path("tasks/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@RolesAllowed({"admin"})
	public Response deleteDestination(@PathParam("id") Long id) {
		super.remove(super.find(id));
		return Response.ok().build();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	protected URI basePath(Destination item) {
		return uriInfo.getBaseUriBuilder().path("destination")
				.path(item.getId().toString()).build();
	}
}
