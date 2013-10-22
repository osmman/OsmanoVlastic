package core.message;

import java.net.URI;

import javax.xml.bind.annotation.*;

@XmlRootElement
public final class SingleEntryPoint {

	private URI destination;
	
	private URI flight;
	
	@XmlElement
	public String getDestination() {
		return destination.toString();
	}
	
	@XmlElement
	public String getFlight() {
		return flight.toString();
	}
	
	public void setDestination(URI destination) {
		this.destination = destination;
	}
	
	public void setFlight(URI flight) {
		this.flight = flight;
	}
	
	
}
