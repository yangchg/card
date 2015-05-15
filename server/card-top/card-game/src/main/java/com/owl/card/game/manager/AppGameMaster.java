package com.owl.card.game.manager;

public class AppGameMaster {

	public static GameModuleManager moduleManager = new GameModuleManager();

	public void init() {

		moduleManager.init();
	}
}
