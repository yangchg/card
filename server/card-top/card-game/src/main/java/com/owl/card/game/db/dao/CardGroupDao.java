package com.owl.card.game.db.dao;

import java.util.List;

import com.owl.card.common.domain.CardGroup;
import com.owl.card.common.persistence.GenericDao;

public interface CardGroupDao extends GenericDao<CardGroup, Long> {

	/**
	 * 获取玩家的卡组
	 * 
	 * @param roleId
	 * @return
	 */
	List<CardGroup> findGroupByRoleId(long roleId);
}
