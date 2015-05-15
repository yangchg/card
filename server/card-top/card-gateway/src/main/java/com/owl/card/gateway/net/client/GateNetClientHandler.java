package com.owl.card.gateway.net.client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

import javax.websocket.Session;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.utils.BytesTools;
import com.owl.card.gateway.app.GateAppManager;

public class GateNetClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		TopMsg topMsg = (TopMsg) msg;

		int channelId = topMsg.getChannelId();
		Session session = GateAppManager.channelIdSessions.get(channelId);
		if (session == null) {
			assert false : "channelId 不存在:" + channelId;
			return;
		}

		// 转发给客户端
		byte[] msgBytes = topMsg.getMsgBodyBytes();

		short sendMsgType = 11;
		int bodyLen = msgBytes.length;
		int sendMsgLn = 2 + 4 + bodyLen;

		byte[] sendBytes = new byte[sendMsgLn];
		BytesTools.shortToByte(sendBytes, 0, sendMsgType);
		BytesTools.intToByte(sendBytes, 2, sendMsgLn);
		System.arraycopy(msgBytes, 0, sendBytes, 6, bodyLen);

		System.out.println("转发数据->客户端:" + Arrays.toString(sendBytes));

		try {
			ByteBuffer data = ByteBuffer.wrap(sendBytes);
			session.getBasicRemote().sendBinary(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client active ");

		super.channelActive(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client close ");
		super.channelInactive(ctx);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		// log.warn("Exception caught during RPC connection handshake.", cause);
		// ctx.close();
	}
}