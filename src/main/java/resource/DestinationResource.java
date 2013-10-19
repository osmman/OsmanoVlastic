package resource;

import java.net.URI;
import java.util.Collection;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
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
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import org.jboss.resteasy.links.AddLinks;
import org.jboss.resteasy.links.LinkResource;

import core.resource.AbstractFacade;
import model.Destination;

@Path("/destination")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class DestinationResource extends AbstractFacade<Destination> {

	public DestinationResource() {
		super(Destination.class);
	}

	@Context
	UriInfo uriInfo;

	@Inject
	private EntityManager em;

	// @Inject
	// private UserTransaction userTransaction;

	@GET
	@Path("/")
	public Response getDestinations(@HeaderParam("X-Order") String order,
			@HeaderParam("X-Base") Integer base,
			@HeaderParam("X-Offset") Integer offset,
			@HeaderParam("X-Filter") String filter) {
		Collection<Destination> destinations = super.findAll(order, base,
				offset);
		GenericEntity<Collection<Destination>> entity = new GenericEntity<Collection<Destination>>(
				destinations) {
		};
		return Response.ok().header("X-Count-records", super.count())
				.entity(entity).build();
	}

	@GET
	@Path("/{id}")
	public Destination getDestination(@PathParam("id") Long id) {
		Destination dest = super.find(id);
		dest.getArrivals();
		return dest;
	}

	@POST
	@Path("/")
	@RolesAllowed({ "admin" })
	public Response add(Destination destination) {
		super.create(destination);
		return Response.status(Status.CREATED)
				.header("Locale", destination.getUrl(this.uriInfo)).build();
	}

	@PUT
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response edit(@PathParam("id") Long id, Destination values) {
		values.setId(id);
		super.edit(values);
		values.loadUrl();
		return Response.status(Status.NO_CONTENT)
				.header("Locale:", values.getUrl(this.uriInfo)).build();
	}

	@DELETE
	@Path("/{id}")
	@RolesAllowed({ "admin" })
	public Response delete(@PathParam("id") Long id) {
		Destination item = super.find(id);
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

}
