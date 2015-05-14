package com.owl.card.game.net.handler;

import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginC2S;
import com.owl.card.common.protobuf.cs.UserLoginS2C;

public class ProtoMsgRegister {

	public static void registerMsg() {
		TopMsg.registerMsg(10, UserLoginC2S.getDefaultInstance());
		TopMsg.registerMsg(11, UserLoginS2C.getDefaultInstance());
	}
}
