package com.owl.card.game.module.card.resp;

import java.util.List;

import com.owl.card.common.define.ServerMsgTypeDefine;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.CardGroupAddRt;

public class CardGroupAddResp {

	CardGroupAddRt.Builder cardGroupAddRt = CardGroupAddRt.newBuilder();

	public CardGroupAddResp(int rt) {
		cardGroupAddRt.setRt(rt);
	}

	public void addAddRtInfo(int hero, String groupName, List<Integer> cardProtoIds) {
		cardGroupAddRt.setHero(hero);
		cardGroupAddRt.setGroupName(groupName);
		cardGroupAddRt.addAllCardProtoId(cardProtoIds);
	}

	public TopMsg build() {
		TopMsg.Builder warMessage = TopMsg.newBuilder();
		warMessage.setMsgType(ServerMsgTypeDefine.cardGroupAddRt);
		warMessage.setMessageLite(cardGroupAddRt.build());
		return warMessage.build();
	}
}
