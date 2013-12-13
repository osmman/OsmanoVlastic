
package client.bank.secured;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the client.bank.secured package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory
{

    private final static QName _TransactionFault_QNAME = new QName("http://centralbank.org/", "TransactionFault");

    private final static QName _NewTransaction_QNAME = new QName("http://centralbank.org/", "newTransaction");

    private final static QName _NewTransactionResponse_QNAME = new QName("http://centralbank.org/", "newTransactionResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: client.bank.secured
     */
    public ObjectFactory()
    {
    }

    /**
     * Create an instance of {@link NewTransaction }
     */
    public NewTransaction createNewTransaction()
    {
        return new NewTransaction();
    }

    /**
     * Create an instance of {@link TransactionFault }
     */
    public TransactionFault createTransactionFault()
    {
        return new TransactionFault();
    }

    /**
     * Create an instance of {@link NewTransactionResponse }
     */
    public NewTransactionResponse createNewTransactionResponse()
    {
        return new NewTransactionResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionFault }{@code >}}
     */
    @XmlElementDecl(namespace = "http://centralbank.org/", name = "TransactionFault")
    public JAXBElement<TransactionFault> createTransactionFault(TransactionFault value)
    {
        return new JAXBElement<TransactionFault>(_TransactionFault_QNAME, TransactionFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewTransaction }{@code >}}
     */
    @XmlElementDecl(namespace = "http://centralbank.org/", name = "newTransaction")
    public JAXBElement<NewTransaction> createNewTransaction(NewTransaction value)
    {
        return new JAXBElement<NewTransaction>(_NewTransaction_QNAME, NewTransaction.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NewTransactionResponse }{@code >}}
     */
    @XmlElementDecl(namespace = "http://centralbank.org/", name = "newTransactionResponse")
    public JAXBElement<NewTransactionResponse> createNewTransactionResponse(NewTransactionResponse value)
    {
        return new JAXBElement<NewTransactionResponse>(_NewTransactionResponse_QNAME, NewTransactionResponse.class, null, value);
    }

}
