package com.owl.card.game.manager;

import java.util.ArrayList;
import java.util.List;

import com.owl.card.common.base.BaseModule;
import com.owl.card.game.module.login.LoginModule;
import com.owl.card.game.module.login.interfaces.LoginModuleInterface;

public class GameModuleManager {

	protected List<BaseModule> modules = new ArrayList<BaseModule>();

	public static LoginModuleInterface loginModule;

	protected <T extends BaseModule> T registerModule(T module) {
		this.modules.add(module);
		return module;
	}

	public void init() {
		loginModule = registerModule(new LoginModule());
	}

}
