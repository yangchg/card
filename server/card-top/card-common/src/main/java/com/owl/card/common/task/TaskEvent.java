package com.owl.card.common.task;

import com.lmax.disruptor.EventFactory;

public class TaskEvent {

	private NoRtTask noRtTask; // 任务

	public final static EventFactory<TaskEvent> EVENT_FACTORY = new EventFactory<TaskEvent>() {
		public TaskEvent newInstance() {
			return new TaskEvent();
		}
	};

	public NoRtTask getNoRtTask() {
		return noRtTask;
	}

	public void setNoRtTask(NoRtTask noRtTask) {
		this.noRtTask = noRtTask;
	}

}
