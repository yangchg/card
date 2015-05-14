package com.owl.card.gateway.websocket;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLite;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.protobuf.cs.UserLoginC2S;
import com.owl.card.common.protobuf.cs.UserLoginS2C;
import com.owl.card.common.utils.BytesTools;
import com.owl.card.gateway.app.GateAppManager;

@ServerEndpoint(value = "/")
public class WSSocket {

	@OnOpen
	public void onWebSocketConnect(Session sess) {

		int channelId = GateAppManager.channelIdMaker.get();
		GateAppManager.clientSessions.put(channelId, sess);
		GateAppManager.sessionIds.put(sess, channelId);

		System.out.println("新客户端连接ID:" + channelId);

		// try {
		// sess.getBasicRemote().sendText("connect success.");
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	// @OnMessage
	// public void onWebSocketText(Session sess, String message)
	// {
	// System.out.println("Received TEXT message: " + message);
	//
	// try {
	// sess.getBasicRemote().sendText("hello,i am server.");
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	@OnMessage
	public void onWebSocketRecv(Session sess, ByteBuffer message) {
		if (message == null) {
			return;
		}

		Integer clientChannelId = GateAppManager.sessionIds.get(sess);
		if (clientChannelId == null) {
			return;
		}

		byte[] bytes = message.array();
		System.out.println("收到数据:" + Arrays.toString(bytes));

		short msgType = BytesTools.readShort(bytes, 0);
		int len = BytesTools.readInt(bytes, 2);
		System.out.println("消息编号:" + msgType + ",长度:" + len);

		// 消息长度|连接编号|消息体(消息编号+长度+消息体)
		int msgBodyLen = 4 + bytes.length;
		int sendMsgLen = 4 + msgBodyLen;

		byte[] sendBytes = new byte[sendMsgLen];
		BytesTools.intToByte(sendBytes, 0, 4 + bytes.length);
		BytesTools.intToByte(sendBytes, 4, clientChannelId);
		System.arraycopy(bytes, 0, sendBytes, 8, bytes.length);
		
		System.out.println("gateway->game:" + Arrays.toString(sendBytes));

		// 发送到游戏服务器
		Channel channel = GateAppManager.gameClientChannels.get(0);
		// channel.writeAndFlush(sendBytes);
		
		TopMsg topMsg = new TopMsg();
		topMsg.setMsgType(11);
		channel.writeAndFlush("1231231");
		
		
		// byte[] msgBody = BytesTools.readBytes(bytes, 6, len);
		//
		// TopMsg topMsg = new TopMsg();
		//
		// topMsg.setMsgType(msgType);
		//
		// MessageLite msgBodyB = null;
		// if (msgType == 10) {
		// try {
		// msgBodyB = UserLoginC2S.parseFrom(msgBody);
		// } catch (InvalidProtocolBufferException e) {
		// e.printStackTrace();
		// }
		// }
		//
		// if (msgBodyB != null) {
		// topMsg.setMsgBody(msgBodyB);
		// }
		//
		// if (msgBodyB != null) {
		// System.out.println("消息体:" + msgBodyB.toString());
		// }
		//
		// // 发送消息给客户端
		// // protobuf
		// UserLoginS2C.Builder testServer2Client = UserLoginS2C.newBuilder();
		// testServer2Client.setFlag(1);
		// byte[] msgBytes = testServer2Client.build().toByteArray();
		//
		// short sendMsgType = 11;
		// int bodyLen = msgBytes.length;
		// int sendMsgLn = 2 + 4 + bodyLen;
		//
		// byte[] sendBytes = new byte[sendMsgLn];
		// BytesTools.shortToByte(sendBytes, 0, sendMsgType);
		// BytesTools.intToByte(sendBytes, 2, sendMsgLn);
		// System.arraycopy(msgBytes, 0, sendBytes, 6, bodyLen);
		//
		// System.out.println("发送数据:" + Arrays.toString(sendBytes));
		//
		// try {
		// ByteBuffer data = ByteBuffer.wrap(sendBytes);
		// sess.getBasicRemote().sendBinary(data);
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
	}

	@OnClose
	public void onWebSocketClose(CloseReason reason) {
		System.out.println("Socket Closed: " + reason);
	}

	@OnError
	public void onWebSocketError(Throwable cause) {
		cause.printStackTrace(System.err);
	}
}