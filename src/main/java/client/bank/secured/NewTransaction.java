
package client.bank.secured;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for newTransaction complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="newTransaction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sourceAccount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="targetAccount" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="money" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "newTransaction", propOrder = {
        "sourceAccount",
        "targetAccount",
        "money",
        "note"
})
public class NewTransaction
{

    protected long sourceAccount;

    protected long targetAccount;

    protected long money;

    protected String note;

    /**
     * Gets the value of the sourceAccount property.
     */
    public long getSourceAccount()
    {
        return sourceAccount;
    }

    /**
     * Sets the value of the sourceAccount property.
     */
    public void setSourceAccount(long value)
    {
        this.sourceAccount = value;
    }

    /**
     * Gets the value of the targetAccount property.
     */
    public long getTargetAccount()
    {
        return targetAccount;
    }

    /**
     * Sets the value of the targetAccount property.
     */
    public void setTargetAccount(long value)
    {
        this.targetAccount = value;
    }

    /**
     * Gets the value of the money property.
     */
    public long getMoney()
    {
        return money;
    }

    /**
     * Sets the value of the money property.
     */
    public void setMoney(long value)
    {
        this.money = value;
    }

    /**
     * Gets the value of the note property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Sets the value of the note property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNote(String value)
    {
        this.note = value;
    }

}
