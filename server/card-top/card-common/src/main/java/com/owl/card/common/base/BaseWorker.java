package com.owl.card.common.base;

import com.owl.card.common.task.InsideMsgExec;

public class BaseWorker {

	protected int index;
	protected static ThreadLocal<BaseWorker> workerLocal = new ThreadLocal<BaseWorker>();

	private InsideMsgExec insideMsgExec;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public static ThreadLocal<BaseWorker> getWorkerLocal() {
		return workerLocal;
	}

	public static void setWorkerLocal(ThreadLocal<BaseWorker> workerLocal) {
		BaseWorker.workerLocal = workerLocal;
	}

	public InsideMsgExec getInsideMsgExec() {
		return insideMsgExec;
	}

	public void setInsideMsgExec(InsideMsgExec insideMsgExec) {
		this.insideMsgExec = insideMsgExec;
	}

	/**
	 * 注册
	 */
	public void register() {

	}

	public static BaseWorker currentWorker() {
		return workerLocal.get();
	}
}
