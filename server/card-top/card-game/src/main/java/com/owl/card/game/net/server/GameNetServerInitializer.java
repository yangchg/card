package com.owl.card.game.net.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.owl.card.game.net.handler.TopMsgProtobufDecoder;
import com.owl.card.game.net.handler.TopMsgProtobufEncoder;

public class GameNetServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(128 * 1024 * 1024, 0, 4, 0, 4, true));

		pipeline.addLast("protobufDecoder", new TopMsgProtobufDecoder());

		// 编码
		// pipeline.addLast("frameEncoder", new LengthFieldPrepender(4, true));
		pipeline.addLast("protobufEncoder", new TopMsgProtobufEncoder());

		// 自己的逻辑Handler
		pipeline.addLast("handler", new GameNetServerHandler());

	}
}