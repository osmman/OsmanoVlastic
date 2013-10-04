package resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class HelloWorld {

    @GET
    @Produces({ MediaType.APPLICATION_JSON})
    public String getHelloWorld() {
        return "{\"msg\":\"Hello world\"}";
    }
}
