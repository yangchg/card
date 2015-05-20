package com.owl.card.game.db.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.owl.card.common.domain.CardGroup;
import com.owl.card.common.persistence.GameGenericDaoImpl;
import com.owl.card.game.db.dao.CardGroupDao;

@Repository("cardGroupDao")
public class CardGroupDaoImpl extends GameGenericDaoImpl<CardGroup, Long> implements CardGroupDao {

	@Override
	public List<CardGroup> findGroupByRoleId(long roleId) {
		List<CardGroup> groups = getHibernateTemplate().execute(new HibernateCallback<List<CardGroup>>() {
			@SuppressWarnings("unchecked")
			public List<CardGroup> doInHibernate(Session session) throws HibernateException {
				Query hqlQuery = session.createQuery("from CardGroup p where p.roleId=:roleId");
				hqlQuery.setLong("roleId", roleId);
				return (List<CardGroup>) hqlQuery.list();
			}
		});

		return groups;
	}

}
