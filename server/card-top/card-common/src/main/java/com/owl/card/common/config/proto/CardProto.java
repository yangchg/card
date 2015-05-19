package com.owl.card.common.config.proto;

import com.owl.card.common.config.anno.ProtoProperty;

public class CardProto extends ProtoBase {

	@ProtoProperty(configName = "id")
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public Object fetchProtoId() {
		return id;
	}
}
