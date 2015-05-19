package com.owl.card.game.app;

import javax.annotation.Resource;

import com.owl.card.game.db.service.game.AccountService;
import com.owl.card.game.db.service.game.RoleService;

public class GameServerInit {

	@Resource
	AccountService accountService;
	@Resource
	RoleService roleService;

	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public void init() {

	}
}
