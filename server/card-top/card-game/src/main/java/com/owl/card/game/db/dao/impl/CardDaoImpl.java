package com.owl.card.game.db.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.owl.card.common.domain.Card;
import com.owl.card.common.persistence.GameGenericDaoImpl;
import com.owl.card.game.db.dao.CardDao;

@Repository("cardDao")
public class CardDaoImpl extends GameGenericDaoImpl<Card, Long> implements CardDao {

	@Override
	public Card findByProtoId(final long roleId, final int cardProtoId) {
		List<Card> accounts = getHibernateTemplate().execute(new HibernateCallback<List<Card>>() {
			@SuppressWarnings("unchecked")
			public List<Card> doInHibernate(Session session) throws HibernateException {
				Query hqlQuery = session.createQuery("from Card p where p.roleId=:roleId AND p.protoId=:protoId");
				hqlQuery.setLong("roleId", roleId);
				hqlQuery.setInteger("protoId", cardProtoId);
				return (List<Card>) hqlQuery.list();
			}
		});

		if (accounts.size() != 0) {
			return accounts.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Card> findByRoleId(final long roleId) {
		List<Card> cards = getHibernateTemplate().execute(new HibernateCallback<List<Card>>() {
			@SuppressWarnings("unchecked")
			public List<Card> doInHibernate(Session session) throws HibernateException {
				Query hqlQuery = session.createQuery("from Card p where p.roleId=:roleId");
				hqlQuery.setLong("roleId", roleId);
				return (List<Card>) hqlQuery.list();
			}
		});

		return cards;
	}
}
