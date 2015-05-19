package com.owl.card.game.db.dao;

import java.util.List;

import com.owl.card.common.domain.Card;
import com.owl.card.common.persistence.GenericDao;

public interface CardDao extends GenericDao<Card, Long> {

	Card findByProtoId(long roleId, int cardProtoId);

	List<Card> findByRoleId(long roleId);

}
