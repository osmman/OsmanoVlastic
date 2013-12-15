package model;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 25.11.13
 * Time: 13:13
 * To change this template use File | Settings | File Templates.
 */
@XmlRootElement
public class Payment
{
    @NotNull
    @Pattern(regexp = "^[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}$")
    @XmlElement(name = "bank-account")
    @JsonProperty(value = "account-number")
    private String accountNumber;

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
}
