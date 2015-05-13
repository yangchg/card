package com.owl.card.game.net.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GameNetServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// 解码器
		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(128 * 1024 * 1024, 0, 4, -4, 4, true));

		// 字符串解码 和 编码
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// 自己的逻辑Handler
		pipeline.addLast("handler", new GameNetServerHandler());

	}
}