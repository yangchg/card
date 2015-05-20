package com.owl.card.game.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owl.card.common.base.BaseModule;
import com.owl.card.game.app.GameServerInit;
import com.owl.card.game.module.card.CardGroupModule;
import com.owl.card.game.module.card.interfaces.CardGroupModuleInterface;
import com.owl.card.game.module.login.LoginModule;
import com.owl.card.game.module.login.interfaces.LoginModuleInterface;

public class GameModuleManager {

	private List<BaseModule> modules = new ArrayList<BaseModule>();
	private Map<Class<?>, BaseModule> moduleClassMap = new HashMap<Class<?>, BaseModule>();

	public List<BaseModule> getModules() {
		return modules;
	}

	public void setModules(List<BaseModule> modules) {
		this.modules = modules;
	}

	public Map<Class<?>, BaseModule> getModuleClassMap() {
		return moduleClassMap;
	}

	public void setModuleClassMap(Map<Class<?>, BaseModule> moduleClassMap) {
		this.moduleClassMap = moduleClassMap;
	}

	public static LoginModuleInterface loginModule;
	public static CardGroupModuleInterface cardGroupModule;

	protected <T extends BaseModule> T registerModule(T module) {
		this.modules.add(module);

		moduleClassMap.put(module.getClass(), module);

		return module;
	}

	public void init() {
		GameServerInit serverInit = AppGameMaster.serverInit;

		loginModule = registerModule(new LoginModule(serverInit.getAccountService(), serverInit.getRoleService(),
				serverInit.getCardService()));
		cardGroupModule = registerModule(new CardGroupModule());
	}

}
