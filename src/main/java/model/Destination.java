package model;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

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
public class Destination implements Serializable {
   
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
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
