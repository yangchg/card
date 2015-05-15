package com.owl.card.game.db.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.owl.card.common.domain.Account;
import com.owl.card.common.persistence.UserGenericDaoImpl;
import com.owl.card.game.db.dao.AccountDao;

@Repository("accountDao")
public class AccountDaoImpl extends UserGenericDaoImpl<Account, Long> implements AccountDao {

	@Override
	public Account findByName(final String accName) {
		List<Account> accounts = getHibernateTemplate().execute(new HibernateCallback<List<Account>>() {
			@SuppressWarnings("unchecked")
			public List<Account> doInHibernate(Session session) throws HibernateException {
				Query hqlQuery = session.createQuery("from Account p where p.accName like :accName");
				hqlQuery.setParameter("accName", accName);
				return (List<Account>) hqlQuery.list();
			}
		});

		if (accounts.size() != 0) {
			return accounts.get(0);
		} else {
			return null;
		}
	}
}
