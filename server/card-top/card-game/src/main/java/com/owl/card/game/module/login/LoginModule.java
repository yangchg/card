package com.owl.card.game.module.login;

import javax.annotation.Resource;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.domain.Account;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginC2S;
import com.owl.card.game.db.service.game.AccountService;
import com.owl.card.game.module.login.interfaces.LoginModuleInterface;

public class LoginModule extends BaseModule implements LoginModuleInterface {

	@Resource
	private AccountService accountService;

	@Override
	public void onInit() {

	}

	@Override
	public void onThreadInit() {

	}

	public void onRoleLogin(TopMsg topMsg) {
		int channelId = topMsg.getChannelId();
		UserLoginC2S userLoginC2S = (UserLoginC2S) topMsg.getMsgBody();

		int accId = userLoginC2S.getAccid();
		int tstamp = userLoginC2S.getTstamp();
		String ticket = userLoginC2S.getTicket();

		String accName = Integer.toString(accId);

		Account account = accountService.findAccByName(accName);

	}
}
