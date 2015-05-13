package com.owl.card.gateway;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.owl.card.gateway.app.ServerInit;
import com.owl.card.gateway.websocket.WSServer;

public class AppGateway {
	private ApplicationContext applicationContext;

	public static void main(String[] args) {
		AppGateway appGateway = new AppGateway();
		appGateway.start();
	}

	public void start() {
		this.applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

		configLoggerLevel();

		ServerInit serverInit = (ServerInit) this.applicationContext.getBean("serverInit");
		serverInit.init();

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
