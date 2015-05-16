package com.owl.card.game;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.owl.card.game.net.handler.ProtoMsgRegister;
import com.owl.card.game.net.server.GameNetServer;
import com.owl.card.game.worker.GameWorker;

public class GameServer {

	@SuppressWarnings("unused")
	private ApplicationContext applicationContext;

	private List<GameWorker> wrokers = new ArrayList<GameWorker>();

	public void start() {

		applicationContext = new ClassPathXmlApplicationContext("applicationContext-game.xml");

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

	}

	private void initLogic() {

	}

	private void startWorker() {
		for (int i = 0; i < 5; i++) {
			doStartWorker(i);
		}
	}

	private void doStartWorker(int index) {
		GameWorker gameWorker = new GameWorker(index);
		wrokers.add(gameWorker);

		gameWorker.start();
	}
}
