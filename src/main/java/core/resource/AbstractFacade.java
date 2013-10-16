package core.resource;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.UserTransaction;

import model.Destination;
import core.QueryBuilder;

public abstract class AbstractFacade<T> {

	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();
	
	public void create(T entity){
		getEntityManager().persist(entity);
		getEntityManager().flush();
		getEntityManager().refresh(entity);
	}

	public void edit(T entity) {
		getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public T find(Object id) {
		return getEntityManager().find(entityClass, id);
	}

	public List<T> findAll(String order, Integer base, Integer offset) {
		QueryBuilder<T> builder = new QueryBuilder<T>(entityClass,
				getEntityManager());
		builder.setOrder(order);
		builder.setBase(base);
		builder.setOffset(offset);
		Query q = builder.build();
		return  q.getResultList();
	}
	
	public boolean contains(T entity){
		return this.getEntityManager().contains(entity);
	}

	public int count() {
		CriteriaQuery<Object> cq = getEntityManager().getCriteriaBuilder()
				.createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

}
