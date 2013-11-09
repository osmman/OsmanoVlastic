package client.exchange;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 11:11
 * To change this template use File | Settings | File Templates.
 */
public interface RateExchangeClient {
    Double getCurrency(String from, String to);
}
