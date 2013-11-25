package core.validation;

import core.validation.anotation.Seats;
import model.Flight;
import model.Reservation;
import model.StateChoices;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 25.11.13
 * Time: 14:59
 * To change this template use File | Settings | File Templates.
 */
public class SeatsValidator implements ConstraintValidator<Seats, Object>
{

    private String seatsField;

    private String flightField;

    @Override
    public void initialize(Seats constraintAnnotation)
    {
        flightField = constraintAnnotation.flight();
        seatsField = constraintAnnotation.seats();
    }

    @Override
    public boolean isValid(final Object target, final ConstraintValidatorContext context)
    {

        try {
            Field fieldObj = target.getClass().getDeclaredField(flightField);
            Field fieldSeats = target.getClass().getDeclaredField(seatsField);
            fieldObj.setAccessible(true);
            fieldSeats.setAccessible(true);

            Flight flight = (Flight) fieldObj.get(target);
            Integer seats = (Integer) fieldSeats.get(target);

            int count = 0;
            for (Reservation reservation : flight.getReservations()) {
                if (reservation.getState() != StateChoices.CANCELED) {
                    count += reservation.getSeats();
                }
            }

            return flight.getSeats() >= count + seats;
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}

