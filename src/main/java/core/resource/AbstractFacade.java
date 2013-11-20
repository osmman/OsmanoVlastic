package core.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.transaction.UserTransaction;

import org.jboss.resteasy.spi.NotFoundException;

import model.Destination;
import core.query.QueryBuilder;
import core.query.WhereBuilder;

public abstract class AbstractFacade<T>
{

    private Class<T> entityClass;

    public AbstractFacade(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity)
    {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        getEntityManager().refresh(entity);
    }

    public void edit(T entity)
    {
        getEntityManager().merge(entity);
        getEntityManager().flush();
        getEntityManager().refresh(entity);
    }

    public void remove(T entity)
    {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public T find(Object id)
    {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll(String order, Integer base, Integer offset)
    {
        return findAll(order, base, offset, null);
    }

    public List<T> findAll(String order, Integer base, Integer offset,
                           WhereBuilder<T> whereBuilder)
    {
        QueryBuilder<T> builder = new QueryBuilder<T>(entityClass,
                getEntityManager());
        builder.setOrder(order);
        builder.setBase(base);
        builder.setOffset(offset);
        builder.setWhere(whereBuilder);
        Query q = builder.build();
        return q.getResultList();
    }

    public boolean contains(T entity)
    {
        return this.getEntityManager().contains(entity);
    }

    public int count()
    {
        return count(null);
    }

    public int count(WhereBuilder<T> whereBuilder)
    {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(entityClass);
        Root<T> rt = cq.from(entityClass);
        cq.select((Selection<? extends T>) cb.count(rt));
        if (whereBuilder != null) {
            cq.where(whereBuilder.build(cq, cb, rt));
        }
        Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    protected void testExistence(Object item)
    {
        if (item == null) {
            throw new NotFoundException("Object not found.");
        }
    }

}
