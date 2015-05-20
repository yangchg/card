package com.owl.card.common.domain;

import com.owl.card.common.persistence.Domain;

/**
 * 卡组的卡牌
 * 
 * @author YangChg
 *
 */
public class GroupCard implements Domain<GroupCard> {

	private long id;
	private long version;

	private int cardProtoId; // 卡牌模板编号
	private long groupId; // 组别编号

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

	public int getCardProtoId() {
		return cardProtoId;
	}

	public void setCardProtoId(int cardProtoId) {
		this.cardProtoId = cardProtoId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public GroupCard() {

	}

	public GroupCard(int cardProtoId, long groupId) {
		this.cardProtoId = cardProtoId;
		this.groupId = groupId;
	}

	@Override
	public void updateSet(GroupCard domain) {
		GroupCard groupCard = domain;

		this.cardProtoId = groupCard.getCardProtoId();
		this.groupId = groupCard.getGroupId();
	}

	@Override
	public GroupCard cloneDomain() {
		GroupCard groupCard = new GroupCard();
		groupCard.setId(id);
		groupCard.updateSet(this);
		return groupCard;
	}

	@Override
	public String showObj() {
		return this.getClass().getSimpleName() + ":" + this.id;
	}

	@Override
	public GroupCard domainSave() {
		GroupCard groupCard = cloneDomain();
		return groupCard;
	}

}