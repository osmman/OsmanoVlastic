
package client.print.bottomup;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the client.print.bottomup package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Reservation_QNAME = new QName("http://printservice.org/", "reservation");
    private final static QName _Flight_QNAME = new QName("http://printservice.org/", "flight");
    private final static QName _PrintReservation_QNAME = new QName("http://printservice.org/", "printReservation");
    private final static QName _PrintReservationResponse_QNAME = new QName("http://printservice.org/", "printReservationResponse");
    private final static QName _PrintError_QNAME = new QName("http://printservice.org/", "PrintError");
    private final static QName _Destination_QNAME = new QName("http://printservice.org/", "destination");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.print.bottomup
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Flight }
     * 
     */
    public Flight createFlight() {
        return new Flight();
    }

    /**
     * Create an instance of {@link PrintReservation }
     * 
     */
    public PrintReservation createPrintReservation() {
        return new PrintReservation();
    }

    /**
     * Create an instance of {@link PrintError }
     * 
     */
    public PrintError createPrintError() {
        return new PrintError();
    }

    /**
     * Create an instance of {@link Reservation }
     * 
     */
    public Reservation createReservation() {
        return new Reservation();
    }

    /**
     * Create an instance of {@link Destination }
     * 
     */
    public Destination createDestination() {
        return new Destination();
    }

    /**
     * Create an instance of {@link PrintReservationResponse }
     * 
     */
    public PrintReservationResponse createPrintReservationResponse() {
        return new PrintReservationResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://printservice.org/", name = "reservation")
    public JAXBElement<Reservation> createReservation(Reservation value) {
        return new JAXBElement<Reservation>(_Reservation_QNAME, Reservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Flight }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://printservice.org/", name = "flight")
    public JAXBElement<Flight> createFlight(Flight value) {
        return new JAXBElement<Flight>(_Flight_QNAME, Flight.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintReservation }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://printservice.org/", name = "printReservation")
    public JAXBElement<PrintReservation> createPrintReservation(PrintReservation value) {
        return new JAXBElement<PrintReservation>(_PrintReservation_QNAME, PrintReservation.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintReservationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://printservice.org/", name = "printReservationResponse")
    public JAXBElement<PrintReservationResponse> createPrintReservationResponse(PrintReservationResponse value) {
        return new JAXBElement<PrintReservationResponse>(_PrintReservationResponse_QNAME, PrintReservationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PrintError }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://printservice.org/", name = "PrintError")
    public JAXBElement<PrintError> createPrintError(PrintError value) {
        return new JAXBElement<PrintError>(_PrintError_QNAME, PrintError.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Destination }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://printservice.org/", name = "destination")
    public JAXBElement<Destination> createDestination(Destination value) {
        return new JAXBElement<Destination>(_Destination_QNAME, Destination.class, null, value);
    }

}
