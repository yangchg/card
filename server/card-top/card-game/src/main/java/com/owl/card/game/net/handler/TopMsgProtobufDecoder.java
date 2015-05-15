package com.owl.card.game.net.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.Arrays;
import java.util.List;

import com.owl.card.common.msg.TopMsg;

public class TopMsgProtobufDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		int chId = in.readInt();
		int msgType = in.readShort();
		int msgLen = in.readInt();

		byte[] msgBodyBytes = new byte[msgLen];
		in.readBytes(msgBodyBytes, 0, msgLen);

		System.out.println("TopMsgProtobufDecoder:" + Arrays.toString(msgBodyBytes));

		TopMsg topMsg = new TopMsg(msgType);
		topMsg.setChannelId(chId);
		topMsg.setMsgType(msgType);
		topMsg.setMsgBodyBytes(msgBodyBytes);

		out.add(topMsg);
	}
}
