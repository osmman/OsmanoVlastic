package core.resource;

import javax.persistence.Transient;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.resteasy.spi.touri.ObjectToURI;
import org.jboss.resteasy.spi.touri.URITemplate;

import resource.ResourceType;

@URITemplate(ResourceType.ROOT+"/{resourceName}/{id}")
public abstract class UrlResource {
	@Transient
	private String url;
	
	public abstract Long getId();
	
	@XmlElement
	public String getUrl() {
		return url;
	}
	
	@JsonIgnore
	@XmlTransient
	public String getResourceName(){
		return this.getClass().getSimpleName().toLowerCase();
	}
	
	protected void loadUrl(){
		url = ObjectToURI.getInstance().resolveURI(this);
	}
}
