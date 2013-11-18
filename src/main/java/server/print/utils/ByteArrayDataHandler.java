package server.print.utils;

import javax.activation.DataHandler;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 15.11.13
 * Time: 14:20
 * To change this template use File | Settings | File Templates.
 */
public class ByteArrayDataHandler extends DataHandler {

    public ByteArrayDataHandler(byte[] data, String name) {
        super(new ByteArrayDataSource(data, name));
    }
}
