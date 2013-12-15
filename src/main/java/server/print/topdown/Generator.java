package server.print.topdown;


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
                "-impl",
                "-server",
                "-d",
                "src/main/java",
                "-p",
                "server.print.topdown",
                "classpath:top-down-PrintService.wsdl"
        });
        System.out.println( "Done!" );
    }

}
