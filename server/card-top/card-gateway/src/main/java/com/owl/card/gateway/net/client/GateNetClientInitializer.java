package com.owl.card.gateway.net.client;

import com.owl.card.gateway.net.handler.GateTopMsgEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class GateNetClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(128 * 1024 * 1024, 0, 4, 0, 4, true));

		pipeline.addLast("encoder", new GateTopMsgEncoder());

		pipeline.addLast("handler", new GateNetClientHandler());
	}
}