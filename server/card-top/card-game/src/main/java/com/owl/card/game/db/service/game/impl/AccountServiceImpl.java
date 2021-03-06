package com.owl.card.game.db.service.game.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.card.common.domain.Account;
import com.owl.card.game.db.dao.AccountDao;
import com.owl.card.game.db.service.game.AccountService;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDao accountDao;

	@Override
	public Account findAccByName(String accName) {
		return accountDao.findByName(accName);
	}

	@Override
	public long createAccount(Account account) {
		account.setRegTime(new Date());
		return accountDao.save(account);
	}

}
