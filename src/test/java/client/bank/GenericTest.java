package client.bank;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.ws.security.wss4j.WSS4JInInterceptor;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;

import java.lang.reflect.Proxy;
import java.util.Map;

public class GenericTest
{

    protected static void registerInInterceptor(Object service, Map<String, Object> context)
    {

        // filtr pro obsluhu WS-Security
        WSS4JInInterceptor interceptor = new WSS4JInInterceptor(context);

        // na ziskaneho klienta zaregistrujeme vstupni filtr, ktery se bude aplikovat na vsechny prichozi zpravy bez vyjimek
        getClient(service).getInInterceptors().add(interceptor);

        // na ziskaneho klienta zaregistrujeme vstupni filtr, ktery se bude aplikovat na vsechny prichozi chybove zpravy
        getClient(service).getInFaultInterceptors().add(interceptor);
    }

    protected static void registerOutInterceptor(Object service, Map<String, Object> context)
    {

        // filtr pro obsluhu WS-Security
        WSS4JOutInterceptor interceptor = new WSS4JOutInterceptor(context);

        // na ziskaneho klienta zaregistrujeme vystupni filtr, ktery se bude aplikovat na odchozi zpravy
        getClient(service).getOutInterceptors().add(interceptor);
    }

    private static Client getClient(Object service)
    {

        // musime ziskat klienta vzdalene sluzby, abychom mu mohli pridat dalsi konfiguraci
        return ((ClientProxy) Proxy.getInvocationHandler(service)).getClient();
    }
}
