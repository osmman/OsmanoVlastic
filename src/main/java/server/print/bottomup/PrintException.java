package server.print.bottomup;

import javax.xml.ws.WebFault;

@WebFault( name = "PrintError", targetNamespace = "http://printservice.org/" )
public class PrintException extends Exception {

    public PrintException( String message ) {
        super( message );
    }
}
