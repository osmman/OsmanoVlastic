package core.mapper;

import java.sql.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import model.Destination;
import model.Flight;

@XmlRootElement(name = "flight")
public class FlightMapper extends Mapper<Flight> {

    private String name;

    private Date dateOfDeparture;

    private Integer seats;

    private Long to;

    private Long from;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateOfDeparture() {
        return dateOfDeparture;
    }

    public void setDateOfDeparture(Date dateOfDeparture) {
        this.dateOfDeparture = dateOfDeparture;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getFrom() {
        return from;
    }

    public void setFrom(Long from) {
        this.from = from;
    }

    @Override
    public Flight map(Flight origin) {
        origin.setDateOfDeparture(dateOfDeparture);
        origin.setName(name);
        origin.setSeats(seats);

        if (this.from != null) {
            Destination from = new Destination();
            from.setId(this.from);
            origin.setFrom(from);
        }

        if (this.to != null) {
            Destination to = new Destination();
            to.setId(this.to);
            origin.setTo(to);
        }
        return origin;
    }
}
