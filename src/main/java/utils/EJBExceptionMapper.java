package utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import model.ValidationError;

@Provider
public class EJBExceptionMapper implements ExceptionMapper<EJBException> {

	@Override
	public Response toResponse(EJBException exception) {
		ConstraintViolationException validationE = findValidationException(exception);
		if (validationE != null) {
			return Response
					.status(Status.BAD_REQUEST)
					.entity(handleConstraintViolation(validationE))
					.build();
		}

		throw exception;
	}

	private ConstraintViolationException findValidationException(Throwable exception){
		while(exception != null){
			if(exception instanceof ConstraintViolationException){
				return (ConstraintViolationException) exception;
			}
			exception = exception.getCause();
		}
		
		return null;
	}
	
	private List<ValidationError> handleConstraintViolation(
			ConstraintViolationException cve) {
		List<ValidationError> errors = new LinkedList<ValidationError>();

		Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();
		for (ConstraintViolation<?> cv : cvs) {
			ValidationError error = new ValidationError();
			error.setMessage(cv.getMessage());
			error.setMessageTemplate(cv.getRootBeanClass().getSimpleName());
			error.setInvalidValue(cv.getInvalidValue().toString());
			error.setPath(cv.getPropertyPath().toString());
			errors.add(error);
		}
		return errors;
	}

}
