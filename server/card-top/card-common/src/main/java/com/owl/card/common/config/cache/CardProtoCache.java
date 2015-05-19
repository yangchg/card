package com.owl.card.common.config.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owl.card.common.config.CfgPool;
import com.owl.card.common.config.ProtoCache;
import com.owl.card.common.config.RowData;
import com.owl.card.common.config.proto.CardProto;
import com.owl.card.common.config.proto.ProtoBase;
import com.owl.card.common.config.proto.ProtoBase.ProtoMetaData;
import com.owl.card.common.define.ConfigFilePath;
import com.owl.card.common.utils.XmlConfig;

public class CardProtoCache implements ProtoCache<CardProto> {

	private List<CardProto> protos = new ArrayList<CardProto>();

	private Map<Integer, CardProto> protoMap = new HashMap<Integer, CardProto>();

	private List<ProtoMetaData> metas = ProtoBase.generateMeta(CardProto.class);

	@Override
	public void preInit(CfgPool dataInit) {
		XmlConfig xmlConfig = new XmlConfig(ConfigFilePath.BASE_CARD_PATH, "r/l", true);
		List<RowData> rowDatas = xmlConfig.load();

		for (RowData rowData : rowDatas) {
			CardProto proto = new CardProto();
			proto.parse(metas, dataInit, rowData);

			protos.add(proto);
			protoMap.put(proto.getId(), proto);
		}
	}

	@Override
	public void init(CfgPool dataInit) {

	}

	@Override
	public void postInit(CfgPool dataInit) {

	}

	@Override
	public CardProto getById(Object id) {
		return protoMap.get(id);
	}

}
