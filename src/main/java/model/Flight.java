package model;

import java.io.Serializable;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Flight
 * 
 */
@Entity
@XmlRootElement
public class Flight implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Date dateOfDeparture;
	private Float distance;
	private Integer seats;
	private Float Price;
	private Destination to;
	private Destination from;
	private static final long serialVersionUID = 1L;

	public Flight() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDateOfDeparture() {
		return this.dateOfDeparture;
	}

	public void setDateOfDeparture(Date dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture;
	}

	public Float getDistance() {
		return this.distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Integer getSeats() {
		return this.seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Float getPrice() {
		return this.Price;
	}

	public void setPrice(Float Price) {
		this.Price = Price;
	}

}
