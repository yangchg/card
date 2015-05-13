package com.owl.card.gateway;

import io.netty.channel.Channel;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.owl.card.gateway.app.GateAppManager;
import com.owl.card.gateway.app.ServerInit;
import com.owl.card.gateway.net.client.GateNetClient;
import com.owl.card.gateway.websocket.WSServer;

public class AppGateway {

	private final static String GAME_SERVER_IP = "127.0.0.1";
	private final static int GAME_SERVER_PORT = 8401;

	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		AppGateway appGateway = new AppGateway();
		appGateway.start();
	}

	public void start() {
		this.applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		configLoggerLevel();

		// 管理者
		GateAppManager gateAppManager = new GateAppManager();
		gateAppManager.init();

		// 初始化
		ServerInit serverInit = (ServerInit) this.applicationContext.getBean("serverInit");
		serverInit.init();

		// 连接游戏服务器
		GateNetClient connectGameClient = new GateNetClient();
		Channel gameClientChannel = connectGameClient.connect(GAME_SERVER_IP, GAME_SERVER_PORT);

		if (gameClientChannel == null) {
			throw new RuntimeException("连接游戏服务器失败");
		}

		GateAppManager.gameClientChannels.add(gameClientChannel);

		// 开启web socket
		Thread netThread = new Thread(new Runnable() {
			public void run() {
				WSServer wsServer = new WSServer();
				wsServer.start();
			}
		});
		netThread.start();
	}

	private void configLoggerLevel() {

	}
}
