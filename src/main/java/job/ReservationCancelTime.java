package job;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    @Schedule(hour = "*", minute = "*")
    public void run()
    {
        logger.info("Job: Reservation cancel time");
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - TIMEOUT_HOURS);
        Date date = calendar.getTime();


    }
}
