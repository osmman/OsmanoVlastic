package resource;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

@Path("/destination")
@Stateless
public class DestinationResource {
	
	@PersistenceContext(name = "primary")
    private EntityManager em;
	
	@GET
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Destination> getDestinations() {
		return em.createNamedQuery("Destination.findAll",
				Destination.class).getResultList();
	}

	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Destination getDestination(@PathParam("id") Long id) {
		return em.find(Destination.class, id);
	}

	@POST
	@Path("/")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Destination addDestination(Destination destination) {
		try {
			em.persist(destination);
		} finally {
			em.close();
		}

		return destination;
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
