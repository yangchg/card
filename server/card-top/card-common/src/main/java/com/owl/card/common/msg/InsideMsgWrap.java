package com.owl.card.common.msg;

public class InsideMsgWrap {

	private int insideMsgType;

	//private InsideMsg insideMsg; // 内部消息

	private TopMsg clientMsg; // 需要传递的客户端消息

	//	public InsideMsg getInsideMsg() {
	//		return insideMsg;
	//	}
	//
	//	public void setInsideMsg(InsideMsg insideMsg) {
	//		this.insideMsg = insideMsg;
	//	}

	public TopMsg getClientMsg() {
		return clientMsg;
	}

	public void setClientMsg(TopMsg clientMsg) {
		this.clientMsg = clientMsg;
	}

	public int getInsideMsgType() {
		return insideMsgType;
	}

	public void setInsideMsgType(int insideMsgType) {
		this.insideMsgType = insideMsgType;
	}

	public InsideMsgWrap(int insideMsgType) {
		this.insideMsgType = insideMsgType;
	}

}
