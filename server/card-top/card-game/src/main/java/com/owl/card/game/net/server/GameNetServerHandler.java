package com.owl.card.game.net.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.google.protobuf.MessageLite;
import com.owl.card.common.msg.TopMsg;

public class GameNetServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

		// ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

		// super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg == null) {
			assert false : "读取消息为空";
			return;
		}

		TopMsg topMsg = (TopMsg) msg;
		//int channelId = topMsg.getChId();
		int msgType = topMsg.getMsgType();

		MessageLite messageLite = TopMsg.fetchByMsgType(msgType);
		if (messageLite == null) {
			assert false : "消息未注册:" + msgType;
			return;
		}

		topMsg.setMsgBody(messageLite);

		System.out.println("收到客户端消息:" + topMsg.dumpMsg());

		// System.out.println(ctx.channel().remoteAddress() + " Say : " + msg.toString());

		// 返回客户端消息 - 我已经接收到了你的消息
		// ctx.writeAndFlush("Received your message !\n");

		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable e) {
		// 异常
		System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " 网络异常:" + e.toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		// 断开连接
		System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " 断开 !");
	}

}
