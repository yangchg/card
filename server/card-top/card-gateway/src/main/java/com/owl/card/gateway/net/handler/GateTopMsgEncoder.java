package com.owl.card.gateway.net.handler;

import static io.netty.buffer.Unpooled.wrappedBuffer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.utils.BytesTools;

public class GateTopMsgEncoder extends MessageToMessageEncoder<TopMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TopMsg msg, List<Object> out) throws Exception {
		int chId = msg.getChannelId();
		int msgType = msg.getMsgType();
		byte[] msgBytes = msg.getMsgBodyBytes();

		// 消息长度|channelId|消息号|消息体长度|消息体
		int len = 4 + 2 + 4 + msgBytes.length;
		byte[] sendBytes = new byte[4 + len];

		BytesTools.intToByte(sendBytes, 0, len);
		BytesTools.intToByte(sendBytes, 4, chId);
		BytesTools.shortToByte(sendBytes, 8, (short) msgType);
		BytesTools.intToByte(sendBytes, 10, msgBytes.length);
		System.arraycopy(msgBytes, 0, sendBytes, 14, msgBytes.length);

		// System.out.println("GateTopMsgEncoder encode:" + Arrays.toString(sendBytes));

		out.add(wrappedBuffer(sendBytes));
	}
}
