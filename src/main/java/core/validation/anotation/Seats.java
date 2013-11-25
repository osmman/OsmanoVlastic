package core.validation.anotation;

import core.validation.SeatsValidator;
import model.Flight;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 25.11.13
 * Time: 15:01
 * To change this template use File | Settings | File Templates.
 */
@Target({METHOD})
@Documented
@Constraint(validatedBy = SeatsValidator.class)
@Retention(RUNTIME)
public @interface Seats
{
    String message() default "{osmanvlastic.validation.seats.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String flight();

    String seats();

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)
    @Documented
    @interface List
    {
        Seats[] value();
    }
}
