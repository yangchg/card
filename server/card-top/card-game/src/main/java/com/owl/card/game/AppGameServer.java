package com.owl.card.game;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.owl.card.game.manager.AppGameMaster;
import com.owl.card.game.net.handler.ProtoMsgRegister;
import com.owl.card.game.net.server.GameNetServer;

/**
 * 游戏服务器
 *
 */
public class AppGameServer {

	private final static Logger logger = Logger.getLogger(AppGameServer.class);

	@SuppressWarnings("unused")
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		AppGameServer appGameServer = new AppGameServer();
		appGameServer.start();
	}

	public void start() {
		logger.info("游戏服务器启动....");

		this.applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		AppGameMaster appGameMaster = new AppGameMaster();
		appGameMaster.init();

		ProtoMsgRegister.registerMsg();

		// 开启网络
		GameNetServer gameNetServer = new GameNetServer();
		gameNetServer.start();
	}

	public void initConfig() {

	}

	public void initLogic() {

	}
}
