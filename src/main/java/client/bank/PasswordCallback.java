package client.bank;

import org.apache.ws.security.WSPasswordCallback;

import javax.security.auth.callback.*;

import java.io.IOException;

/**
 * @author Karel Cemus
 */
public class PasswordCallback implements CallbackHandler
{

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException
    {

        for (Callback callback : callbacks) {
            ((WSPasswordCallback) callback).setPassword("passwd");
        }
    }
}
