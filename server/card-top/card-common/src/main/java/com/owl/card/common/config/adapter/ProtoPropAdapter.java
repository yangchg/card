package com.owl.card.common.config.adapter;

import com.owl.card.common.config.CfgPool;
import com.owl.card.common.config.RowData;

/**
 * 模板属性适配器
 * 
 * @author ariane
 * 
 */
public interface ProtoPropAdapter {

	/**
	 * 对属性值做解析。
	 * 
	 * @param configManager
	 * @param rowData
	 * @param configName
	 * @param index
	 * @return
	 */
	public Object compile(CfgPool configManager, RowData rowData, String configName, int index);

	/**
	 * 建立深层引用链接
	 */
	public void link();

}
