package com.owl.card.game.worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.owl.card.common.base.BaseWorker;
import com.owl.card.common.task.NoRtTask;
import com.owl.card.common.task.RegisterWorkerTask;
import com.owl.card.common.task.TaskEvent;
import com.owl.card.common.task.TaskExecComponent;
import com.owl.card.common.task.WaitNoRtTask;

public class GameWorker extends BaseWorker {

	private int index;

	private ExecutorService exec;
	private final Disruptor<TaskEvent> disruptor;
	private RingBuffer<TaskEvent> ringBuffer;
	private Thread currentThread;

	private final TaskExecComponent taskExecComponent;

	public GameWorker(int index) {
		this.index = index;

		taskExecComponent = new TaskExecComponent();

		exec = Executors.newFixedThreadPool(1); // 建立供disruptor使用的线程池

		// 建立disruptor
		disruptor = new Disruptor<TaskEvent>(TaskEvent.EVENT_FACTORY, 16384, exec, ProducerType.MULTI,
				new BlockingWaitStrategy());

		// 事件处理
		final EventHandler<TaskEvent> handler = new EventHandler<TaskEvent>() {
			public void onEvent(final TaskEvent event, final long sequence, final boolean endOfBatch) throws Exception {
				try {

					//initSelf();

					//handleTaskEvent(event);

				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
		};

		disruptor.handleEventsWith(handler); // 设置事件处理

		ringBuffer = disruptor.start();

	}

	@Override
	public void register() {
		this.currentThread = Thread.currentThread();

		System.out.println("游戏服务器线程:" + this.index + ",线程编号:" + this.currentThread + ", 线程名称:"
				+ Thread.currentThread().toString());
	}

	public void start() {
		addAsyncTask(new RegisterWorkerTask(this));
	}

	public void addAsyncTask(NoRtTask task) {
		long seq = 0;
		// 当前是在生产者环境下发送任务，所以当前的执行不可以被阻塞！
		// 这里需要判断当前消费者环境是否有阻塞发送的消息，消费者环境的目标队列是否满。
		// 任何一种情况发生，都需要将任务转存到延迟发送队列，避免死锁。
		if (taskExecComponent.isNeedWait()) {
			taskExecComponent.addWaitPostTask(new WaitNoRtTask(this, task));
			return;
		}

		try {
			seq = ringBuffer.tryNext();
		} catch (InsufficientCapacityException e) {
			taskExecComponent.addWaitPostTask(new WaitNoRtTask(this, task));
			return;
		}

		try {
			TaskEvent taskEvent = ringBuffer.get(seq);
			taskEvent.setNoRtTask(task);
		} finally {
			ringBuffer.publish(seq);
		}

	}

}
