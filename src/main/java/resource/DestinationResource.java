package resource;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.validation.Valid;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.Consumes;

import org.jboss.resteasy.annotations.Form;
import org.jboss.resteasy.spi.validation.ValidateRequest;

import model.Destination;

@Path("/destination")
@Stateless
public class DestinationResource extends AbstractFacade<Destination> {

	public DestinationResource() {
		super(Destination.class);
	}

	@PersistenceContext(name = "primary")
	private EntityManager em;

	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Destination> getDestinations() {
		return super.findAll();
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
	public Response addDestination(Destination destination) {
		super.create(destination);
		return Response.ok().build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response editDestination(@PathParam("id") Long id,
			@Valid Destination destination) {
		super.edit(destination);
		return Response.ok().build();
	}

	@DELETE
	@Path("tasks/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteDestination(@PathParam("id") Long id) {
		super.remove(super.find(id));
		return Response.ok().build();
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
