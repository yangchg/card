package com.owl.card.game.db.service.game;

public interface CardService {

	/**
	 * 添加卡牌
	 * @param roleId
	 * @param cardProtoId
	 * @param num
	 */
	void addCard(long roleId, int cardProtoId, int num);

}
