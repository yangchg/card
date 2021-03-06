package com.owl.card.game.db.service.game;

import com.owl.card.common.domain.Account;

public interface AccountService {

	/**
	 * 获取账号
	 * 
	 * @param accName
	 * @return
	 */
	public Account findAccByName(String accName);

	/**
	 * 新建账号
	 * 
	 * @param account
	 * @return 
	 */
	public long createAccount(Account account);
}
