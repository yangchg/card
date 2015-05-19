package com.owl.card.game.db.dao;

import java.util.List;

import com.owl.card.common.domain.Card;
import com.owl.card.common.persistence.GenericDao;

public interface CardDao extends GenericDao<Card, Long> {

	/**
	 * 获取玩家指定编号的卡牌信息
	 * @param roleId
	 * @param cardProtoId
	 * @return
	 */
	Card findByProtoId(long roleId, int cardProtoId);

	/**
	 * 获取玩家卡牌信息
	 * @param roleId
	 * @return
	 */
	List<Card> findByRoleId(long roleId);

}
