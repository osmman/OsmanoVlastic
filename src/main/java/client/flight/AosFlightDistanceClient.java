package client.flight;

import client.ClientException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import model.Geocode;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.enterprise.inject.Alternative;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 8.11.13
 * Time: 22:26
 * To change this template use File | Settings | File Templates.
 */
@Alternative
public class AosFlightDistanceClient implements FlightDistanceClient {

    private Client client;

    public static final String RESOURCE = "http://aos.czacm.org/flight-distance";

    public AosFlightDistanceClient() {
        super();
        client = Client.create();
    }

    @Override
    public Double getDistance(Geocode from, Geocode to) {
        ClientResponse response = requestDistance(from, to);
        String output = response.getEntity(String.class);

        JSONParser parser = new JSONParser();

        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) parser.parse(output);
        } catch (ParseException e) {
            throw new ClientException("Bad json format");
        }

        return parseDistance(jsonObj);
    }

    private ClientResponse requestDistance(Geocode from, Geocode to) {
        WebResource webResource = client.resource(RESOURCE)
                .queryParam("from", from.toString()).queryParam("to", to.toString());

        ClientResponse response = webResource.accept("application/json").get(
                ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new ClientException("Failed : HTTP error code : " + response.getStatus());
        }

        return response;
    }

    private Double parseDistance(JSONObject jsonObj) {
        if (jsonObj != null && jsonObj.containsKey("length")) {
            return (Double) jsonObj.get("length");
        }
        throw new ClientException("Bad json format");
    }
}
