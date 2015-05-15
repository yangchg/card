package com.owl.card.game.db.dao;

import com.owl.card.common.domain.Account;
import com.owl.card.common.persistence.GenericDao;

public interface AccountDao extends GenericDao<Account, Long> {

	/**
	 * 根据帐号名查找帐号。
	 * 
	 * @param accName
	 * @return 帐号
	 */
	public Account findByName(String accName);

}
