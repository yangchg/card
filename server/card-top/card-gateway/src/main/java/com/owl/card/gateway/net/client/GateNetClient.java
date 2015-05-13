package com.owl.card.gateway.net.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class GateNetClient {

	public Channel connect(final String ip, final int port) {

		Channel ch = null;

		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(new GateNetClientInitializer());

		// 连接服务端
		try {
			ch = b.connect(ip, port).sync().channel();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return ch;
	}

}
