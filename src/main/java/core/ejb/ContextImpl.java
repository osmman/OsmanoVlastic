package core.ejb;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */

@Stateless
public class ContextImpl implements Context {

    private Map<String, Object> context;

    @PostConstruct
    private void postConstruct() {
        context = new HashMap<String, Object>();
        System.out.println("construct");
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void add(String key, Object obj) {
        context.put(key, obj);
    }

    public boolean exist(String key) {
        return context.containsKey(key);
    }

}
