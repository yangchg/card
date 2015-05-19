package com.owl.card.common.config;

import com.owl.card.common.config.proto.ProtoBase;

public interface ProtoCache<T extends ProtoBase> {

	/**
	 * 预加载，这里主要负责建立只包含ID的模板，然后存入模板表中，供正式加载时建立引用。
	 * 
	 * @Param dataInit
	 */
	public void preInit(CfgPool dataInit);

	/**
	 * 初始化模板中的数据，并建立相关的引用。
	 * 
	 * @Param dataInit
	 */
	public void init(CfgPool dataInit);

	/**
	 * 最终加载。
	 * 
	 * @param dataInit
	 */
	public void postInit(CfgPool dataInit);

	/**
	 * 根据ID获取模板
	 * 
	 * @param id
	 * @return
	 */
	public T getById(Object id);

}
