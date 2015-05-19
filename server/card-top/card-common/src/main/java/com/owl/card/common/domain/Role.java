package com.owl.card.common.domain;

import java.util.Date;

import com.owl.card.common.persistence.Domain;

public class Role implements Domain<Role> {

	private long id;
	private long version;

	private String name; // 角色名
	private int sex; // 性别
	private int level; // 等级
	private int exp; // 经验
	private int gold; // 金币
	private int diamond; // 钻石
	private int point; // 积分

	private Date lastLoginDate; // 最近登陆时间

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public int getDiamond() {
		return diamond;
	}

	public void setDiamond(int diamond) {
		this.diamond = diamond;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Role() {

	}

	public Role(long id, int sex, int level, int exp, int gold, int diamond, int point) {
		this.id = id;
		this.sex = sex;
		this.level = level;
		this.exp = exp;
		this.gold = gold;
		this.diamond = diamond;
		this.point = point;
		this.lastLoginDate = new Date();
	}

	@Override
	public void updateSet(Role domain) {
		Role role = domain;

		this.name = role.getName();
		this.level = role.getLevel();
		this.exp = role.getExp();
		this.gold = role.getGold();
		this.diamond = role.getDiamond();
		this.point = role.getPoint();
		this.lastLoginDate = role.getLastLoginDate();
	}

	@Override
	public Role cloneDomain() {
		Role role = new Role();
		role.setId(id);
		role.updateSet(this);
		return role;
	}

	@Override
	public String showObj() {
		return this.getClass().getSimpleName() + ":" + this.id;
	}

	@Override
	public Role domainSave() {
		Role role = cloneDomain();
		return role;
	}

}
