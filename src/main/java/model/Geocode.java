package model;

import javax.validation.constraints.NotNull;

public class Geocode
{

    private String latitude;

    private String longitude;

    public Geocode(String latitude, String longitude)
    {
        super();
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude()
    {
        return latitude;
    }

    @NotNull
    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    @NotNull
    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return latitude + "," + longitude;
    }
}
