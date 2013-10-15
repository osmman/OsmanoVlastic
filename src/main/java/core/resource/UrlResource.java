package core.resource;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jboss.resteasy.spi.touri.ObjectToURI;
import org.jboss.resteasy.spi.touri.URITemplate;

@URITemplate("/osmanvlastic/rest/{resourceName}/{id}")
public abstract class UrlResource {
	@Transient
	private String url;
	
	public abstract Long getId();
	
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
