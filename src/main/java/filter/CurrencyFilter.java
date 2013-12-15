package filter;

import client.exchange.RateExchangeClient;
import core.ejb.Context;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 9.11.13
 * Time: 16:24
 * To change this template use File | Settings | File Templates.
 */
public class CurrencyFilter implements Filter
{

    @Inject
    private RateExchangeClient rateExchangeClient;

    @Inject
    private Context context;

    @Inject
    private Logger logger;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        String currency = ((HttpServletRequest) request).getHeader("X-Currency");
        if (currency != null && !currency.isEmpty()) {
            Double rate = rateExchangeClient.getCurrency("CZK", currency);
            context.add("ExchangeRate", rate);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy()
    {
    }
}
