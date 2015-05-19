package com.owl.card.game.app;

import javax.annotation.Resource;

import com.owl.card.game.db.service.game.AccountService;
import com.owl.card.game.db.service.game.CardService;
import com.owl.card.game.db.service.game.RoleService;

public class GameServerInit {

	@Resource
	AccountService accountService;
	@Resource
	RoleService roleService;
	@Resource
	CardService cardService;

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

	public CardService getCardService() {
		return cardService;
	}

	public void setCardService(CardService cardService) {
		this.cardService = cardService;
	}

	public void init() {

	}
}
