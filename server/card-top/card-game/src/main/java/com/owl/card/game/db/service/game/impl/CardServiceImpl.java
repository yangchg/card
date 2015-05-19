package com.owl.card.game.db.service.game.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.card.common.domain.Card;
import com.owl.card.game.db.dao.CardDao;
import com.owl.card.game.db.service.game.CardService;

@Service("cardService")
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDao cardDao;

	@Override
	public void addCard(long roleId, int cardProtoId, int num) {
		Card card = cardDao.findByProtoId(roleId, cardProtoId);
		if (card == null) {
			// 当前无此卡牌
			card = new Card(cardProtoId, num, roleId);
			cardDao.save(card);
		} else {
			// 更新卡牌数量
			card.setNum(card.getNum() + num);
		}
	}

}
