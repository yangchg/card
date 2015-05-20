package com.owl.card.game.obj;

import java.util.ArrayList;
import java.util.List;

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

	// 卡组信息
	private List<CardGroup> cardGroups = new ArrayList<CardGroup>();

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

	public List<CardGroup> getCardGroups() {
		return cardGroups;
	}

	public void setCardGroups(List<CardGroup> cardGroups) {
		this.cardGroups = cardGroups;
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
