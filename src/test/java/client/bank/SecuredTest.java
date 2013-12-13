package client.bank;

import client.bank.secured.*;
import org.apache.ws.security.handler.WSHandlerConstants;

import javax.xml.namespace.QName;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class SecuredTest extends GenericTest
{

    private static final QName SERVICE_NAME = new QName("http://centralbank.org/", "SecuredService");

    private static final URL WSDL_URL = SecuredService.WSDL_LOCATION;

    public static void main(String args[])
    {

        SecuredService factory = new SecuredService(WSDL_URL, SERVICE_NAME);
        BankService service = factory.getSecuredPort();

        // konfigurace vstupniho filtru
        Map<String, Object> inContext = new HashMap<String, Object>();
        inContext.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE + " " + WSHandlerConstants.ENCRYPT); // akce, kterou registrujeme
        inContext.put(WSHandlerConstants.SIG_PROP_FILE, "bank.properties"); // konfigurace keystore
        inContext.put(WSHandlerConstants.SIGNATURE_USER, "bank"); // kterou identitu z keystore budeme pouzivat
        inContext.put(WSHandlerConstants.DEC_PROP_FILE, "client.properties"); // konfigurace keystore
        inContext.put(WSHandlerConstants.ENCRYPTION_USER, "client"); // kterou identitu z keystore budeme pouzivat
        inContext.put(WSHandlerConstants.PW_CALLBACK_CLASS, "client.bank.PasswordCallback"); // prihlaseni ke keystore
        registerInInterceptor(service, inContext);

        // konfigurace vystupniho filtru
        Map<String, Object> outContext = new HashMap<String, Object>();
        outContext.put(WSHandlerConstants.ACTION, WSHandlerConstants.SIGNATURE + " " + WSHandlerConstants.ENCRYPT); // akce, kterou registrujeme
        outContext.put(WSHandlerConstants.USER, "xxx"); // povinna konfigurace, ale nepouzivame ji ted
        outContext.put(WSHandlerConstants.SIG_PROP_FILE, "client.properties"); // konfigurace keystore
        outContext.put(WSHandlerConstants.SIGNATURE_USER, "client"); // kterou identitu z keystore budeme pouzivat
        outContext.put(WSHandlerConstants.ENC_PROP_FILE, "bank.properties"); // konfigurace keystore
        outContext.put(WSHandlerConstants.ENCRYPTION_USER, "bank"); // kterou identitu z keystore budeme pouzivat
        outContext.put(WSHandlerConstants.PW_CALLBACK_CLASS, "client.bank.PasswordCallback"); // prihlaseni ke keystore
        registerOutInterceptor(service, outContext);

        try {

            service.newTransaction(1234, 5678, 1000, "Platba uzivatele " + Generator.USERNAME);
            System.out.println("Platba probehla uspesne.");

        } catch (TransactionException e) {
            System.out.println("Platba nebyla prijata. " + e.getMessage());
        }
    }
}
