package com.owl.card.game.module.card;

import java.util.List;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.config.AppConfig;
import com.owl.card.common.define.ClientErrCode;
import com.owl.card.common.define.ClientMsgTypeDefine;
import com.owl.card.common.define.HeroCareerDefine;
import com.owl.card.common.domain.CardGroup;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.CardGroupAdd;
import com.owl.card.common.protobuf.cs.CardGroupDel;
import com.owl.card.common.protobuf.cs.CardGroupEdit;
import com.owl.card.game.db.service.game.CardService;
import com.owl.card.game.manager.GameCallbackManager;
import com.owl.card.game.module.card.interfaces.CardGroupModuleInterface;
import com.owl.card.game.module.card.resp.CardGroupAddResp;
import com.owl.card.game.module.card.resp.CardGroupDelResp;
import com.owl.card.game.module.card.resp.CardGroupEditResp;
import com.owl.card.game.obj.GameSession;

/**
 * 卡组
 * 
 * @author YangChg
 *
 */
public class CardGroupModule extends BaseModule implements CardGroupModuleInterface {

	private CardService cardService;

	public CardGroupModule(CardService cardService) {
		this.cardService = cardService;
	}

	@Override
	public void onInit() {
		// 添加卡组
		GameCallbackManager.registerClientMsg(ClientMsgTypeDefine.cardGroupAdd, this.getClass(), "onCardGroupAdd");
		// 编辑卡组
		GameCallbackManager.registerClientMsg(ClientMsgTypeDefine.cardGroupEdit, this.getClass(), "onCardGroupEdit");
		// 删除卡组
		GameCallbackManager.registerClientMsg(ClientMsgTypeDefine.cardGroupDel, this.getClass(), "onCardGroupDel");
	}

	@Override
	public void onWorkerInit() {

	}

	/**
	 * 添加卡组
	 * 
	 * @param session
	 * @param topMsg
	 */
	public void onCardGroupAdd(GameSession session, TopMsg topMsg) {
		CardGroupAdd msgBody = (CardGroupAdd) topMsg.getMsgBody();
		int hero = msgBody.getHero(); // 英雄
		String groupName = msgBody.getGroupName(); // 卡组名称
		List<Integer> cardProtoIds = msgBody.getCardProtoIdList(); // 卡组卡牌

		if (HeroCareerDefine.allCareers.contains(hero) == false || groupName == null || groupName.length() > 50) {
			CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.RT_FAIL);
			session.sendMsg(resp.build());
			return;
		}

		if (session.getGroups().size() >= AppConfig.cfgs.gameConfig.MAX_GROUP_CNT) {
			// 卡组数量已达上限
			CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.GROUP_COUNT_LIMIT);
			session.sendMsg(resp.build());
			return;
		}

		for (Integer cardProtoId : cardProtoIds) {
			if (session.getCardMap().containsKey(cardProtoId) == false) {
				// 卡牌不存在
				CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.CARD_IS_NOT_EXIST);
				session.sendMsg(resp.build());
				return;
			}
		}

		if (cardProtoIds.size() >= AppConfig.cfgs.gameConfig.GROUP_MAX_CARD_CNT) {
			// 卡牌数量超出上限
			CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.GROUP_CARD_CNT_LIMIT);
			session.sendMsg(resp.build());
			return;
		}

		long roleId = session.getRoleId();
		CardGroup cardGroup = cardService.addCardGroup(hero, groupName, roleId, cardProtoIds);
		if (cardGroup == null) {
			CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.RT_FAIL);
			session.sendMsg(resp.build());
			return;
		}

		session.getGroups().add(cardGroup);
		session.getGroupMap().put(cardGroup.getId(), cardGroup);

		CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.RT_SUCC);
		resp.addInfo(hero, groupName, cardProtoIds);
		session.sendMsg(resp.build());
	}

	/**
	 * 编辑卡组
	 * 
	 * @param session
	 * @param topMsg
	 */
	public void onCardGroupEdit(GameSession session, TopMsg topMsg) {
		CardGroupEdit msgBody = (CardGroupEdit) topMsg.getMsgBody();
		long groupId = msgBody.getGroupId();
		String groupName = msgBody.getGroupName();
		List<Integer> cardProtoIds = msgBody.getCardProtoIdList();

		if (groupId < 0 || groupName == null || groupName.length() > 50) {
			CardGroupEditResp resp = new CardGroupEditResp(ClientErrCode.RT_FAIL, groupId);
			session.sendMsg(resp.build());
			return;
		}

		CardGroup cardGroup = session.getGroupMap().get(groupId);
		if (cardGroup == null) {
			// 卡组不存在
			CardGroupEditResp resp = new CardGroupEditResp(ClientErrCode.GROUP_IS_NOT_EXIST, groupId);
			session.sendMsg(resp.build());
			return;
		}

		if (cardProtoIds.size() >= AppConfig.cfgs.gameConfig.GROUP_MAX_CARD_CNT) {
			// 卡牌数量超出上限
			CardGroupEditResp resp = new CardGroupEditResp(ClientErrCode.GROUP_CARD_CNT_LIMIT, groupId);
			session.sendMsg(resp.build());
			return;
		}

		CardGroup editCardGroup = cardService.editCardGroup(groupId, groupName, cardProtoIds);
		if (editCardGroup == null) {
			CardGroupEditResp resp = new CardGroupEditResp(ClientErrCode.RT_FAIL, groupId);
			session.sendMsg(resp.build());
			return;
		}

		cardGroup.setGroupName(groupName);
		cardGroup.setCards(editCardGroup.getCards());

		CardGroupEditResp resp = new CardGroupEditResp(ClientErrCode.RT_SUCC, groupId);
		resp.addInfo(groupName, cardProtoIds);
		session.sendMsg(resp.build());
	}

	/**
	 * 删除卡组
	 * 
	 * @param session
	 * @param topMsg
	 */
	public void onCardGroupDel(GameSession session, TopMsg topMsg) {
		CardGroupDel msgBody = (CardGroupDel) topMsg.getMsgBody();
		long groupId = msgBody.getGroupId();

		if (!session.getGroupMap().containsKey(groupId)) {
			// 卡组不存在
			CardGroupDelResp resp = new CardGroupDelResp(ClientErrCode.GROUP_IS_NOT_EXIST, groupId);
			session.sendMsg(resp.build());
			return;
		}

		cardService.delCardGroup(groupId);

		CardGroupDelResp resp = new CardGroupDelResp(ClientErrCode.RT_SUCC, groupId);
		session.sendMsg(resp.build());
	}
}
