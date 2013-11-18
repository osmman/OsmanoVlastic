package server.print.bottomup;

import model.Reservation;

import javax.activation.DataHandler;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlMimeType;

@WebService(targetNamespace = "http://printservice.org/")
public interface PrintService {

    @XmlMimeType("application/octet-stream")
    DataHandler printReservation(
            @WebParam(name = "reservation") Reservation reservation)
			throws PrintException;
}
