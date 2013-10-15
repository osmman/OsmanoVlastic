package core.mapper;

import java.sql.Date;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import model.Destination;
import model.Flight;

@XmlRootElement
public class FlightMapper {

	private String name;

	private Date dateOfDeparture;

	private Float distance;

	private Integer seats;

	private Float price;

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

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
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

	public Flight getValue() {
		Flight flight = new Flight();
		flight.setDateOfDeparture(dateOfDeparture);
		flight.setDistance(distance);
		flight.setName(name);
		flight.setPrice(price);
		flight.setSeats(seats);

		if (this.from != null) {
			Destination from = new Destination();
			from.setId(this.from);
			flight.setFrom(from);
		}

		if (this.to != null) {
			Destination to = new Destination();
			to.setId(this.to);
			flight.setTo(to);
		}
		return flight;
	}
}
