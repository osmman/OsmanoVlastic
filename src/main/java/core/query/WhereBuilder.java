package core.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface WhereBuilder<T> {
	Predicate build(CriteriaQuery<T> cq, CriteriaBuilder cb, Root<T> root);
}
