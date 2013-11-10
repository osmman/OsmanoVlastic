package core.listener;

import core.ejb.Context;
import model.Flight;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PostLoad;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class FlightListener {

    @PostLoad
    public void load(Flight entity) throws NamingException {
        Context context = (Context) new InitialContext().lookup("java:module/ContextImpl");

        if (context.exist("ExchangeRate")) {
            entity.setRate((Double) context.get("ExchangeRate"));
        }
    }
}
