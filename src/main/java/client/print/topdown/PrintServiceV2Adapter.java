package client.print.topdown;

import client.print.topdown.utils.ObjectsMapper;

import javax.enterprise.inject.Alternative;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.namespace.QName;
import java.io.InputStream;
import java.net.URL;

/**
* Created with IntelliJ IDEA.
* User: usul
* Date: 19.11.13
* Time: 0:44
* To change this template use File | Settings | File Templates.
*/
@Alternative
public class PrintServiceV2Adapter {

    private URL serviceUrl;

    private final QName SERVICE_NAME = new QName("http://www.printservicev2.org/", "printServiceV2");

    public void setServiceUrl(String url) {
        try {
            serviceUrl = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getFileIS(model.Reservation reservation, String email) throws Exception {
        URL wsdlURL = PrintServiceV2_Service.WSDL_LOCATION;

        PrintServiceV2_Service ss = new PrintServiceV2_Service(serviceUrl, SERVICE_NAME);
        PrintServiceV2 service = ss.getTextPort();

        InputStream is = null;

        Reservation printReservation = null;

        try {
            printReservation = ObjectsMapper.mapToServiceReservation(reservation);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

        try {
            service.printReservation(printReservation, email);
        } catch (PrintException e) {
            e.printStackTrace();
            throw new Exception(e);
        }

    }
}
