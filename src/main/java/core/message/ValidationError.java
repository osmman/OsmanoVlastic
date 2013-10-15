package core.message;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class ValidationError {
 
    private String message;
 
    private String root;
 
    private String property;
 
    private String invalidValue;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(String invalidValue) {
		this.invalidValue = invalidValue;
	}
 
    
}
