package com.owl.card.game;

import org.apache.log4j.Logger;

import com.owl.card.game.net.server.GameNetServer;

/**
 * 游戏服务器
 *
 */
public class AppGameServer {
	
	private final static Logger logger = Logger.getLogger(AppGameServer.class);
	
	public static void main(String[] args) {
		
		logger.info("游戏服务器启动....1");
		
		// 开启网络
		GameNetServer gameNetServer = new GameNetServer();
		gameNetServer.start();
		
	}
}
