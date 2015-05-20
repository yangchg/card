package com.owl.card.game.module.login.resp;

import java.util.List;

import com.owl.card.common.define.ServerMsgTypeDefine;
import com.owl.card.common.domain.Card;
import com.owl.card.common.domain.CardGroup;
import com.owl.card.common.domain.Role;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginS2C;
import com.owl.card.common.protobuf.struct.CardGroupInfo;
import com.owl.card.common.protobuf.struct.CardInfo;
import com.owl.card.common.protobuf.struct.RoleInfoData;

public class UserLoginS2CResp {

	UserLoginS2C.Builder userLoginS2C = UserLoginS2C.newBuilder();

	public UserLoginS2CResp(int rt) {
		userLoginS2C.setRt(rt);
	}

	public void addRoleInfo(Role role, List<Card> cards, List<CardGroup> cardGroups) {
		RoleInfoData.Builder info = RoleInfoData.newBuilder();
		if (role.getName() != null && role.getName().isEmpty() == false) {
			info.setName(role.getName());
		}

		info.setGold(role.getGold());
		info.setDiamond(role.getDiamond());
		info.setPoint(role.getPoint());

		// 卡牌信息
		if (cards != null && !cards.isEmpty()) {
			for (Card card : cards) {
				CardInfo.Builder cardB = CardInfo.newBuilder();
				cardB.setProtoId(card.getProtoId());
				cardB.setNum(card.getNum());
				info.addCard(cardB);
			}
		}

		// 卡组信息
		if (cardGroups != null && !cardGroups.isEmpty()) {
			for (CardGroup cardGroup : cardGroups) {
				CardGroupInfo.Builder groupB = CardGroupInfo.newBuilder();
				groupB.setGroupId(cardGroup.getId());
				groupB.setGroupName(cardGroup.getGroupName());
				groupB.setHeroCareer(cardGroup.getHeroCareer());
				groupB.addAllCardProtoId(cardGroup.getCards());
			}
		}

		userLoginS2C.setRoleInfo(info);
	}

	public TopMsg build() {
		TopMsg.Builder warMessage = TopMsg.newBuilder();
		warMessage.setMsgType(ServerMsgTypeDefine.userLoginS2C);
		warMessage.setMessageLite(userLoginS2C.build());
		return warMessage.build();
	}
}