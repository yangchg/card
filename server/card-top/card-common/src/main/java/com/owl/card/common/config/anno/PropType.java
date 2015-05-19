package com.owl.card.common.config.anno;

/**
 * 属性的类型
 * 
 * @author Ariane
 * 
 */
public enum PropType {

	/** 无法识别的类型 **/
	UNDEFINED,

	/** 复杂类型 **/
	COMPLEX_TYPE,

	/** 模板引用 **/
	PROTO_REF,

	/** 模板引用的列表 **/
	PROTO_REF_LIST,

	/** 语言引用 **/
	LANG_REF
}
