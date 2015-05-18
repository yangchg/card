package com.owl.card.common.task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * 任务执行组件
 * 
 * @author jack
 *
 */
public class TaskExecComponent {

	// 任务队列
	private List<NoRtTask> taskQueue = new LinkedList<NoRtTask>();
	private List<NoRtTask> backTaskQueue = new LinkedList<NoRtTask>();

	// 任务统计信息
	public LinkedList<Integer> taskDealNumPreSecond = new LinkedList<Integer>(); // 每秒处理的任务数
	public int currentTaskDealNum;
	public Map<Integer, Integer> taskTypeMap = new HashMap<>();
	public long lastRecordTime;

	// 任务后执行任务，这类任务必须在当前任务执行完毕后立即执行。
	// private List<AfterTaskBehTask> afterTaskBehTasks = new LinkedList<AfterTaskBehTask>();

	// 如果发送任务的过程在ServerWorker中进行。而目标队列满了，那么将任务临时放置在这个列表中，以避免阻塞引发死锁，
	// 等待目标队列空的时候在发送。
	private Queue<WaitNoRtTask> waitPostTasks = new LinkedList<WaitNoRtTask>();
	private boolean needWait; // 延迟发送的标记

	public boolean isNeedWait() {
		return needWait;
	}

	public void setNeedWait(boolean needWait) {
		this.needWait = needWait;
	}

	/**
	 * 添加任务到后备队列
	 * 
	 * @param task
	 */
	public void addTask2BackQueue(NoRtTask task) {
		this.backTaskQueue.add(task);
	}

	/**
	 * 处理即时任务
	 */
	public void dealAsyncTask(long now) {

		// Thread curThread = Thread.currentThread();
		// System.out.println("dealAsyncTask:" + curThread.toString() + "id:" + curThread.getId());

		// 双缓存反转
		List<NoRtTask> tmpQueue = this.taskQueue;
		this.taskQueue = this.backTaskQueue;
		tmpQueue = new LinkedList<NoRtTask>();
		this.backTaskQueue = tmpQueue;

		// 更新任务表
		List<NoRtTask> queue = this.taskQueue;
		this.currentTaskDealNum += queue.size();
		if (now - lastRecordTime > 1000) {
			lastRecordTime = lastRecordTime + 1000;

			this.taskDealNumPreSecond.add(currentTaskDealNum);

			// analyzeTaskPrint();

			currentTaskDealNum = 0;

			int size = this.taskDealNumPreSecond.size();
			if (size > 600) {
				int diff = size - 600;
				for (int i = 0; i < diff; i++) {
					taskDealNumPreSecond.poll();
				}
			}
		}

		// 遍历并执行
		for (NoRtTask task : queue) {
			// analyzeTaskPost(task);

			task.runTask();

			// dealTaskAfterTasks();
		}

	}

	/**
	 * 添加延迟post任务
	 * 
	 * @param waitTask
	 */
	public void addWaitPostTask(WaitNoRtTask waitTask) {
		waitPostTasks.add(waitTask);
		this.needWait = true;
	}

	// /**
	// * 尝试把当前worker中等待的通信消息任务发布到目标Worker中。
	// *
	// * 这个方法通常用于worker间通信用，目标worker的队列有可能满，这会导致发布失败，从而延迟到下一个执行周期再发布。
	// */
	// public void tryPostTask() {
	// do {
	// WaitNoRtTask waitTask = waitPostTasks.peek();
	// if (waitTask == null) {
	// needWait = false;
	// return;
	// }
	//
	// NodeWorker targetWorker = waitTask.targetWorker;
	// NoRtTask task = waitTask.task;
	// boolean addResult = targetWorker.tryAddAsyncTask(task);
	// if (addResult == false) {
	// // 添加失败，不再继续，退出
	// return;
	// }
	//
	// // 添加成功，移除等待任务。
	// waitPostTasks.poll();
	//
	// } while (true);
	// }
	//
	// /**
	// * 添加任务后任务
	// *
	// * 只有非常少数的任务是这类任务
	// *
	// * @param task
	// */
	// public void addAfterTaskBehTask(AfterTaskBehTask task) {
	// afterTaskBehTasks.add(task);
	// }
	//
	// /**
	// * 运行一次任务执行后的附加后续任务。
	// */
	// public void dealTaskAfterTasks() {
	// // 执行任务后任务
	// if (this.afterTaskBehTasks.isEmpty() == false) {
	// for (AfterTaskBehTask afterTask : afterTaskBehTasks) {
	// afterTask.exec();
	// }
	// this.afterTaskBehTasks.clear();
	// }
	// }

}
