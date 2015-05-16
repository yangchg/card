package com.owl.card.common.task;

import java.util.concurrent.ScheduledFuture;

/**
 * 异步调度任务基类。
 * 
 * 任务需要调度器立即或延迟执行的任务都会继承这个基类。
 * 
 * 这类任务是异步的，没有返回值。
 * 
 * @author Ariane
 * 
 */
public abstract class NoRtTask {

	/** 是否被取消了。 **/
	protected boolean isAbort = false;

	/** 是否已经执行完毕。 **/
	protected boolean isExecFinish = false;

	private ScheduledFuture<?> scheduledFuture;

	public ScheduledFuture<?> getScheduledFuture() {
		return scheduledFuture;
	}

	public void setScheduledFuture(ScheduledFuture<?> scheduledFuture) {
		this.scheduledFuture = scheduledFuture;
	}

	/**
	 * 是否放弃这个事件。
	 * 
	 * @return
	 */
	public boolean isAbort() {
		return isAbort;
	}

	/**
	 * 放弃这个事件。
	 */
	public void abortTask() {
		// 取消当前任务的调度。
		if (scheduledFuture != null) {
			scheduledFuture.cancel(false);
		}

		if (this.isExecFinish) {
			// Task已经执行完毕，不需要取消
			return;
		}

		// 需要任务的执行。
		abort();
	}

	/**
	 * 是否执行结束了。
	 * 
	 * @return
	 */
	public boolean isFinish() {
		return this.isExecFinish;
	}

	/**
	 * 放弃任务的执行。
	 */
	protected void abort() {
		this.isAbort = true;
	}

	/**
	 * 执行任务。
	 */
	public abstract void runTask();
}
