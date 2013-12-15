package client.print.topdown;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 15.12.13
 * Time: 16:35
 * To change this template use File | Settings | File Templates.
 */
public class Generator {

    public static void main( String[] args ) {

        WSDLToJava.main(new String[]{
                "-client",
                "-d",
                "src/main/java",
                "-p",
                "client.print.topdown",
                "http://localhost:8080/osmanvlastic/PrintService-v2?wsdl"
        });
        System.out.println( "Done!" );
    }

}
