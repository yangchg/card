package com.owl.card.game.net.handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.Arrays;
import java.util.List;

import com.google.protobuf.MessageLite;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.common.utils.BytesTools;

public class TopMsgProtobufEncoder extends MessageToMessageEncoder<TopMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TopMsg topMsg, List<Object> out) throws Exception {
		int msgType = topMsg.getMsgType();
		int channelId = topMsg.getChId();

		// int len = 2 + 4;
		// byte[] bytes = new byte[len];
		// BytesTools.shortToByte(bytes, 0, (short) msgType);
		// BytesTools.intToByte(bytes, 2, channelId);

		// byte[] intByte = new byte[2];
		// intByte[1] = (byte) (msgType & 0xff);
		// intByte[0] = (byte) ((msgType >> 8) & 0xff);
		// // intByte[1] = (byte) ((msgType >> 16) & 0xff);
		// // intByte[0] = (byte) ((msgType >> 24));

		byte[] datas = topMsg.getMsgBodyBytes();
		if (datas == null) {
			MessageLite protobufMsg = topMsg.getMsgBody();
			if (protobufMsg != null) {
				datas = ((MessageLite) protobufMsg).toByteArray();
				topMsg.setMsgBodyBytes(datas);

				int len = 4 + 2 + datas.length;
				byte[] sendBytes = new byte[4 + len];
				BytesTools.intToByte(sendBytes, 0, len);
				BytesTools.intToByte(sendBytes, 4, channelId);
				BytesTools.shortToByte(sendBytes, 8, (short) msgType);
				System.arraycopy(datas, 0, sendBytes, 10, datas.length);

				System.out.println("TopMsgProtobufEncoder:" + Arrays.toString(sendBytes));

				out.add(Unpooled.wrappedBuffer(sendBytes));

			} else {
				// out.add(Unpooled.wrappedBuffer(bytes));
			}

		} else {
			// out.add(Unpooled.wrappedBuffer(bytes, datas));
		}
	}

}
