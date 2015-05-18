package com.owl.card.game.obj;

import com.owl.card.common.msg.TopMsg;
import com.owl.card.game.manager.AppGameMaster;

public class Role {
	private int channelId;

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public Role(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * 向客户端发送消息
	 * 
	 * @param topMsg
	 */
	public void sendMsg(TopMsg topMsg) {
		topMsg.setChannelId(channelId);

		AppGameMaster.channelManager.getMainGatewayChannel().writeAndFlush(topMsg);
	}
}
