package resource;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

@Path("/")
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class AppResource {

	@Context
	UriInfo uriInfo;
	
	@GET
	public Response getResources() {
		Collection<URI> locales = new LinkedList<URI>();
		locales.add(buildURIFromResource(DestinationResource.class));
		locales.add(buildURIFromResource(FlightResource.class));
		return Response.status(Status.NO_CONTENT).header("Locale", locales)
				.build();
	}
	
	private URI buildURIFromResource(final Class<?> resource){
		return uriInfo.getBaseUriBuilder().path(resource).build();
	}
}
