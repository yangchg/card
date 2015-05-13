package com.owl.card.game.net.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameNetServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		System.out.println("RamoteAddress : " + ctx.channel().remoteAddress() + " active !");

		// ctx.writeAndFlush("Welcome to " + InetAddress.getLocalHost().getHostName() + " service!\n");

		// super.channelActive(ctx);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		// 收到消息直接打印输出
		System.out.println(ctx.channel().remoteAddress() + " Say : " + msg.toString());

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
