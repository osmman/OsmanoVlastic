package client.exchange;

import client.ClientException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.enterprise.inject.Alternative;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
@Alternative
public class RateExchangeClientImpl implements RateExchangeClient {

    private Client client;

    public static final String RESOURCE = "http://rate-exchange.appspot.com/currency";

    public RateExchangeClientImpl() {
        super();
        client = Client.create();
    }

    private ClientResponse requestRate(String from, String to) {
        WebResource webResource = client.resource(RESOURCE)
                .queryParam("from", from).queryParam("to", to);

        ClientResponse response = webResource.accept("application/json").get(
                ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new ClientException("Failed : HTTP error code : " + response.getStatus());
        }

        return response;
    }

    private Double parseRate(JSONObject jsonObj) {
        if (jsonObj != null) {
            if (!jsonObj.containsKey("err")) {
                return (Double) jsonObj.get("rate");
            } else {
                throw new ClientException((String) jsonObj.get("err"));
            }
        }
        throw new ClientException("Bad json format");
    }

    @Override
    public Double getCurrency(String from, String to) {
        ClientResponse response = requestRate(from, to);
        String output = response.getEntity(String.class);

        JSONParser parser = new JSONParser();

        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject) parser.parse(output);
        } catch (ParseException e) {
            throw new ClientException("Bad json format");
        }

        return parseRate(jsonObj);
    }
}
