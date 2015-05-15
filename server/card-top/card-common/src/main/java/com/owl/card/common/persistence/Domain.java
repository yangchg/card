package com.owl.card.common.persistence;

public interface Domain<T extends Domain<?>> {

	/**
	 * 获取主键
	 */
	public long getId();

	/**
	 * 属性更新覆盖。
	 * 
	 * @param domain
	 */
	public void updateSet(T domain);

	/**
	 * 为刷新而准备的复制。
	 * 
	 * @return
	 */
	public T cloneDomain();

	/**
	 * 获取对象信息
	 * 
	 * @return
	 */
	public String showObj();

	/**
	 * 对象保存时需要执行的逻辑
	 * 
	 * @return
	 */
	public T domainSave();

}
