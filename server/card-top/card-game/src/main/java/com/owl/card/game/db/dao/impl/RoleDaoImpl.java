package com.owl.card.game.db.dao.impl;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.owl.card.common.domain.Role;
import com.owl.card.common.persistence.GameGenericDaoImpl;
import com.owl.card.game.db.dao.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl extends GameGenericDaoImpl<Role, Long> implements RoleDao {

	@Override
	public void updateLastLoginDate(final long rolId, final Date date) {
		getHibernateTemplate().execute(new HibernateCallback<Integer>() {
			public Integer doInHibernate(Session session) throws HibernateException {
				Query hqlQuery = session.createQuery("UPDATE Role SET lastLoginDate=:lastLoginDate WHERE id=:rolId");
				hqlQuery.setTimestamp("lastLoginDate", date);
				hqlQuery.setLong("rolId", rolId);
				return hqlQuery.executeUpdate();
			}
		});
	}
}
