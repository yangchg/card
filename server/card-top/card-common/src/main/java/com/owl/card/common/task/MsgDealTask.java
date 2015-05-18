package com.owl.card.common.task;

import com.owl.card.common.base.BaseWorker;
import com.owl.card.common.msg.InsideMsgWrap;

/**
 * 内部消息处理任务
 * 
 * @author jack
 *
 */
public class MsgDealTask extends NoRtTask {

	private final InsideMsgWrap wrap; // 包裹内部消息的wrap

	public InsideMsgWrap getWrap() {
		return wrap;
	}

	public MsgDealTask(InsideMsgWrap wrap) {
		this.wrap = wrap;
	}

	@Override
	public void runTask() {
		InsideMsgExec insideMsgExec = BaseWorker.currentWorker().getInsideMsgExec();
		if (insideMsgExec == null) {
			assert false;
			return;
		}

		// 处理消息
		insideMsgExec.dispatchMsg(wrap);
	}

}
