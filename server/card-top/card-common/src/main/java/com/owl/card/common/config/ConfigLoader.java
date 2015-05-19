package com.owl.card.common.config;

public class ConfigLoader {

	public static void loadConfig(){
		// 加载游戏配置
		loadGameConfig();
	}
	
	/**
	 * 加载游戏配置
	 */
	public static void loadGameConfig(){
		AppConfig.cfgs = new CfgPool();
		AppConfig.cfgs.init();
	}
}
