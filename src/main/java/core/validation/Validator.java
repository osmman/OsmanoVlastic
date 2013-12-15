package core.validation;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 25.11.13
 * Time: 14:18
 * To change this template use File | Settings | File Templates.
 */
public class Validator
{
    @Inject
    private javax.validation.Validator validator;

    /**
     * @param object
     * @throws ConstraintViolationException
     */
    public <T> void validate(T object)
    {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(object);

        if (constraintViolations.size() > 0) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }


}
