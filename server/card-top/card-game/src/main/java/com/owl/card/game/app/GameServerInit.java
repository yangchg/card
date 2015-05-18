package com.owl.card.game.app;

import javax.annotation.Resource;

import com.owl.card.game.db.service.game.AccountService;

public class GameServerInit {

	@Resource
	AccountService accountService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void init() {

	}
}
