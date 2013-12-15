package core.ejb;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 17:19
 * To change this template use File | Settings | File Templates.
 */

@RequestScoped
public class ContextImpl implements Context
{

    @Inject
    private Logger logger;

    private Map<String, Object> context;

    @PostConstruct
    private void postConstruct()
    {
        context = new HashMap<String, Object>();
        logger.info("Created");
    }

    public Object get(String key)
    {
        return context.get(key);
    }

    public void add(String key, Object obj)
    {
        context.put(key, obj);
    }

    public boolean exist(String key)
    {
        return context.containsKey(key);
    }

}
