package com.owl.card.common.config.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.owl.card.common.config.adapter.DefaultProtoPropAdapter;
import com.owl.card.common.config.adapter.ProtoPropAdapter;
import com.owl.card.common.config.proto.DefaultProtoBase;
import com.owl.card.common.config.proto.ProtoBase;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ProtoProperty {

	/** 配置文件中字段的名字 **/
	String configName();

	/** 配置的数据类型 **/
	PropType configType() default PropType.UNDEFINED;

	/** 配置中是否可空 **/
	boolean canEmpty() default false;

	/** 解析器类名 **/
	Class<? extends ProtoPropAdapter> parserClass() default DefaultProtoPropAdapter.class;

	/** 如果是复杂类型或者复杂类型列表，那么类型名是什么 **/
	String typeName() default "@";

	/** 如果是复杂类型列表，那么列表分割符是什么 **/
	String typeSplit() default "%";

	/** 如果当前属性是个引用或引用列表，那么引用类名是什么 **/
	Class<? extends ProtoBase> refName() default DefaultProtoBase.class;

	/** 如果当前属性是个引用列表，那么列表分割符是什么 **/
	String refSplit() default "%";

	/** 如果是对语言文件的引用，那么ID前的前缀是什么 **/
	String langRefPrefix() default "@";

	/** 如果是对语言文件的引用，那么ID后的后缀是什么 **/
	String langRefPostfix() default "@";

	/** 解析为什么自定义类型 **/
	Class<?> parseType() default Object.class;

	/** 如何是属性解析，组别号 **/
	int attrIndex() default 1;
}
