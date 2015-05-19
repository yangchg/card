package com.owl.card.common.config;

import java.util.ArrayList;
import java.util.List;

import com.owl.card.common.config.adapter.ProtoPropAdapter;
import com.owl.card.common.config.cache.CardProtoCache;
import com.owl.card.common.config.proto.ProtoBase;

public class CfgPool {

	private List<ProtoCache<? extends ProtoBase>> singletons = new ArrayList<ProtoCache<? extends ProtoBase>>();
	// 链接器
	private List<ProtoPropAdapter> linkers = new ArrayList<ProtoPropAdapter>();

	public GameConfig gameConfig;
	public CardProtoCache cardProtoCache;

	public void init() {
		singletons.clear();

		gameConfig = new GameConfig();
		gameConfig.init();

		cardProtoCache = new CardProtoCache();
		singletons.add(cardProtoCache);

		// 初步初始化
		for (ProtoCache<? extends ProtoBase> protoCache : singletons) {
			protoCache.preInit(this);
		}

		// 使用链接器建立深层引用链接
		for (ProtoPropAdapter linker : linkers) {
			linker.link();
		}
		linkers.clear();

		// 建立表之间的关联。
		for (ProtoCache<? extends ProtoBase> protoCache : singletons) {
			protoCache.init(this);
		}

		// 完成后续加载。
		for (ProtoCache<? extends ProtoBase> protoCache : singletons) {
			protoCache.postInit(this);
		}

		// 执行数据检测。
		dataCheck();
	}

	/**
	 * 注册链接器
	 * 
	 * @param linker
	 */
	public void registerLinker(ProtoPropAdapter linker) {
		linkers.add(linker);
	}

	public void dataCheck() {

	}
}
