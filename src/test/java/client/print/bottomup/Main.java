package client.print.bottomup;

import server.print.bottomup.PrintServiceImpl;

import javax.xml.ws.Endpoint;

public class Main {

    public static void main( String[] args ) {
        Object implementor = new PrintServiceImpl();
        String address = "http://localhost:8000/PrintService";
        Endpoint.publish( address, implementor );
    }
}
