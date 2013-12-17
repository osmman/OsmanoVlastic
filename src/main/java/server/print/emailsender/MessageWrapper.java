package server.print.emailsender;


import java.io.File;
import java.io.Serializable;

/**
 * Created by Tomáš on 17.12.13.
 */
public class MessageWrapper implements Serializable
{

    private String email;

    private File file;

    public MessageWrapper()
    {
    }

    public MessageWrapper(String email, File file)
    {
        this.email = email;
        this.file = file;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {

        return file;
    }
}
