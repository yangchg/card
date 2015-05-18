package com.owl.card.game.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.define.ServerDefine;
import com.owl.card.game.manager.AppGameMaster;
import com.owl.card.game.net.handler.ProtoMsgRegister;
import com.owl.card.game.net.server.GameNetServer;
import com.owl.card.game.worker.GameWorker;

public class GameServer {

	private ApplicationContext applicationContext;

	public void start() {

		applicationContext = new ClassPathXmlApplicationContext("applicationContext-game.xml");

		GameServerInit serverInit = (GameServerInit) this.applicationContext.getBean("serverInit");
		serverInit.init();

		AppGameMaster appGameMaster = new AppGameMaster();
		appGameMaster.init(serverInit);

		ProtoMsgRegister.registerMsg();

		// 加载配置
		initConfig();

		// 初始化模块
		initLogic();

		// 
		startWorker();

		// 开启网络
		GameNetServer gameNetServer = new GameNetServer();
		gameNetServer.start();
	}

	private void initConfig() {
		for (BaseModule module : AppGameMaster.moduleManager.getModules()) {
			module.onInit();
		}
	}

	private void initLogic() {

	}

	private void startWorker() {
		for (int i = 0; i < ServerDefine.GAME_SERVER_COUNT; i++) {
			doStartWorker(i);
		}
	}

	private void doStartWorker(int index) {
		GameWorker gameWorker = new GameWorker(index);
		AppGameMaster.workerManager.getWrokers().add(gameWorker);

		gameWorker.start();
	}
}
