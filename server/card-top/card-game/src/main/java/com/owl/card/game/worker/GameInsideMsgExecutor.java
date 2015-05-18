package com.owl.card.game.worker;

import java.util.Map;

import com.owl.card.common.base.BaseWorker;
import com.owl.card.common.define.InsideMsgTypeDefine;
import com.owl.card.common.msg.InsideMsgWrap;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.task.InsideMsgExecutor;
import com.owl.card.game.manager.AppGameMaster;
import com.owl.card.game.obj.GameSession;

public class GameInsideMsgExecutor extends InsideMsgExecutor {

	/**
	 * 处理网络消息。
	 * 
	 */
	@Override
	public void dispatchMsg(InsideMsgWrap insideMsgWrap) {
		int insideMsgType = insideMsgWrap.getInsideMsgType();
		if (insideMsgType == InsideMsgTypeDefine.CLINET2SERVER_MSG) {
			dispathClientMsg(insideMsgWrap.getClientMsg());
		} else if (insideMsgType == InsideMsgTypeDefine.SERVER2SERVER_MSG) {

		}
	}

	/**
	 * 客户端请求消息处理
	 * 
	 * @param topMsg
	 */
	private void dispathClientMsg(TopMsg topMsg) {
		BaseWorker baseWorker = BaseWorker.currentWorker();
		if (baseWorker == null || !(baseWorker instanceof GameWorker)) {
			assert false : "当前woker不是游戏服务器";
			return;
		}

		GameWorker gameWorker = (GameWorker) baseWorker;
		Map<Integer, GameSession> roleMap = gameWorker.getRoleMap();
		int channelId = topMsg.getChannelId();

		GameSession role = roleMap.get(channelId);
		if (role == null) {
			// 获取缓存数据
			role = new GameSession(channelId);
		}

		int msgType = topMsg.getMsgType();
		AppGameMaster.callbackManager.callCallbackFunc(msgType, role, topMsg);
	}
}
