package com.owl.card.common.base;

public abstract class BaseModule {
	/**
	 * 构建系统时初始化
	 */
	public abstract void onInit();

	/**
	 * 工作线程开启后的初始化。
	 */
	public abstract void onWorkerInit();
}
