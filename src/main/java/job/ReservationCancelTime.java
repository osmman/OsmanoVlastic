package job;

import core.query.WhereBuilder;
import model.Flight;
import model.Reservation;
import model.StateChoices;
import service.ReservationService;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 20.11.13
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */

@Singleton
@Startup
public class ReservationCancelTime
{

    public static final int TIMEOUT_HOURS = 2;

    @Inject
    private Logger logger;

    @Inject
    private ReservationService service;

    @Schedule(hour = "*", minute = "*")
    public void run()
    {
        logger.info("Job: Reservation cancel time");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - TIMEOUT_HOURS);
        final Date date = calendar.getTime();

        WhereBuilder<Reservation> whereBuilder = new WhereBuilder<Reservation>()
        {

            @Override
            public Predicate build(CriteriaQuery<Reservation> cq, CriteriaBuilder cb, Root<Reservation> root)
            {
                List<Predicate> predicates = new ArrayList<Predicate>();

                predicates.add(cb.lessThan(root.<Date>get("created"), date));
                predicates.add(cb.equal(root.<StateChoices>get("state"), StateChoices.NEW));

                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        Collection<Reservation> forCancel = service.findAll(null, null, null, whereBuilder);

        for (Reservation reservation : forCancel) {
            service.cancelReservation(reservation);
            logger.info("Reservation " + reservation.getId() + " canceled");
        }
    }
}
