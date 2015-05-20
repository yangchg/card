package com.owl.card.game.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owl.card.common.domain.Card;
import com.owl.card.common.domain.CardGroup;
import com.owl.card.common.domain.Role;
import com.owl.card.common.msg.TopMsg;
import com.owl.card.game.manager.AppGameMaster;

public class GameSession {
	private int channelId;

	// 玩家信息
	private Role role;

	// 卡牌信息
	private List<Card> cards = new ArrayList<Card>();

	private Map<Integer, Card> cardMap = new HashMap<>();

	// 卡组信息
	private List<CardGroup> groups = new ArrayList<CardGroup>();
	private Map<Long, CardGroup> groupMap = new HashMap<>();

	public int getChannelId() {
		return channelId;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public Map<Integer, Card> getCardMap() {
		return cardMap;
	}

	public void setCardMap(Map<Integer, Card> cardMap) {
		this.cardMap = cardMap;
	}

	public List<CardGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<CardGroup> groups) {
		this.groups = groups;
	}

	public Map<Long, CardGroup> getGroupMap() {
		return groupMap;
	}

	public void setGroupMap(Map<Long, CardGroup> groupMap) {
		this.groupMap = groupMap;
	}

	public GameSession(int channelId) {
		this.channelId = channelId;
	}

	/**
	 * 向客户端发送消息
	 * 
	 * @param topMsg
	 */
	public void sendMsg(TopMsg topMsg) {
		topMsg.setChannelId(channelId);

		AppGameMaster.channelManager.getMainGatewayChannel().writeAndFlush(topMsg);
	}
}
