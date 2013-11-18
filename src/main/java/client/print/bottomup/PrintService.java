package client.print.bottomup;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2013-11-17T11:20:20.196+01:00
 * Generated source version: 2.7.7
 * 
 */
@WebService(targetNamespace = "http://printservice.org/", name = "PrintService")
@XmlSeeAlso({ObjectFactory.class})
public interface PrintService {

    @WebResult(name = "return", targetNamespace = "")
    @RequestWrapper(localName = "printReservation", targetNamespace = "http://printservice.org/", className = "client.print.bottomup.PrintReservation")
    @WebMethod
    @ResponseWrapper(localName = "printReservationResponse", targetNamespace = "http://printservice.org/", className = "client.print.bottomup.PrintReservationResponse")
    public javax.activation.DataHandler printReservation(
        @WebParam(name = "reservation", targetNamespace = "")
        client.print.bottomup.Reservation reservation
    ) throws PrintException;
}
