package com.owl.card.gateway.net.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import com.owl.card.gateway.net.handler.GateTopMsgDecoder;
import com.owl.card.gateway.net.handler.GateTopMsgEncoder;

public class GateNetClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();

		pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(128 * 1024 * 1024, 0, 4, 0, 4, true));

		pipeline.addLast("encoder", new GateTopMsgEncoder());
		pipeline.addLast("decoder", new GateTopMsgDecoder());

		pipeline.addLast("handler", new GateNetClientHandler());
	}
}