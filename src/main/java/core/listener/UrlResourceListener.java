package core.listener;

import core.resource.UrlResource;

import javax.persistence.PostLoad;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 25.11.13
 * Time: 23:24
 * To change this template use File | Settings | File Templates.
 */
public class UrlResourceListener
{
    @PostLoad
    public void load(UrlResource entity)
    {
        entity.loadUrl();
    }
}
