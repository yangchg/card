package com.owl.card.game.db.service.game.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.card.common.domain.Card;
import com.owl.card.common.domain.CardGroup;
import com.owl.card.common.domain.GroupCard;
import com.owl.card.game.db.dao.CardDao;
import com.owl.card.game.db.dao.CardGroupDao;
import com.owl.card.game.db.dao.GroupCardDao;
import com.owl.card.game.db.service.game.CardService;

@Service("cardService")
public class CardServiceImpl implements CardService {

	@Autowired
	private CardDao cardDao;
	@Autowired
	private CardGroupDao cardGroupDao;
	@Autowired
	private GroupCardDao groupCardDao;

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

	@Override
	public List<Card> findByRoleId(long roleId) {
		return cardDao.findByRoleId(roleId);
	}

	@Override
	public List<CardGroup> fetchCardGroupByRoleId(long roleId) {
		List<CardGroup> groups = cardGroupDao.findGroupByRoleId(roleId);

		if (groups == null || groups.isEmpty()) {
			return groups;
		}

		Map<Long, CardGroup> groupCards = new HashMap<Long, CardGroup>();
		for (CardGroup group : groups) {
			groupCards.put(group.getId(), group);
		}

		List<GroupCard> cards = groupCardDao.findCardByGroupId(groupCards.keySet());
		if (cards != null && !cards.isEmpty()) {
			for (GroupCard card : cards) {
				CardGroup cg = groupCards.get(card.getGroupId());

				if (cg != null) {
					cg.getCards().add(card.getCardProtoId());
				}
			}
		}

		return null;
	}

	@Override
	public CardGroup addCardGroup(int hero, String groupName, long roleId, List<Integer> cardProtoIds) {
		CardGroup cardGroup = new CardGroup(hero, groupName, roleId);
		long groupId = cardGroupDao.save(cardGroup);

		for (Integer cardProtoId : cardProtoIds) {
			GroupCard groupCard = new GroupCard(cardProtoId, groupId);
			long gid = groupCardDao.save(groupCard);
			groupCard.setGroupId(gid);
		}

		cardGroup.getCards().addAll(cardProtoIds);

		return cardGroup;
	}

	@Override
	public CardGroup editCardGroup(long groupId, String newGroupName, List<Integer> cardProtoIds) {
		// 修改组名
		CardGroup cardGroup = cardGroupDao.findById(groupId);
		if (cardGroup == null) {
			return null;
		}

		cardGroup.setGroupName(newGroupName);

		// 修改卡组的卡牌
		groupCardDao.delByGroupId(groupId);

		for (Integer cardProtoId : cardProtoIds) {
			GroupCard groupCard = new GroupCard(cardProtoId, groupId);
			groupCardDao.save(groupCard);
		}

		cardGroup.getCards().addAll(cardProtoIds);

		return cardGroup;
	}

	@Override
	public void delCardGroup(long groupId) {
		// 删除卡组
		cardGroupDao.deleteById(groupId);

		// 删除卡组的卡牌
		groupCardDao.delByGroupId(groupId);
	}
}
