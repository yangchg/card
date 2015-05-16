package com.owl.card.common.task;

import com.owl.card.common.base.BaseWorker;

public class WaitNoRtTask {

	public BaseWorker targetWorker;
	public NoRtTask task;

	public WaitNoRtTask(BaseWorker targetWorker, NoRtTask task) {
		this.targetWorker = targetWorker;
		this.task = task;
	}

}
