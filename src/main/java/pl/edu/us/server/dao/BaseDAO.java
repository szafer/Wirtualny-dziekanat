package pl.edu.us.server.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import net.sf.beanlib.hibernate3.Hibernate3DtoCopier;

import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;

public abstract class BaseDAO<K, E> extends JpaDaoSupport {
	protected Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDAO() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<E>) genericSuperclass.getActualTypeArguments()[1];
	}

	public void saveOrUpdate(E entity) {
		// if (entity.getClass().get)
	}

	public void persist(E entity) {
		getJpaTemplate().persist(entity);
	}

	public void remove(E entity) {
		getJpaTemplate().remove(entity);
	}

	public E merge(E entity) {
		return getJpaTemplate().merge(entity);
	}

	public void refresh(E entity) {
		getJpaTemplate().refresh(entity);
	}

	public E findById(K id) {
		return getJpaTemplate().find(entityClass, id);
	}

	public E flush(E entity) {
		getJpaTemplate().flush();
		return entity;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public Integer findMaxId() {
		Object res = getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("SELECT max(h.id) FROM " + entityClass.getName() + " h");
				return (Integer) q.getSingleResult();

			}

		});
		return (Integer) res;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<E> findAll() {
		Object res = getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("SELECT h FROM " + entityClass.getName() + " h");
				List<E> hibernate2dto = (List<E>) new Hibernate3DtoCopier().hibernate2dtoFully(q.getResultList());
				return hibernate2dto;
			}

		});

		return (List<E>) res;
	}

	@SuppressWarnings("unchecked")
	public Integer removeAll() {
		return (Integer) getJpaTemplate().execute(new JpaCallback() {

			public Object doInJpa(EntityManager em) throws PersistenceException {
				Query q = em.createQuery("DELETE FROM " + entityClass.getName() + " h");
				return q.executeUpdate();
			}

		});
	}

	@SuppressWarnings("deprecation")
	public EntityManager getEntityManager() {
		return getJpaTemplate().getEntityManagerFactory().createEntityManager();
	}
}