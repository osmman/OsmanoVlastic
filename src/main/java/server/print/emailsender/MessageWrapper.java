package server.print.emailsender;


import java.io.Serializable;

/**
 * Created by Tomáš on 17.12.13.
 */
public class MessageWrapper implements Serializable
{

    private String email;

    public MessageWrapper()
    {
    }

    public MessageWrapper(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
