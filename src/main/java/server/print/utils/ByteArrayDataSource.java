package server.print.utils;

import javax.activation.DataSource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: usul
 * Date: 15.11.13
 * Time: 14:15
 * To change this template use File | Settings | File Templates.
 */
public class ByteArrayDataSource implements DataSource {

    private InputStream is;

    private String contentType;

    private String name;

    public ByteArrayDataSource(byte[] array, String name) {
        this.is = new ByteArrayInputStream(array);
        this.contentType = "application/octet-stream";
        this.name = name;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return is;
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getName() {
        return name;
    }
}
