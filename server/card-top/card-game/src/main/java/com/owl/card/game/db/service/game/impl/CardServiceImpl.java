package com.owl.card.game.db.service.game.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.owl.card.game.db.dao.CardDao;
import com.owl.card.game.db.service.game.CardService;

public class CardServiceImpl implements CardService {

	@Autowired
	private CardDao cardDao;

	public void addCard(long roleId, int cardProtoId, int num) {
		
		
	}

}
