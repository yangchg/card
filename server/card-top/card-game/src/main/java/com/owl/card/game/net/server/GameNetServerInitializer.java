package com.owl.card.game.net.server;

import com.owl.card.game.net.handler.TopMsgProtobufDecoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class GameNetServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		// 解码器
		pipeline.addLast("protobufDecoder", new TopMsgProtobufDecoder());

		//pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(128 * 1024 * 1024, 0, 4, 0, 4, true));

		// 字符串解码 和 编码
		// pipeline.addLast("decoder", new StringDecoder());
		// pipeline.addLast("encoder", new StringEncoder());

		// 自己的逻辑Handler
		pipeline.addLast("handler", new GameNetServerHandler());

	}
}