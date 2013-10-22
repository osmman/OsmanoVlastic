package resource;

import java.net.URI;
import java.util.Collection;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.swing.border.EtchedBorder;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import core.message.SingleEntryPoint;

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
		
		SingleEntryPoint entry = new SingleEntryPoint();
		entry.setDestination(buildURIFromResource(DestinationResource.class));
		entry.setFlight(buildURIFromResource(FlightResource.class));

		return Response.ok(entry)
				.build();
	}
	
	private URI buildURIFromResource(final Class<?> resource){
		return uriInfo.getBaseUriBuilder().path(resource).build();
	}
}
