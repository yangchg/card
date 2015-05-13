package com.owl.card.gateway.app;

import io.netty.channel.Channel;

import java.util.HashSet;
import java.util.Set;

public class GateAppManager {

	public static Set<Channel> gameClientChannels = new HashSet<Channel>();

	
	public boolean init() {

		return true;
	}
}
