package server.print.bottomup;

import model.Reservation;
import model.StateChoices;
import org.apache.commons.io.FileUtils;
import server.print.utils.ByteArrayDataHandler;
import server.print.utils.GenerateTicke;

import javax.activation.DataHandler;
import javax.jws.WebService;
import javax.xml.ws.soap.MTOM;
import java.io.File;
import java.io.IOException;

@MTOM
@WebService(portName = "TextPort", serviceName = "PrintService", targetNamespace = "http://printservice.org/", endpointInterface = "server.print.bottomup.PrintService")
public class PrintServiceImpl implements PrintService
{

    public DataHandler printReservation(Reservation reservation) throws PrintException
    {
        if (reservation == null) throw new PrintException("Nebyl zaslan objekt Reservation");
        if (reservation.getState() != StateChoices.PAID) throw new PrintException("Rezervace nebyla zaplaceno");
        File temp;
        byte[] output;
        try {
            temp = GenerateTicke.generate(reservation.getFlight().getName(), reservation.getSeats());
            output = FileUtils.readFileToByteArray(temp);
        } catch (IOException e) {
            throw new PrintException("Nepodarilo se vygenerovat soubor");
        }
        return new ByteArrayDataHandler(output, "");
    }

}
