package com.owl.card.common.domain;

import java.util.Date;

import com.owl.card.common.persistence.Domain;

public class Account implements Domain<Account> {

	private long id;
	private long version;

	private String accName; // 帐号名
	private String accPwd; // 密文密码
	private Date regTime; // 注册时间
	private String regIP; // 注册IP

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getAccPwd() {
		return accPwd;
	}

	public void setAccPwd(String accPwd) {
		this.accPwd = accPwd;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public long getId() {
		return this.id;
	}

	public Account() {

	}

	public Account(String accName, String accPwd, String regIP) {
		this.accName = accName;
		this.accPwd = accPwd;
		this.regIP = regIP;
	}

	@Override
	public void updateSet(Account domain) {
		Account account = domain;

		accName = account.getAccName();
		accPwd = account.getAccPwd();
		regTime = account.getRegTime();
		regIP = account.getRegIP();
	}

	@Override
	public Account cloneDomain() {
		Account account = new Account();
		account.setId(id);
		account.updateSet(this);
		return account;
	}

	@Override
	public String showObj() {
		return this.getClass().getSimpleName() + ":" + this.id;
	}

	@Override
	public Account domainSave() {
		Account account = cloneDomain();
		return account;
	}

}
