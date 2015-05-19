package com.owl.card.common.config;

import java.util.ArrayList;
import java.util.List;

import com.owl.card.common.config.adapter.ProtoPropAdapter;

public class CfgPool {

	// 链接器
	private List<ProtoPropAdapter> linkers = new ArrayList<ProtoPropAdapter>();

	public void init() {

	}

	/**
	 * 注册链接器
	 * 
	 * @param linker
	 */
	public void registerLinker(ProtoPropAdapter linker) {
		linkers.add(linker);
	}
}
