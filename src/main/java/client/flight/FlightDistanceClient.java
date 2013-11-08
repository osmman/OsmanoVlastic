package client.flight;

import model.Geocode;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 8.11.13
 * Time: 22:25
 * To change this template use File | Settings | File Templates.
 */
public interface FlightDistanceClient {
    Double getDistance(Geocode from, Geocode to);
}
