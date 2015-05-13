package com.owl.card.common.msg;

import com.google.protobuf.MessageLite;

public class TopMsg {
	private int msgType;
	private MessageLite msgBody;

	public int getMsgType() {
		return msgType;
	}

	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}

	public MessageLite getMsgBody() {
		return msgBody;
	}

	public void setMsgBody(MessageLite msgBody) {
		this.msgBody = msgBody;
	}

}
