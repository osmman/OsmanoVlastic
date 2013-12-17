package server.print.emailsender;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.*;
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

                logger.info("Send to email: " + obj.getEmail() + "; file: ");

            } else {
                logger.warning("Message of wrong type: " + message.getClass().getName());
            }
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
