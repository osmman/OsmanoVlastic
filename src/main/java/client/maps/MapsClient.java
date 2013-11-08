package client.maps;

import model.Geocode;

public interface MapsClient {
    public Geocode getGeocode(String city);
}
