package com.owl.card.game.module.card.resp;

import java.util.List;

import com.owl.card.common.define.ServerMsgTypeDefine;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.CardGroupEditRt;

public class CardGroupEditResp {

	CardGroupEditRt.Builder cardGroupEditRt = CardGroupEditRt.newBuilder();

	public CardGroupEditResp(int rt, long groupId) {
		cardGroupEditRt.setRt(rt);
		cardGroupEditRt.setGroupId(groupId);
	}

	public void addInfo(String groupName, List<Integer> cardProtoIds) {
		cardGroupEditRt.setGroupName(groupName);
		cardGroupEditRt.addAllCardProtoId(cardProtoIds);
	}

	public TopMsg build() {
		TopMsg.Builder warMessage = TopMsg.newBuilder();
		warMessage.setMsgType(ServerMsgTypeDefine.cardGroupEditRt);
		warMessage.setMessageLite(cardGroupEditRt.build());
		return warMessage.build();
	}
}
