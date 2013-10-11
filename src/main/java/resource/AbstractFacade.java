package resource;

import java.net.URI;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.Query;

import utils.QueryBuilder;
import model.UrlResource;

public abstract class AbstractFacade<T extends UrlResource> {

	private Class<T> entityClass;

	public AbstractFacade(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

	protected abstract EntityManager getEntityManager();

	public void create(T entity) {
		getEntityManager().persist(entity);
	}

	public void edit(T entity) {
		getEntityManager().merge(entity);
	}

	public void remove(T entity) {
		getEntityManager().remove(getEntityManager().merge(entity));
	}

	public T find(Object id) {
		T item = getEntityManager().find(entityClass, id);
		if (item == null) {
			return null;
		}

		item.setUrl(basePath(item));
		return item;
	}

	public List<T> findAll(String order, Integer base, Integer offset) {
		QueryBuilder<T> builder = new QueryBuilder<T>(entityClass, getEntityManager());
		builder.setOrder(order);
		builder.setBase(base);
		builder.setOffset(offset);
		Query q = builder.build();
		List<T> results = q.getResultList();

		for (T item : results) {
			item.setUrl(basePath(item));
		}

		return results;
	}

	public int count() {
		CriteriaQuery<Object> cq = getEntityManager()
				.getCriteriaBuilder().createQuery();
		javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
		cq.select(getEntityManager().getCriteriaBuilder().count(rt));
		Query q = getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).intValue();
	}

	protected abstract URI basePath(T item);

}
