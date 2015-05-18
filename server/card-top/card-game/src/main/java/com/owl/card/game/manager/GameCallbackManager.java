package com.owl.card.game.manager;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.owl.card.common.base.BaseModule;
import com.owl.card.common.define.ClientMsgTypeDefine;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.game.obj.Role;

public class GameCallbackManager {

	private static List<Method> funcs = new ArrayList<Method>();
	private static List<BaseModule> modules = new ArrayList<BaseModule>();

	public void init() {
		for (int i = 0; i <= ClientMsgTypeDefine.MAX_MSG_TYPE; i++) {
			funcs.add(null);
			modules.add(null);
		}
	}

	public void callCallbackFunc(int msgType, Role role, TopMsg topMsg) {
		if (msgType < 0 || msgType > ClientMsgTypeDefine.MAX_MSG_TYPE) {
			assert false : "客户端消息处理越界:" + msgType;
			return;
		}

		Method method = funcs.get(msgType);
		BaseModule module = modules.get(msgType);
		try {
			method.invoke(module, new Object[] { role, topMsg });
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void registerClientMsg(int msgType, Class<?> moduleCalss, String funcName) {
		if (msgType < 0 || msgType > ClientMsgTypeDefine.MAX_MSG_TYPE) {
			assert false : "注册客户端消息越界:" + msgType;
			return;
		}

		Method method = null;
		try {
			method = moduleCalss.getMethod(funcName, Role.class, TopMsg.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			assert false : "注册客户端消息对应的函数未找到:" + msgType;
			return;
		}

		BaseModule module = AppGameMaster.moduleManager.getModuleClassMap().get(moduleCalss);
		if (module == null) {
			assert false : "注册客户端消息对应的模块未找到:" + moduleCalss;
			return;
		}

		funcs.set(msgType, method);
		modules.set(msgType, module);
	}
}
