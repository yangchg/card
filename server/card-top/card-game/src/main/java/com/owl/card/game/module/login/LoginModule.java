package com.owl.card.game.module.login;

import org.apache.log4j.Logger;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.define.ClientMsgTypeDefine;
import com.owl.card.common.domain.Account;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginC2S;
import com.owl.card.common.protobuf.cs.UserLoginS2C;
import com.owl.card.game.db.service.game.AccountService;
import com.owl.card.game.manager.GameCallbackManager;
import com.owl.card.game.module.login.interfaces.LoginModuleInterface;
import com.owl.card.game.obj.Role;

public class LoginModule extends BaseModule implements LoginModuleInterface {

	private final static Logger logger = Logger.getLogger(LoginModule.class);

	private AccountService accountService;

	public LoginModule(AccountService accountService) {
		this.accountService = accountService;
	}

	@Override
	public void onInit() {
		GameCallbackManager.registerClientMsg(ClientMsgTypeDefine.userLoginC2S, this.getClass(), "onRoleLogin");
	}

	@Override
	public void onWorkerInit() {

	}

	/**
	 * 玩家登陆
	 * 
	 * @param role
	 * @param topMsg
	 */
	public void onRoleLogin(Role role, TopMsg topMsg) {

		UserLoginC2S userLoginC2S = (UserLoginC2S) topMsg.getMsgBody();
		int accid = userLoginC2S.getAccid();
		int tstamp = userLoginC2S.getTstamp();
		String ticket = userLoginC2S.getTicket();

		String accName = Integer.toString(accid);
		Account account = accountService.findAccByName(accName);
		if (account == null) {
			// 没有账号，新建账号
			account = new Account(accName, "", "");
			long accId = accountService.createAccount(account);
			account.setId(accId);
		}
		
		

		logger.info("玩家登陆　accid:" + accid + ", tstamp" + tstamp + ", ticket：" + ticket);

		UserLoginS2C.Builder userLoginS2C = UserLoginS2C.newBuilder();
		userLoginS2C.setFlag(1);

		TopMsg.Builder sendTopMsgBuilder = TopMsg.newBuilder();
		sendTopMsgBuilder.setMsgType(11);
		sendTopMsgBuilder.setMessageLite(userLoginS2C.build());
		TopMsg sendTopMsg = sendTopMsgBuilder.build();
		role.sendMsg(sendTopMsg);
	}
}
