package com.owl.card.game.manager;

import io.netty.channel.ChannelHandlerContext;

public class GameChannelManager {

	private ChannelHandlerContext mainGatewayChannel = null;

	public ChannelHandlerContext getMainGatewayChannel() {
		return mainGatewayChannel;
	}

	public void setMainGatewayChannel(ChannelHandlerContext mainGatewayChannel) {
		this.mainGatewayChannel = mainGatewayChannel;
	}

	public void init() {

	}
}
