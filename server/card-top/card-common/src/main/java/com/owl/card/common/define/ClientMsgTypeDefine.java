package com.owl.card.common.define;

/**
 * 客户端消息定义
 * 
 * @author YangChg
 *
 */
public class ClientMsgTypeDefine {
	public final static int MAX_MSG_TYPE = 200;

	/**
	 * 玩家请求登陆
	 */
	public final static int userLoginC2S = 10;

	/**
	 * 添加卡组
	 */
	public final static int cardGroupAdd = 20;

	/**
	 * 编辑卡组
	 */
	public final static int cardGroupEdit = 22;

	/**
	 * 删除卡组
	 */
	public final static int cardGroupDel = 24;
}
