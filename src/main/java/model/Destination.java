package model;

import java.lang.Long;
import java.lang.String;
import java.util.Collection;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.resteasy.spi.touri.ObjectToURI;
import org.jboss.resteasy.spi.touri.URITemplate;

import javax.xml.bind.annotation.adapters.*;

//import org.jboss.resteasy.annotations.providers.jaxb.json.Mapped;
//import org.jboss.resteasy.annotations.providers.jaxb.json.XmlNsMap;
import org.codehaus.jackson.annotate.JsonIgnore;

import core.resource.UrlResource;

/**
 * Entity implementation class for Entity: Destinantion
 * 
 */
@Entity
@XmlRootElement
public class Destination extends UrlResource {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@Size(min = 1, max = 255)
	@Column(unique = true)
	private String name;

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
	public void loadUrl(){
		super.loadUrl();
	}
}
