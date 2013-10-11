package model;

import java.net.URI;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;

public abstract class UrlResource {
	@Transient
	private URI url;
	
	public URI getUrl() {
		return url;
	}
	public void setUrl(URI url) {
		this.url = url;
	}
}
