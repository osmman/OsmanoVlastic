package core.resource;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import core.listener.UrlResourceListener;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.resteasy.spi.touri.ObjectToURI;
import org.jboss.resteasy.spi.touri.URITemplate;

import resource.ResourceType;

@URITemplate(ResourceType.ROOT + "/{resourceName}/{id}")
@EntityListeners({UrlResourceListener.class})
@MappedSuperclass
public abstract class UrlResource
{
    @Transient
    private String url;

    public abstract Long getId();

    @XmlElement
    public String getUrl()
    {
        return url;
    }

    @JsonIgnore
    @XmlTransient
    public String getResourceName()
    {
        return this.getClass().getSimpleName().toLowerCase();
    }

    public void loadUrl()
    {
        url = ObjectToURI.getInstance().resolveURI(this);
    }
}
