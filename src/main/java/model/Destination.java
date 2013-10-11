package model;

import java.lang.Long;
import java.lang.String;
import java.net.URI;
import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity implementation class for Entity: Destinantion
 *
 */
@Entity
@XmlRootElement
public class Destination extends UrlResource{
   
	@Id
	@GeneratedValue
	private Long id;
	

	@NotNull
	@Size(min=1, max=255)
	@Column(unique=true)
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
