package com.owl.card.game.module.card;

import java.util.List;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.define.ClientErrCode;
import com.owl.card.common.define.ClientMsgTypeDefine;
import com.owl.card.common.define.HeroCareerDefine;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.CardGroupAdd;
import com.owl.card.game.manager.GameCallbackManager;
import com.owl.card.game.module.card.interfaces.CardGroupModuleInterface;
import com.owl.card.game.module.card.resp.CardGroupAddResp;
import com.owl.card.game.obj.GameSession;

/**
 * 卡组
 * 
 * @author YangChg
 *
 */
public class CardGroupModule extends BaseModule implements CardGroupModuleInterface {

	private final static int MAX_GROUP_CNT = 10;

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

		if (HeroCareerDefine.allCareers.contains(hero) == false || groupName == null) {
			CardGroupAddResp resp = new CardGroupAddResp(ClientErrCode.RT_FAIL);
			session.sendMsg(resp.build());
			return;
		}

		if (session.getGroups().size() >= MAX_GROUP_CNT) {
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

	}

	/**
	 * 编辑卡组
	 * 
	 * @param session
	 * @param topMsg
	 */
	public void onCardGroupEdit(GameSession session, TopMsg topMsg) {

	}

	/**
	 * 删除卡组
	 * 
	 * @param session
	 * @param topMsg
	 */
	public void onCardGroupDel(GameSession session, TopMsg topMsg) {

	}
}
