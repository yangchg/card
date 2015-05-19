package com.owl.card.game.module.login;

import java.util.Date;

import org.apache.log4j.Logger;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.config.AppConfig;
import com.owl.card.common.config.proto.CardProto;
import com.owl.card.common.define.ClientErrCode;
import com.owl.card.common.define.ClientMsgTypeDefine;
import com.owl.card.common.define.SexDefine;
import com.owl.card.common.domain.Account;
import com.owl.card.common.domain.Role;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginC2S;
import com.owl.card.game.db.service.game.AccountService;
import com.owl.card.game.db.service.game.CardService;
import com.owl.card.game.db.service.game.RoleService;
import com.owl.card.game.manager.GameCallbackManager;
import com.owl.card.game.module.login.interfaces.LoginModuleInterface;
import com.owl.card.game.module.login.resp.UserLoginS2CResp;
import com.owl.card.game.obj.GameSession;

public class LoginModule extends BaseModule implements LoginModuleInterface {

	private final static Logger logger = Logger.getLogger(LoginModule.class);

	private AccountService accountService;
	private RoleService roleService;
	private CardService cardService;

	public LoginModule(AccountService accountService, RoleService roleService, CardService cardService) {
		this.accountService = accountService;
		this.roleService = roleService;
		this.cardService = cardService;
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
	public void onRoleLogin(GameSession session, TopMsg topMsg) {

		UserLoginC2S userLoginC2S = (UserLoginC2S) topMsg.getMsgBody();
		int accid = userLoginC2S.getAccid();
		int tstamp = userLoginC2S.getTstamp();
		String ticket = userLoginC2S.getTicket();

		logger.info("玩家登陆　accid:" + accid + ", tstamp" + tstamp + ", ticket：" + ticket);

		String accName = Integer.toString(accid);
		Account account = accountService.findAccByName(accName);
		if (account == null) {
			// 没有账号，新建账号
			account = new Account(accName, "", "");
			long accId = accountService.createAccount(account);
			account.setId(accId);
		}

		long roleId = account.getId();
		Role role = roleService.findByRoleId(roleId);
		if (role == null) {
			role = new Role(roleId, SexDefine.SEX_MALE, 1, 0, 1000, 100, 0);
			roleService.createRole(role);

			// 初始化玩家数据
			initRole(role);
		} else {
			roleService.updateLastLoginDate(roleId, new Date());
		}

		// 返回客户端
		UserLoginS2CResp resp = new UserLoginS2CResp(ClientErrCode.RT_SUCC);
		if (role != null) {
			resp.addRoleInfo(role);
		}

		session.sendMsg(resp.build());
	}

	private void initRole(Role role) {
		// 赠送卡牌
		int[] giftCards = { 1, 2, 3, 4 };

		for (int cardProtoId : giftCards) {
			CardProto cardProto = AppConfig.cfgs.cardProtoCache.getById(cardProtoId);

			if (cardProto != null) {
				cardService.addCard(role.getId(), cardProtoId, 1);
			}
		}
	}
}
