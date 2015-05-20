package com.owl.card.common.domain;

import java.util.ArrayList;
import java.util.List;

import com.owl.card.common.persistence.Domain;

/**
 * 卡组
 * 
 * @author YangChg
 *
 */
public class CardGroup implements Domain<CardGroup> {

	private long id;
	private long version;

	private int heroCareer; // 英雄职业
	private String groupName; // 卡组名称
	private long roleId; // 玩家编号

	private List<Integer> cards = new ArrayList<Integer>();

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getHeroCareer() {
		return heroCareer;
	}

	public void setHeroCareer(int heroCareer) {
		this.heroCareer = heroCareer;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getCards() {
		return cards;
	}

	public void setCards(List<Integer> cards) {
		this.cards = cards;
	}

	public CardGroup() {

	}

	public CardGroup(int heroCareer, String groupName, long roleId) {
		this.heroCareer = heroCareer;
		this.groupName = groupName;
		this.roleId = roleId;
	}

	@Override
	public void updateSet(CardGroup domain) {
		CardGroup cardGroup = domain;

		this.heroCareer = cardGroup.getHeroCareer();
		this.groupName = cardGroup.getGroupName();
		this.roleId = cardGroup.getRoleId();
	}

	@Override
	public CardGroup cloneDomain() {
		CardGroup cardGroup = new CardGroup();
		cardGroup.setId(id);
		cardGroup.updateSet(this);
		return cardGroup;
	}

	@Override
	public String showObj() {
		return this.getClass().getSimpleName() + ":" + this.id;
	}

	@Override
	public CardGroup domainSave() {
		CardGroup cardGroup = cloneDomain();
		return cardGroup;
	}

}
