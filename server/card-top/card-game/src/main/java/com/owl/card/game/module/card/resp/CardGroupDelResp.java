package com.owl.card.game.module.card.resp;

import com.owl.card.common.define.ServerMsgTypeDefine;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.CardGroupDelRt;

public class CardGroupDelResp {
	CardGroupDelRt.Builder cardGroupDelRt = CardGroupDelRt.newBuilder();

	public CardGroupDelResp(int rt, long groupId) {
		cardGroupDelRt.setRt(rt);
		cardGroupDelRt.setGroupId(groupId);
	}

	public TopMsg build() {
		TopMsg.Builder warMessage = TopMsg.newBuilder();
		warMessage.setMsgType(ServerMsgTypeDefine.cardGroupDelRt);
		warMessage.setMessageLite(cardGroupDelRt.build());
		return warMessage.build();
	}
}
