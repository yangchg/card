package com.owl.card.gateway.app;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.Session;

public class GateAppManager {

	public static AtomicInteger channelIdMaker = new AtomicInteger();

	// 连接游戏服务器的客户端
	public static List<Channel> gameClientChannels = new ArrayList<Channel>();

	// 玩家session
	public static Map<Integer, Session> channelIdSessions = new ConcurrentHashMap<Integer, Session>();
	public static Map<Session, Integer> sessionChannelIds = new ConcurrentHashMap<Session, Integer>();

	public boolean init() {

		return true;
	}
}
