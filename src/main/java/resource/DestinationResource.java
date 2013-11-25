package resource;

import client.ClientException;
import client.maps.MapsClient;
import core.validation.Validator;
import model.Geocode;
import client.maps.GoogleMapsClient;
import core.mapper.DestinationMapper;
import core.resource.AbstractFacade;
import model.Destination;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import java.util.Collection;

@Path(ResourceType.DESTINATION)
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class DestinationResource extends AbstractFacade<Destination>
{

    public DestinationResource()
    {
        super(Destination.class);
    }

    @Context
    UriInfo uriInfo;

    @Inject
    private EntityManager em;

    @Inject
    private UserTransaction userTransaction;

    @Inject
    private MapsClient mapsClient;

    @Inject
    private Validator validator;

    @GET
    @Path("/")
    public Response getDestinations(@HeaderParam("X-Order") String order,
                                    @HeaderParam("X-Base") Integer base,
                                    @HeaderParam("X-Offset") Integer offset,
                                    @HeaderParam("X-Filter") String filter)
    {
        Collection<Destination> destinations = super.findAll(order, base,
                offset);
        GenericEntity<Collection<Destination>> entity = new GenericEntity<Collection<Destination>>(
                destinations)
        {
        };
        return Response.ok().header("X-Count-records", super.count())
                .entity(entity).build();
    }

    @GET
    @Path("/{id}")
    public Destination getDestination(@PathParam("id") Long id)
    {
        Destination destination = super.find(id);
        testExistence(destination);
        destination.getArrivals();
        return destination;
    }

    @POST
    @Path("/")
    @RolesAllowed({"admin"})
    public Response add(DestinationMapper mapper) throws SystemException
    {
        try {
            Destination destination = mapper.map(new Destination());
            validator.validate(destination);

            Geocode geocode = mapsClient.getGeocode(destination.getName());
            destination.setLatitude(geocode.getLatitude());
            destination.setLongitude(geocode.getLongitude());

            super.create(destination);
            return Response.status(Status.CREATED)
                    .header("Locale", destination.getUrl()).build();

        } catch (ClientException e) {
            userTransaction.setRollbackOnly();
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }

    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response edit(@PathParam("id") Long id, DestinationMapper mapper) throws SystemException
    {

        try {
            Destination destination = super.find(id);
            mapper.map(destination);

            Geocode geocode = mapsClient.getGeocode(destination.getName());
            destination.setLatitude(geocode.getLatitude());
            destination.setLongitude(geocode.getLongitude());

            System.out.println(geocode);
            super.edit(destination);
            return Response.status(Status.NO_CONTENT)
                    .header("Locale", destination.getUrl()).build();

        } catch (ClientException e) {
            userTransaction.setRollbackOnly();
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"admin"})
    public Response delete(@PathParam("id") Long id)
    {
        Destination item = super.find(id);
        testExistence(item);
        super.remove(item);
        return Response.status(Status.NO_CONTENT).build();
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

}
