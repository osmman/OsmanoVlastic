package model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Collection;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Destinantion
 *
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name="Destination.findAll",
                query="SELECT d FROM Destination d"),
    @NamedQuery(name="Destination.findByName",
                query="SELECT d FROM Destination d WHERE d.name = :name"),
}) 
public class Destination{
   
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	
	@OneToMany(mappedBy="from")
	private Collection<Flight> departures;
	
	@OneToMany(mappedBy="to")
	private Collection<Flight> arrivals;
	
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
   
}
