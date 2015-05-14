package com.owl.card.game.net.handler;

import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class TopMsgProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		byte[] bytes = in.array();
		System.out.println("TopMsgProtobufDecoder:" + Arrays.toString(bytes));

	}
}
