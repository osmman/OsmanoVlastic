package client.maps;

import client.ClientException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GoogleMapsClient implements MapsClient {

    private Client client;

    public static final String RESOURCE = "http://maps.googleapis.com/maps/api/geocode/json";

    public GoogleMapsClient() {
        super();
        client = Client.create();
    }

    private ClientResponse requestGeocode(String city) {
        WebResource webResource = client.resource(RESOURCE)
                .queryParam("address", city).queryParam("sensor", "false");
        ClientResponse response = webResource.accept("application/json").get(
                ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new ClientException("Failed : HTTP error code : " + response.getStatus());
        }

        return response;
    }

    private Geocode parseGeocode(JSONObject jsonObj) {
        if (jsonObj != null && jsonObj.containsKey("status")) {
            if (jsonObj.get("status").equals("OK")) {
                JSONArray results = (JSONArray) jsonObj.get("results");
                JSONObject first = (JSONObject) results.get(0);
                JSONObject geometry = (JSONObject) first.get("geometry");
                JSONObject location = (JSONObject) geometry.get("location");
                return new Geocode(location.get("lat").toString(), location.get("lng").toString());
            } else {
                throw new ClientException((String) jsonObj.get("error_message"));
            }
        }
        throw new ClientException("Bad json format");
    }

    public Geocode getGeocode(String city) {

        ClientResponse response = requestGeocode(city);
        String output = response.getEntity(String.class);

        JSONParser parser = new JSONParser();

        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) parser.parse(output);
        } catch (ParseException e) {
            throw new ClientException("Bad json format");
        }

        return parseGeocode(jsonObj);
    }

}
