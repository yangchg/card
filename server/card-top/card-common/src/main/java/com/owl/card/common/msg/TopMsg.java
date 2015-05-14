package com.owl.card.common.msg;

import com.google.protobuf.MessageLite;

public class TopMsg {

	public static final int MAX_MSG_TYPE = 1024;
	public static MessageLite[] prototypeMap = new MessageLite[MAX_MSG_TYPE + 1];

	private int msgType;
	private MessageLite msgBody;
	private byte[] msgBodyBytes;

	private int chId;

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

	public int getChId() {
		return chId;
	}

	public void setChId(int chId) {
		this.chId = chId;
	}

	public byte[] getMsgBodyBytes() {
		return msgBodyBytes;
	}

	public void setMsgBodyBytes(byte[] msgBodyBytes) {
		this.msgBodyBytes = msgBodyBytes;
	}

	public TopMsg() {
	}

	public TopMsg(int msgType) {
		this.msgType = msgType;
	}

	public static void registerMsg(int msgType, MessageLite messageLite) {
		if (msgType < 0 || msgType > MAX_MSG_TYPE) {
			throw new RuntimeException("proto消息注册，消息编号不合法:" + msgType);
		}

		prototypeMap[msgType] = messageLite;
	}

	public static MessageLite fetchByMsgType(int type) {
		if (type < 0 || type >= prototypeMap.length) {
			return null;
		}

		return prototypeMap[type];
	}

	public StringBuilder dumpMsg() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.msgType);
		sb.append("(");
		MessageLite ml = null;
		if (this.msgType < prototypeMap.length) {
			ml = prototypeMap[this.msgType];
			if (ml != null) {
				sb.append(ml.getClass().getSimpleName());
			} else {
				sb.append("NULL");
			}
		} else {
			sb.append("NULL");
		}

		sb.append(")");
		sb.append("\n");
		if (this.msgBody != null) {
			sb.append(this.msgBody.toString());
		}

		/*
		else if (this.msgBodyBin != null) {
			if (ml != null) {
				try {
					MessageLite mls = ml.getParserForType().parseFrom(msgBodyBin, 0, msgBodyBin.length);
					sb.append(mls.toString());
				} catch (InvalidProtocolBufferException e) {
					e.printStackTrace();
				}
			}

		}
		*/

		return sb;
	}
}
