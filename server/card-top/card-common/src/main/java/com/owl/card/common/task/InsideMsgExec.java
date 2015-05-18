package com.owl.card.common.task;

import com.owl.card.common.msg.InsideMsgWrap;

public interface InsideMsgExec {

	/**
	 * 处理消息
	 */
	public void dispatchMsg(InsideMsgWrap insideMsgWrap);

}
