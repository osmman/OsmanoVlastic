package server.print.topdown;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2013-12-15T20:36:02.669+01:00
 * Generated source version: 2.7.7
 * 
 */
@WebService(targetNamespace = "http://www.printservice.org/", name = "PrintService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface PrintService {

    @WebMethod(operationName = "PrintReservation", action = "http://www.printservice.org/PrintReservation")
    public void printReservation(
        @WebParam(partName = "PrintReservation", name = "PrintReservation", targetNamespace = "")
        Reservation printReservation,
        @WebParam(partName = "Email", name = "Email", targetNamespace = "")
        java.lang.String email
    ) throws PrintReservationFault;
}
