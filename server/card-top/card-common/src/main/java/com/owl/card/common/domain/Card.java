package com.owl.card.common.domain;

import com.owl.card.common.persistence.Domain;

public class Card implements Domain<Card> {

	private long id;
	private long version;

	private int protoId; // 模板编号
	private int num; // 个数
	private long roleId; // 玩家编号

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public int getProtoId() {
		return protoId;
	}

	public void setProtoId(int protoId) {
		this.protoId = protoId;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Card() {

	}

	@Override
	public void updateSet(Card domain) {
		Card cardData = domain;

		this.protoId = cardData.getProtoId();
		this.num = cardData.getNum();
		this.roleId = cardData.getRoleId();
	}

	@Override
	public Card cloneDomain() {
		Card cardData = new Card();
		cardData.setId(id);
		cardData.updateSet(this);
		return cardData;
	}

	@Override
	public String showObj() {
		return this.getClass().getSimpleName() + ":" + this.id;
	}

	@Override
	public Card domainSave() {
		Card cardData = cloneDomain();
		return cardData;
	}

}
