package core.ejb;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface Context {
    public Object get(String key);

    public void add(String key, Object obj);

    public boolean exist(String key);
}
