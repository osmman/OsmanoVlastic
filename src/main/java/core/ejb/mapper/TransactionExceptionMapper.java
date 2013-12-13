package core.ejb.mapper;

import client.bank.secured.TransactionException;
import org.json.simple.JSONObject;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by Tomáš on 13.12.13.
 */

@Provider
public class TransactionExceptionMapper implements
        ExceptionMapper<TransactionException>
{
    @Override
    public Response toResponse(TransactionException exception)
    {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(exception.getMessage()).build();
    }
}
