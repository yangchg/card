package com.owl.card.common.define;

/**
 * 服务器->客户端 消息
 * 
 * @author YangChg
 *
 */
public class ServerMsgTypeDefine {

	/**
	 * 客户端请求登陆返回
	 */
	public final static int userLoginS2C = 11;

	/**
	 * 客户端请求登陆返回
	 */
	public final static int cardGroupAddRt = 21;

	/**
	 * 编辑卡组返回
	 */
	public final static int cardGroupEditRt = 23;

	/**
	 * 删除卡组返回
	 */
	public final static int cardGroupDelRt = 25;
}
