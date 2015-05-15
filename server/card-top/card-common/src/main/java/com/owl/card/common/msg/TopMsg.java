package com.owl.card.common.msg;

import com.google.protobuf.MessageLite;

public class TopMsg {

	public static final int MAX_MSG_TYPE = 1024;
	public static MessageLite[] prototypeMap = new MessageLite[MAX_MSG_TYPE + 1];

	private int msgType;
	private MessageLite msgBody;
	private byte[] msgBodyBytes;

	private int channelId;

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

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
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

	public TopMsg(int msgType, MessageLite msgBody) {
		this.msgType = msgType;
		this.msgBody = msgBody;
	}

	public TopMsg(int channelId, int msgType, MessageLite msgBody) {
		this.channelId = channelId;
		this.msgType = msgType;
		this.msgBody = msgBody;
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static class Builder {
		private int channelId;
		private int msgType;
		private MessageLite messageLite;

		public int getChannelId() {
			return channelId;
		}

		public void setChannelId(int channelId) {
			this.channelId = channelId;
		}

		public int getMsgType() {
			return msgType;
		}

		public void setMsgType(int msgType) {
			this.msgType = msgType;
		}

		public MessageLite getMessageLite() {
			return messageLite;
		}

		public void setMessageLite(MessageLite messageLite) {
			this.messageLite = messageLite;
		}

		public TopMsg build() {
			TopMsg topMsg = new TopMsg(channelId, msgType, messageLite);
			return topMsg;
		}

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
		 * else if (this.msgBodyBin != null) { if (ml != null) { try { MessageLite mls =
		 * ml.getParserForType().parseFrom(msgBodyBin, 0, msgBodyBin.length); sb.append(mls.toString()); } catch
		 * (InvalidProtocolBufferException e) { e.printStackTrace(); } }
		 * 
		 * }
		 */

		return sb;
	}
}
