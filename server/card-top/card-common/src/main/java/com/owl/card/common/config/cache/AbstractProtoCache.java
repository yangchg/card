package com.owl.card.common.config.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owl.card.common.config.CfgPool;
import com.owl.card.common.config.ProtoCache;
import com.owl.card.common.config.RowData;
import com.owl.card.common.config.proto.ProtoBase;
import com.owl.card.common.config.proto.ProtoBase.ProtoMetaData;
import com.owl.card.common.utils.LoadPrint;
import com.owl.card.common.utils.XmlConfig;

public abstract class AbstractProtoCache<T extends ProtoBase, ID> implements ProtoCache<T> {

	protected final String configFilePath;

	protected final Class<?> clazz;

	protected List<T> protos = new ArrayList<T>();
	protected Map<ID, T> protoMap = new HashMap<ID, T>();

	private List<ProtoMetaData> metas;

	public List<T> getProtos() {
		return protos;
	}

	public void setProtos(List<T> protos) {
		this.protos = protos;
	}

	public Map<ID, T> getProtoMap() {
		return protoMap;
	}

	public void setProtoMap(Map<ID, T> protoMap) {
		this.protoMap = protoMap;
	}

	public AbstractProtoCache(String configFilePath, Class<? extends ProtoBase> clazz) {
		this.configFilePath = configFilePath;
		this.clazz = clazz;

		this.metas = ProtoBase.generateMeta(clazz);
	}

	@Override
	final public void preInit(CfgPool dataInit) {
		XmlConfig config = new XmlConfig(configFilePath, "r/l", true);
		List<RowData> rowDatas = config.load();
		for (RowData rowData : rowDatas) {
			T proto;
			try {
				proto = (T) clazz.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				throw new RuntimeException("无法建立类型为" + clazz.getSimpleName() + "的实例");
			}

			proto.parse(metas, dataInit, rowData);
			ID id = (ID) proto.fetchProtoId();

			// 存入总List和ID_Map中。
			protos.add(proto);

			if (protoMap.get(id) != null) {
				throw new RuntimeException(configFilePath + "中存在ID为" + id + "的重复模板");
			}
			protoMap.put(id, proto);
			
			// 初始化
			doPreInit(proto, rowData);
		}

		LoadPrint.loadPrint(clazz.getSimpleName(), protos.size());
	}

	protected abstract void doPreInit(T proto, RowData rowData);

	@Override
	final public void init(CfgPool configManager) {
		for (T proto : protos) {
			proto.buildRef(metas, configManager);

			// 初始化
			doInit(proto, configManager);
		}
	}

	protected abstract void doInit(T proto, CfgPool configManager);

	@Override
	final public void postInit(CfgPool dataInit) {

	}

	@Override
	final public T getById(Object id) {
		return protoMap.get(id);
	}
}
