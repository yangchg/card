package com.owl.card.game.db.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.owl.card.common.domain.GroupCard;
import com.owl.card.common.persistence.GameGenericDaoImpl;
import com.owl.card.game.db.dao.GroupCardDao;

@Repository("groupCardDao")
public class GroupCardDaoImpl extends GameGenericDaoImpl<GroupCard, Long> implements GroupCardDao {

	@Override
	public List<GroupCard> findCardByGroupId(Set<Long> groupIds) {
		List<GroupCard> groupCards = getHibernateTemplate().execute(new HibernateCallback<List<GroupCard>>() {
			@SuppressWarnings("unchecked")
			public List<GroupCard> doInHibernate(Session session) throws HibernateException {
				Query hqlQuery = session.createQuery("from GroupCard p where p.groupId in (:groupIds)");
				hqlQuery.setParameterList("groupIds", groupIds);
				return (List<GroupCard>) hqlQuery.list();
			}
		});

		return groupCards;
	}

}
