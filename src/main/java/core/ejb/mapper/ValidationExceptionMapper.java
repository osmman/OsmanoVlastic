package core.ejb.mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import core.message.ValidationError;

@Provider
public class ValidationExceptionMapper implements
		ExceptionMapper<ConstraintViolationException> {

	@Override
	public Response toResponse(ConstraintViolationException exception) {

		return Response.status(Status.BAD_REQUEST)
				.entity(handleConstraintViolation(exception)).build();
	}

	private List<ValidationError> handleConstraintViolation(
			ConstraintViolationException cve) {
		List<ValidationError> errors = new LinkedList<ValidationError>();

		Set<ConstraintViolation<?>> cvs = cve.getConstraintViolations();
		for (ConstraintViolation<?> cv : cvs) {
			ValidationError error = new ValidationError();
			error.setMessage(cv.getMessage());
			error.setRoot(cv.getRootBeanClass().getSimpleName());
			error.setInvalidValue(cv.getInvalidValue().toString());
			error.setProperty(cv.getPropertyPath().toString());
			errors.add(error);
		}
		return errors;
	}

}
