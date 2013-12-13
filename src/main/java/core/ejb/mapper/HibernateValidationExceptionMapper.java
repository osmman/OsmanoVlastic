package core.ejb.mapper;

import core.message.ValidationError;
import org.hibernate.exception.ConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 25.11.13
 * Time: 21:07
 * To change this template use File | Settings | File Templates.
 */
@Provider
public class HibernateValidationExceptionMapper implements
        ExceptionMapper<ConstraintViolationException>
{
    public Response toResponse(ConstraintViolationException exception)
    {
        ValidationError error = new ValidationError();
        error.setMessage(exception.getMessage());
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(error).build();
    }

}
