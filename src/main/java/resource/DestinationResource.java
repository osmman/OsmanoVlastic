package resource;

import java.util.List;

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

import utils.Resources;
import model.Destination;


@Path(value = "/destination")
public class DestinationResource extends Resources {
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Destination> getDestinations() {
		return getEntityManager().createNamedQuery("Destination.findAll", Destination.class).getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Destination getDestination(@PathParam("id") Long id) {
		return getEntityManager().find(Destination.class, id);
	}

	@POST
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Destination addDestination(Destination destination) {
		return null;
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Destination editDestination(@PathParam("id") Long id,
			Destination destination) {
		return null;
	}

	@DELETE
	@Path("tasks/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response deleteDestination(@PathParam("id") Long id) {
		return Response.ok().build();
	}

}
