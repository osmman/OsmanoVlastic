package model;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.lang.String;
import java.sql.Time;
import java.util.Date;

import javax.persistence.*;
import javax.ws.rs.DefaultValue;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.spi.touri.URITemplate;

import resource.ResourceType;
import model.Flight;
import model.StateChoices;
import core.resource.UrlResource;

/**
 * Entity implementation class for Entity: Reservation
 */
@Entity
@XmlRootElement
public class Reservation extends UrlResource
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer seats;

    private String password;

    private Date created;

    @Enumerated(EnumType.STRING)
    private StateChoices state;

    @ManyToOne(fetch = FetchType.EAGER)
    private Flight flight;

    private static final long serialVersionUID = 1L;

    public Reservation()
    {
        super();
    }

    public Flight getFlight()
    {
        return this.flight;
    }

    public void setFlight(Flight flight)
    {
        this.flight = flight;
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getSeats()
    {
        return this.seats;
    }

    public void setSeats(Integer seats)
    {
        this.seats = seats;
    }

    public String getPassword()
    {
        return this.password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Date getCreated()
    {
        return this.created;
    }

    public void setCreated(Date created)
    {
        this.created = created;
    }

    public StateChoices getState()
    {
        return this.state;
    }

    public void setState(StateChoices state)
    {
        this.state = state;
    }

    public Long getFlightId()
    {
        return getFlight().getId();
    }

    @PostLoad
    public void loadUrl()
    {
        super.loadUrl();
    }
}
