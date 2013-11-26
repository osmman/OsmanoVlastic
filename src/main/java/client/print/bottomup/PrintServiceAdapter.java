package client.print.bottomup;

import client.print.utils.ObjectsMapper;

import javax.activation.DataHandler;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 19.11.13
 * Time: 0:44
 * To change this template use File | Settings | File Templates.
 */
public class PrintServiceAdapter {

    private URL serviceUrl;

    private final QName SERVICE_NAME = new QName("http://printservice.org/", "PrintService");

    public void setServiceUrl(String url) {
        try {
            serviceUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public InputStream getFileIS(model.Reservation reservation) {
        PrintService_Service ss = new PrintService_Service(serviceUrl, SERVICE_NAME);
        PrintService service = ss.getTextPort();

        InputStream is = null;

        Reservation printReservation = null;

        try {
            printReservation = ObjectsMapper.mapToServiceReservation(reservation);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        try {
            DataHandler resultHandler = service.printReservation(printReservation);
            is = resultHandler.getInputStream();
        } catch (PrintException e) {
            System.out.println("Expected exception: PrintException has occurred.");
            System.out.println(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return is;
    }
}
