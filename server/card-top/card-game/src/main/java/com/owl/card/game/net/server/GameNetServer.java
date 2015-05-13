package com.owl.card.game.net.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class GameNetServer {

	private ServerBootstrap bootstrap;

	private final static int GAME_PORT = 8401;

	public void start() {

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup);
			bootstrap.channel(NioServerSocketChannel.class);
			bootstrap.childHandler(new GameNetServerInitializer());

			// 服务器绑定端口监听
			ChannelFuture f = bootstrap.bind(GAME_PORT).sync();
			// 监听服务器关闭监听
			f.channel().closeFuture().sync();

			// 可以简写为
			/* b.bind(portNumber).sync().channel().closeFuture().sync(); */
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/*
		finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		*/

	}
}
