package core.mapper;

import org.hibernate.validator.constraints.Email;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class EmailMapper
{
    @Email
    private String email;

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}