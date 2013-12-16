package client.print.bottomup.utils;

import client.print.bottomup.Destination;
import client.print.bottomup.Flight;
import client.print.bottomup.Reservation;
import client.print.bottomup.StateChoices;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 19.11.13
 * Time: 0:19
 * To change this template use File | Settings | File Templates.
 */
public class ObjectsMapper {

    public static Reservation mapToServiceReservation(model.Reservation reservation) throws DatatypeConfigurationException {
        if (reservation == null) return null;
        Reservation result = new Reservation();
        result.setState(mapToServiceStateChoise(reservation.getState()));
        result.setPassword(reservation.getPassword());
        result.setSeats(reservation.getSeats());

        result.setFlight(mapToServiceFlight(reservation.getFlight()));

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(reservation.getCreated());
        DatatypeFactory df = DatatypeFactory.newInstance();
        result.setCreated(df.newXMLGregorianCalendar(calendar));

        return result;
    }

    public static StateChoices mapToServiceStateChoise(model.StateChoices state) {
        switch (state) {
            case PAID:
                return StateChoices.PAID;
            case CANCELED:
                return StateChoices.CANCELED;
            case NEW:
                return StateChoices.NEW;
            default: // do nothing
        }
        return null;
    }

    public static Flight mapToServiceFlight(model.Flight flight) throws DatatypeConfigurationException {
        if (flight == null) return null;
        Flight result = new Flight();
        result.setSeats(flight.getSeats());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(flight.getDateOfDeparture());
        DatatypeFactory df = DatatypeFactory.newInstance();
        result.setDateOfDeparture(df.newXMLGregorianCalendar(calendar));

        result.setDistance(flight.getDistance());
        result.setFrom(mapToServiceDestination(flight.getFrom()));
        result.setTo(mapToServiceDestination(flight.getTo()));
        result.setName(flight.getName());
        return result;
    }

    public static Destination mapToServiceDestination(model.Destination destination) {
        if (destination == null) return null;
        Destination result = new Destination();
        result.setName(destination.getName());
        result.setLatitude(destination.getLatitude());
        result.setLongitude(destination.getLongitude());
        return result;
    }
}
