package com.owl.card.game.manager;

import com.owl.card.common.define.ServerDefine;
import com.owl.card.game.app.GameServerInit;

public class AppGameMaster {

	public static GameServerInit serverInit;
	public static GameModuleManager moduleManager = new GameModuleManager();
	public static GameCallbackManager callbackManager = new GameCallbackManager();
	public static GameWorkerManager workerManager = new GameWorkerManager();
	public static GameChannelManager channelManager = new GameChannelManager();

	public void init(GameServerInit serverInit) {
		AppGameMaster.serverInit = serverInit;

		moduleManager.init();
		callbackManager.init();
		workerManager.init();
		channelManager.init();
	}

	public static int fetchWorkerId(int channelId) {
		return channelId % ServerDefine.GAME_SERVER_COUNT;
	}
}
