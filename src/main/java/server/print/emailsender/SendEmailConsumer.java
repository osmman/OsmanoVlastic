package server.print.emailsender;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.*;
import java.util.logging.Logger;

/**
 * Created by Tomáš on 13.12.13.
 */

@MessageDriven(name = "SendEmailConsumer", activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/SendEmailQueue"),
        @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class SendEmailConsumer implements MessageListener
{
    @Inject
    private Logger logger;

    @Override
    public void onMessage(Message message)
    {
        try {
            if (message instanceof ObjectMessage) {
                ObjectMessage msg = (ObjectMessage) message;
                MessageWrapper obj = (MessageWrapper) msg.getObject();

                String fileCountent = generateContent(obj.getFile());

                logger.info("Send to email: " + obj.getEmail() + "; file: " + fileCountent);
            } else {
                logger.warning("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.warning("Neusnul!");
            }
        }
    }

    public String generateContent(File file) {
        String out = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            while (in.ready()) {
                out += in.readLine().toString() + "\n";
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out;
    }
}
