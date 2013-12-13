package client.bank;

import org.apache.cxf.tools.wsdlto.WSDLToJava;

public class Generator
{

    public static final String USERNAME = "turekto5"; // ToDo VYPLNIT pro testovani!!!!;

    public static void main(String[] args)
    {

        String[] services = {"secured"};

        for (String service : services) {
            generate(service);
        }
    }

    private static void generate(String service)
    {

        WSDLToJava.main(new String[]{
                "-d",
                "src/main/java",
                "-p",
                "client.bank." + service,
                "http://aos.czacm.org/bank/" + service + "?wsdl"
        });
        System.out.println("Done!");
    }

}
