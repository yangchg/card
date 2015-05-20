package com.owl.card.game.net.handler;

import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.CardGroupAdd;
import com.owl.card.common.protobuf.cs.CardGroupAddRt;
import com.owl.card.common.protobuf.cs.CardGroupDel;
import com.owl.card.common.protobuf.cs.CardGroupDelRt;
import com.owl.card.common.protobuf.cs.CardGroupEdit;
import com.owl.card.common.protobuf.cs.CardGroupEditRt;
import com.owl.card.common.protobuf.cs.UserLoginC2S;
import com.owl.card.common.protobuf.cs.UserLoginS2C;

public class ProtoMsgRegister {

	public static void registerMsg() {
		TopMsg.registerMsg(10, UserLoginC2S.getDefaultInstance());
		TopMsg.registerMsg(11, UserLoginS2C.getDefaultInstance());
		TopMsg.registerMsg(20, CardGroupAdd.getDefaultInstance());
		TopMsg.registerMsg(21, CardGroupAddRt.getDefaultInstance());
		TopMsg.registerMsg(22, CardGroupEdit.getDefaultInstance());
		TopMsg.registerMsg(23, CardGroupEditRt.getDefaultInstance());
		TopMsg.registerMsg(24, CardGroupDel.getDefaultInstance());
		TopMsg.registerMsg(25, CardGroupDelRt.getDefaultInstance());
	}
}
