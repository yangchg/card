package com.owl.card.game;

import org.apache.log4j.Logger;

import com.owl.card.common.config.ConfigLoader;
import com.owl.card.game.app.GameServer;

/**
 * 游戏服务器
 *
 */
public class AppGameServer {

	private final static Logger logger = Logger.getLogger(AppGameServer.class);

	public static void main(String[] args) {

		logger.info("游戏服务器启动....");
		
		// 加载配置
		ConfigLoader.loadConfig();

		GameServer gameServer = new GameServer();
		gameServer.start();
	}
}
