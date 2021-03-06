package com.owl.card.common.define;

/**
 * 错误码
 * 
 * @author YangChg
 *
 */
public class ClientErrCode {
	public final static int RT_FAIL = 0; // 失败
	public final static int RT_SUCC = 1; // 成功

	public final static int GROUP_COUNT_LIMIT = 100; // 卡组数量已达上限
	public final static int CARD_IS_NOT_EXIST = 101; // 卡牌不存在
	public final static int GROUP_IS_NOT_EXIST = 102; // 卡组不存在
	public final static int GROUP_CARD_CNT_LIMIT = 103; // 卡牌数量超出上限
	public final static int CARD_REPEAT_LIMIT = 104; // 卡牌重复数量过多
}
