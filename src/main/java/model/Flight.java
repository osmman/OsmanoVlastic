package model;

import core.listener.FlightListener;
import core.resource.UrlResource;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collection;
import java.util.Date;

/**
 * Entity implementation class for Entity: Flight
 */
@Entity
@XmlRootElement
@EntityListeners(FlightListener.class)
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

    @Transient
    private Double rate = 1.;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "to_id")
    @NotNull
    private Destination to;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "from_id")
    @NotNull
    private Destination from;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "flight")
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

    @JsonIgnore
    @XmlTransient
    public Float getPrice() {
        return this.price;
    }

    @XmlElement(name = "price")
    @JsonProperty("price")
    public Float getRatedPrice() {
        return this.price * this.rate.floatValue();
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
    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @JsonIgnore
    @XmlTransient
    public Collection<Reservation> getReservations() {
        return reservations;
    }

    @PostLoad
    public void loadUrl() {
        super.loadUrl();
    }
}
