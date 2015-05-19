package com.owl.card.game.module.login.resp;

import com.owl.card.common.define.ServerMsgTypeDefine;
import com.owl.card.common.domain.Role;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginS2C;
import com.owl.card.common.protobuf.struct.RoleInfoData;

public class UserLoginS2CResp {

	UserLoginS2C.Builder userLoginS2C = UserLoginS2C.newBuilder();

	public UserLoginS2CResp(int rt) {
		userLoginS2C.setRt(rt);
	}

	public void addRoleInfo(Role role) {
		RoleInfoData.Builder info = RoleInfoData.newBuilder();
		if (role.getName() != null && role.getName().isEmpty() == false) {
			info.setName(role.getName());
		}

		info.setGold(role.getGold());
		info.setDiamond(role.getDiamond());
		info.setPoint(role.getPoint());

		userLoginS2C.setRoleInfo(info);
	}

	public TopMsg build() {
		TopMsg.Builder warMessage = TopMsg.newBuilder();
		warMessage.setMsgType(ServerMsgTypeDefine.userLoginS2C);
		warMessage.setMessageLite(userLoginS2C.build());
		return warMessage.build();
	}
}