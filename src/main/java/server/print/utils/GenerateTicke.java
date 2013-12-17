package server.print.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 17.12.13
 * Time: 12:37
 * To change this template use File | Settings | File Templates.
 */
public class GenerateTicke {
    public static File generate(String flightName, int seats) {
        File temp = null;
        try {
            temp = File.createTempFile("reservation", ".txt");
            temp.deleteOnExit();

            BufferedWriter out = new BufferedWriter(new FileWriter(temp));
            out.write("Rezervace letu: ");
            out.write(flightName + "\n");
            out.write("Pocet sedadel: ");
            out.write(seats + "");

            out.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return temp;
    }
}
