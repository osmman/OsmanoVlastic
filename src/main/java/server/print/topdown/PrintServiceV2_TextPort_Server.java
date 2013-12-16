
package server.print.topdown;

import javax.xml.ws.Endpoint;

/**
 * This class was generated by Apache CXF 2.7.7
 * 2013-12-16T22:20:12.041+01:00
 * Generated source version: 2.7.7
 * 
 */
 
public class PrintServiceV2_TextPort_Server{

    protected PrintServiceV2_TextPort_Server() throws java.lang.Exception {
        System.out.println("Starting Server");
        Object implementor = new PrintServiceV2Impl();
        String address = "http://localhost:8080/osmanvlastic/printServiceV2";
        Endpoint.publish(address, implementor);
    }
    
    public static void main(String args[]) throws java.lang.Exception { 
        new PrintServiceV2_TextPort_Server();
        System.out.println("Server ready..."); 
        
        Thread.sleep(5 * 60 * 1000); 
        System.out.println("Server exiting");
        System.exit(0);
    }
}
