package com.owl.card.game.manager;

import com.owl.card.common.define.ServerDefine;

public class AppGameMaster {

	public static GameModuleManager moduleManager = new GameModuleManager();
	public static GameCallbackManager callbackManager = new GameCallbackManager();
	public static GameWorkerManager workerManager = new GameWorkerManager();
	public static GameChannelManager channelManager = new GameChannelManager();

	public void init() {

		moduleManager.init();
		callbackManager.init();
		workerManager.init();
		channelManager.init();
	}

	public static int fetchWorkerId(int channelId) {
		return channelId % ServerDefine.GAME_SERVER_COUNT;
	}
}
