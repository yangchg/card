package com.owl.card.common.task;

import com.owl.card.common.base.BaseWorker;

public class RegisterWorkerTask extends NoRtTask {

	private BaseWorker nodeWorker;

	public RegisterWorkerTask(BaseWorker nodeWorker) {
		this.nodeWorker = nodeWorker;
	}

	@Override
	public void runTask() {
		nodeWorker.register();
	}

}
