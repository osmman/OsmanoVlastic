package model;

import java.io.Serializable;
import java.lang.Float;
import java.lang.Integer;
import java.lang.Long;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import core.resource.UrlResource;

/**
 * Entity implementation class for Entity: Flight
 * 
 */
@Entity
@XmlRootElement
public class Flight extends UrlResource {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	@Size(min = 1, max = 255)
	@Column(unique = true)
	private String name;
	
	private Date dateOfDeparture;
	
	private Float distance;
	
	private Integer seats;
	
	private Float price;
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "to_id")
	private Destination to;
	
	
	@ManyToOne(fetch= FetchType.EAGER)
	@JoinColumn(name = "from_id")
	private Destination from;
	
	@OneToMany(fetch=FetchType.EAGER)
	private Collection<Reservation> reservations;
	
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

	public void setDateOfDeparture(Date date) {
		this.dateOfDeparture = date;
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
		return this.price;
	}

	public void setPrice(Float Price) {
		this.price = Price;
	}

	public Destination getTo() {
		return to;
	}

	public void setTo(Destination to) {
		this.to = to;
	}

	public Destination getFrom() {
		return from;
	}

	public void setFrom(Destination from) {
		this.from = from;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonIgnore
	@XmlTransient
	public Collection<Reservation> getReservations() {
		return reservations;
	}

	@PostLoad
	public void loadUrl(){
		super.loadUrl();
	}
}
