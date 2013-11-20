package service;

import core.resource.AbstractFacade;
import model.Reservation;
import model.StateChoices;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

/**
 * Created with IntelliJ IDEA.
 * User: Tomáš
 * Date: 20.11.13
 * Time: 14:57
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class ReservationService extends AbstractFacade<Reservation>
{
    @Inject
    private EntityManager em;

    public ReservationService()
    {
        super(Reservation.class);
    }

    @Override
    protected EntityManager getEntityManager()
    {
        return em;
    }

    public void cancelReservation(Reservation reservation)
    {
        reservation.setState(StateChoices.CANCELED);
        super.edit(reservation);
    }
}
