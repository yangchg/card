package com.owl.card.common.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

public class UserGenericDaoImpl<T extends Domain<?>, ID extends Serializable> extends HibernateDaoSupport implements
		GenericDao<T, ID> {

	private Class<T> entityClass;
	@SuppressWarnings("unused")
	private String entityClassName;

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public UserGenericDaoImpl() {
		entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		entityClassName = entityClass.getSimpleName();
	}

	// 为父类HibernateDaoSupport注入sessionFactory的值
	@Resource
	@Qualifier(value = "userSessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public void saveOrUpdate(T instance) {
		getHibernateTemplate().saveOrUpdate(instance);
	}

	@Override
	public void delete(T persistentInstance) {
		getHibernateTemplate().delete(persistentInstance);
	}

	@Override
	public List<T> findAll() {
		return (List<T>) getHibernateTemplate().loadAll(entityClass);
	}

	@Override
	public T findById(ID id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public ID save(Domain<?> transientInstance) {
		return (ID) getHibernateTemplate().save(transientInstance);
	}

	@Override
	public void update(T transientInstance) {
		getHibernateTemplate().update(transientInstance);
	}

	@Override
	public void flush() {
		getHibernateTemplate().flush();
	}

	@Override
	public void clear() {
		getHibernateTemplate().clear();
	}

	@Override
	public long getLatestId() {
		Object idObj = getHibernateTemplate().execute(new HibernateCallback<Object>() {
			public Object doInHibernate(Session session) throws HibernateException {
				String entityClassName = entityClass.getSimpleName();
				String hql = "select max(id) from " + entityClassName;

				Query query = session.createQuery(hql);
				return query.uniqueResult();
			}
		});
		long maxId = 1L;
		if (idObj == null) {
			maxId = 1L;
		} else {
			maxId = (Long) idObj >> IdDefine.ID_OFFSET;
		}

		return maxId + 1;
	}

	@Override
	public void deleteById(final ID id) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				String entityClassName = entityClass.getSimpleName();
				String hql = "DELETE FROM " + entityClassName + " WHERE id = :id";

				Query query = session.createQuery(hql);
				query.setLong("id", (Long) id);

				return query.executeUpdate();
			}
		});
	}

	@Override
	public void opeateServerId() {

	}

}
