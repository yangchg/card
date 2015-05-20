package com.owl.card.game.db.service.game;

import java.util.List;

import com.owl.card.common.domain.Card;
import com.owl.card.common.domain.CardGroup;

public interface CardService {

	/**
	 * 添加卡牌
	 * 
	 * @param roleId
	 * @param cardProtoId
	 * @param num
	 */
	void addCard(long roleId, int cardProtoId, int num);

	/**
	 * 查询玩家卡牌数据
	 * 
	 * @param roleId
	 * @return
	 */
	List<Card> findByRoleId(long roleId);

	/**
	 * 获取卡组信息
	 * 
	 * @param roleId
	 * @return
	 */
	List<CardGroup> fetchCardGroupByRoleId(long roleId);
}
