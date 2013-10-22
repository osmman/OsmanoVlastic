package core.mapper;

import javax.xml.bind.annotation.XmlRootElement;

import model.Destination;
import model.Flight;
import model.Reservation;


@XmlRootElement(name="reservation")
public class ReservationMapper extends Mapper<Reservation> {
	private Integer seats;
	
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	
	public Reservation map(Reservation origin) {
		origin.setSeats(seats);
		return origin;
	}
	
}
