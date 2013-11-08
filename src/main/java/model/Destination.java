package model;

import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import core.resource.UrlResource;

/**
 * Entity implementation class for Entity: Destination
 */
@Entity
@XmlRootElement
public class Destination extends UrlResource {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    @Column(unique = true)
    private String name;

    private String latitude;

    private String longitude;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "from")
    private Set<Flight> departures;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "to")
    private Set<Flight> arrivals;

    @SuppressWarnings("unused")
    private static final long serialVersionUID = 1L;

    public Destination() {
        super();
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Flight> getArrivals() {
        return arrivals;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<Flight> getDepartures() {
        return departures;
    }

    @PostLoad
    public void loadUrl() {
        super.loadUrl();
    }
}
