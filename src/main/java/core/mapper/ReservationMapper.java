package core.mapper;

import model.Flight;
import model.Reservation;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "reservation")
public class ReservationMapper extends Mapper<Reservation>
{
    private Integer seats;

    private Long flight;

    public Integer getSeats()
    {
        return seats;
    }

    public void setSeats(Integer seats)
    {
        this.seats = seats;
    }

    public Long getFlight()
    {
        return flight;
    }

    public void setFlight(Long flight)
    {
        this.flight = flight;
    }

    public Reservation map(Reservation origin)
    {
        origin.setSeats(seats);
        Flight flight = new Flight();
        flight.setId(this.flight);
        origin.setFlight(flight);
        return origin;
    }

}
