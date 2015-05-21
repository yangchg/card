package com.owl.card.game.db.dao;

import java.util.List;
import java.util.Set;

import com.owl.card.common.domain.GroupCard;
import com.owl.card.common.persistence.GenericDao;

public interface GroupCardDao extends GenericDao<GroupCard, Long> {
	/**
	 * 获取指定卡组的卡牌
	 * 
	 * @param groupId
	 * @return
	 */
	List<GroupCard> findCardByGroupId(Set<Long> groupIds);

	/**
	 * 删除指定卡组的卡牌
	 * 
	 * @param groupId
	 */
	void delByGroupId(long groupId);
}
