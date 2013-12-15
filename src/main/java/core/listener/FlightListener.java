package core.listener;

import core.ejb.Context;
import model.Flight;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 12:42
 * To change this template use File | Settings | File Templates.
 */
public class FlightListener
{

    private BeanManager getBeanManager()
    {
        try {
            InitialContext initialContext = new InitialContext();
            return (BeanManager) initialContext.lookup("java:comp/BeanManager");
        } catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Context lookupContext()
    {
        BeanManager bm = getBeanManager();
        Bean<Context> bean = (Bean<Context>) bm.getBeans(Context.class).iterator().next();
        CreationalContext<Context> ctx = bm.createCreationalContext(bean);
        return (Context) bm.getReference(bean, Context.class, ctx);
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void load(Flight entity) throws NamingException
    {
        Context context = lookupContext();

        if (context.exist("ExchangeRate")) {
            entity.setRate((Double) context.get("ExchangeRate"));
        }
    }
}
