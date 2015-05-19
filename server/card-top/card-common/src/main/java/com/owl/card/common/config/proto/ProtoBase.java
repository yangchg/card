package com.owl.card.common.config.proto;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.owl.card.common.config.AppConfig;
import com.owl.card.common.config.CfgPool;
import com.owl.card.common.config.RowData;
import com.owl.card.common.config.adapter.ProtoPropAdapter;
import com.owl.card.common.config.anno.PropType;
import com.owl.card.common.config.anno.ProtoProperty;

/**
 * 配置基础模板。
 * 
 * 提供解析配置的接口。
 * 
 * @author Ariane
 * 
 */
public abstract class ProtoBase {

	public static final String PROTO_CLASS_PREFIX = "com.loong.caesar.common.proto.";

	public static final String NULL_MARK = "0";

	public static final String LINK_MARK = "^";
	public static final String LINK_MARK2 = "_";

	// 配置分割符
	public static final String SPILIT_LVL1 = ":";
	public static final String SPILIT_LVL2 = "%";
	public static final String SPILIT_LVL3 = "#";

	/** 方法类型 **/
	public static final int TYPE_SET = 1;
	public static final int TYPE_GET = 2;

	/**
	 * 获取模板的ID。
	 * 
	 * @return
	 */
	public abstract Object fetchProtoId();

	public void buildRef(List<ProtoMetaData> metas, CfgPool dataInit) {
		Object ownId = fetchProtoId();
		Class<?> thisObjClass = this.getClass();
		for (ProtoMetaData meta : metas) {
			Field field = meta.field;
			ProtoProperty protoProperty = meta.protoProperty;

			try {
				// 跳过不可能有模板引用的属性。
				if (field.getType().equals(int.class)) {
					continue;
				} else if (field.getType().equals(long.class)) {
					continue;
				} else if (field.getType().equals(double.class)) {
					continue;
				} else if (field.getType().equals(String.class)) {
					continue;
				} else if (field.getType().isEnum()) {
					continue;
				}

				PropType propType = protoProperty.configType();
				if (propType == PropType.UNDEFINED) {
					throw new RuntimeException("无法为简单类型的" + field.getName() + "字段建立模板引用");
				}

				// 解析剩余的属性，如果有模板引用，建立引用。
				if (propType == PropType.PROTO_REF || propType == PropType.LANG_REF) {
					// 这里，我们要做的就是将模板引用的代理对象替换为真正的模板对象。
					// 我们会从代理对象中得到引用的ID，然后从引用模板的Cache中找到目标对象。
					// 最后，将这个对象替换代理对象。
					Object fieldObj = getFieldObjPlus(meta, this);
					boolean canEmpty = protoProperty.canEmpty();
					Object trueRefObj = findRefObj(dataInit, ownId, thisObjClass, fieldObj, canEmpty);
					setFieldObjPlus(meta, this, trueRefObj);

				} else if (propType == PropType.PROTO_REF_LIST) {
					// 获取当前域的对象，这一定是个List。
					@SuppressWarnings("unchecked")
					List<ProtoBase> fieldObjs = (List<ProtoBase>) getFieldObjPlus(meta, this);
					List<Object> newFieldObjs = new ArrayList<Object>();
					for (ProtoBase fieldObj : fieldObjs) {
						Object trueRefObj = findRefObj(dataInit, ownId, thisObjClass, fieldObj, false);

						// 添加到列表中。
						if (trueRefObj == null) {
							throw new RuntimeException("域" + field.getName() + "中引用列表中引用的ID为" + fieldObj.fetchProtoId()
									+ "的对象找不到。");
						}

						newFieldObjs.add(trueRefObj);
					}

					setFieldObjPlus(meta, this, newFieldObjs);
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new RuntimeException("域" + field.getName() + "的引用无法成功关联");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException("域" + field.getName() + "的引用无法成功关联");
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new RuntimeException("域" + field.getName() + "的引用无法成功关联");
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				throw new RuntimeException("域" + field.getName() + "的引用无法成功关联");
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				throw new RuntimeException("域" + field.getName() + "的引用无法成功关联");
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException("域" + field.getName() + "的引用无法成功关联");
			}
		}
	}

	private Object findRefObj(CfgPool dataInit, // 模板池。
			Object ownId, // 拥有当前字段的实例的ID。
			Class<?> ownClass, // 拥有当前字段的类的类型。
			Object fieldObj, // 字段对象实例。
			boolean canEmpty)
			// 是否可空。
			throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		// 获取当前域的对象。
		if (fieldObj == null) {
			return null;
		}

		// 根据引用的对象的类型获取到对应类型的cache。
		Class<?> fieldClass = fieldObj.getClass(); // 字段域的类类型。
		StringBuilder str = new StringBuilder(fieldClass.getSimpleName());
		char firstChar = str.charAt(0); // 首字母变为小写。
		firstChar = Character.toLowerCase(firstChar);
		str.setCharAt(0, firstChar);
		str.append("Cache"); // 拼装出Cache类的名字。
		Class<?> cacheMgrClass = dataInit.getClass();
		Field cacheField = cacheMgrClass.getDeclaredField(str.toString()); // 获取DataInit中对应的Cache域。
		Object cacheObject = cacheField.get(dataInit); // 得到Cache对象实例。

		// 得到引用类的主键。
		Field idField = fieldClass.getDeclaredField("id");
		// Class<?> refIdType = idField.getType();
		Class<?> cacheClass = cacheObject.getClass();
		Object trueRefObj = null;

		// 从cache中得到引用类的实例，并重写。
		Object idObj = getFieldObj(idField, fieldObj);
		Method method = cacheClass.getMethod("getById", Object.class);
		trueRefObj = method.invoke(cacheObject, idObj);

		if (trueRefObj == null) {
			if (canEmpty) {
				return null;
			} else {
				throw new RuntimeException("ID为" + ownId.toString() + "的" + ownClass.getSimpleName() + "中引用的ID为"
						+ idObj + "的" + fieldClass.getSimpleName() + "找不到。");
			}
		}

		return trueRefObj;
	}

	public static List<ProtoMetaData> generateMeta(Class<?> c) {
		List<ProtoMetaData> metas = new ArrayList<ProtoMetaData>();
		Field[] fields = c.getDeclaredFields();
		for (Field field : fields) {
			ProtoProperty protoProperty = field.getAnnotation(ProtoProperty.class);
			if (protoProperty == null) {
				// 不是属性成员。
				continue;
			}

			String setMethodName = buildGetSetMethodName(TYPE_SET, field).toString();
			Method setMethod = null;
			String getMethodName = buildGetSetMethodName(TYPE_GET, field).toString();
			Method getMethod = null;
			try {
				setMethod = c.getDeclaredMethod(setMethodName, field.getType());
				getMethod = c.getDeclaredMethod(getMethodName);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				continue;
			} catch (SecurityException e) {
				e.printStackTrace();
				continue;
			}

			ProtoMetaData meta = new ProtoMetaData();
			meta.field = field;
			meta.protoProperty = protoProperty;
			meta.setMethod = setMethod;
			meta.getMethod = getMethod;
			metas.add(meta);
		}

		return metas;
	}

	public void parse(List<ProtoMetaData> metas, CfgPool configManager, RowData rowData) {
		for (ProtoMetaData meta : metas) {
			Field field = meta.field;
			Class<?> fieldType = field.getType();
			ProtoProperty protoProperty = meta.protoProperty;
			String configName = protoProperty.configName();

			Object obj = null;
			try {
				if (fieldType.equals(int.class)) {
					// 属性是int
					obj = parseInt(protoProperty, rowData);

				} else if (fieldType.equals(long.class)) {
					// 属性是long
					obj = parseLong(protoProperty, rowData);

				} else if (fieldType.equals(double.class)) {
					// 属性是double
					obj = parseDouble(protoProperty, rowData);

				} else if (fieldType.equals(String.class)) {
					// 属性是描述性文本
					obj = parseString(protoProperty, rowData);

				} else if (fieldType.isEnum()) {
					// 属性是枚举类型
					obj = parseEnum(protoProperty, rowData, field);

				} else {
					// 属性是复杂类型
					obj = parseComplex(protoProperty, configManager, rowData, field);
				}

				setFieldObjPlus(meta, this, obj);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				throw new RuntimeException("无法存入配置" + configName + "的值");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				throw new RuntimeException("无法存入配置" + configName + "的值");
			} catch (SecurityException e) {
				e.printStackTrace();
				throw new RuntimeException("无法存入配置" + configName + "的值");
			} catch (InvocationTargetException e) {
				e.printStackTrace();
				throw new RuntimeException("无法存入配置" + configName + "的值");
			}
		}
	}

	public static class ProtoMetaData {
		public Field field;
		public ProtoProperty protoProperty;
		public Method setMethod;
		public Method getMethod;
	}

	private void setFieldObjPlus(ProtoMetaData meta, Object obj, Object saveObj) {
		try {
			Method method = meta.setMethod;
			method.invoke(obj, saveObj);

		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + meta.field.getName() + "的值");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + meta.field.getName() + "的值");
		}
	}

	private Object getFieldObjPlus(ProtoMetaData meta, Object obj) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		Method method = meta.getMethod;
		return method.invoke(obj);
	}

	private Object parseComplex(ProtoProperty protoProperty, CfgPool configManager, RowData rowData, Field field) {
		// 可能是复杂类型。
		PropType propType = protoProperty.configType();
		if (propType == PropType.UNDEFINED) {
			throw new RuntimeException(field.getName() + "字段不是简单类型，并且未配置类型说明。");
		}

		Object obj = null;
		if (propType == PropType.COMPLEX_TYPE) {
			obj = parseComplexAttr(configManager, rowData, protoProperty);

		} else if (propType == PropType.PROTO_REF) {
			obj = parseProtoRefAttr(rowData, protoProperty);

		} else if (propType == PropType.PROTO_REF_LIST) {
			obj = parseProtoRefListAttr(rowData, protoProperty);

		} else if (propType == PropType.LANG_REF) {
			obj = parseLangRefAttr(rowData, protoProperty);
		} else {
			throw new RuntimeException(field.getName() + "字段不是简单类型，并且未配置类型说明。");
		}

		return obj;
	}

	private Object parseEnum(ProtoProperty protoProperty, RowData rowData, Field field) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String configName = protoProperty.configName();
		Class<?> fieldType = field.getType();
		String className = fieldType.getName();
		Method parseMethod;
		try {
			parseMethod = Class.forName(className).getMethod("attrParse", int.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("无法获取到" + className + "的attrParse方法");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("无法获取到" + className + "的attrParse方法");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("无法获取到" + className + "的attrParse方法");
		}
		int value = rowData.fetchIntValue(configName);
		Object valueObj = parseMethod.invoke(null, value);
		return valueObj;
	}

	private Object parseString(ProtoProperty protoProperty, RowData rowData) {
		String configName = protoProperty.configName();
		String value = rowData.fetchStrValue(configName);

		return value;
	}

	private Object parseDouble(ProtoProperty protoProperty, RowData rowData) {
		String configName = protoProperty.configName();
		double value = rowData.fetchDoubleValue(configName);

		return value;
	}

	private Object parseLong(ProtoProperty protoProperty, RowData rowData) {
		String configName = protoProperty.configName();
		long value = rowData.fetchLongValue(configName);

		return value;
	}

	private Object parseInt(ProtoProperty protoProperty, RowData rowData) {
		String configName = protoProperty.configName();
		int value = rowData.fetchIntValue(configName);

		return value;
	}

	private Object parseProtoRefListAttr(RowData rowData, ProtoProperty protoProperty) {
		String configName = protoProperty.configName();
		String refSplit = protoProperty.refSplit();

		// 属性是另一个模板的引用列表。
		String value = rowData.fetchStrValueCanNull(configName);
		if (value.equals(ProtoBase.NULL_MARK)) {
			throw new RuntimeException(configName + " 错误的属性格式");
		}

		List<Object> refList = new ArrayList<Object>();
		String[] refIdStrs = value.split(refSplit);
		if (refIdStrs.length == 0) {
			return refList;
		}

		// 获取引用类的类型。
		Class<?> refClass = protoProperty.refName();

		// 获取ID类型。
		Field idField = null;
		try {
			idField = refClass.getDeclaredField("id");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("无法访问到引用类 " + refClass.getSimpleName() + " 的ID域");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new RuntimeException("无法访问到引用类 " + refClass.getSimpleName() + " 的ID域");
		}

		for (String refIdStr : refIdStrs) {
			Object complexObject;
			try {
				complexObject = refClass.newInstance();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
				throw new RuntimeException("无法创建引用类 " + refClass.getSimpleName() + " 的实例");
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
				throw new RuntimeException("无法创建引用类 " + refClass.getSimpleName() + " 的实例");
			}

			if (idField.getType().equals(int.class)) {
				int id = Integer.parseInt(refIdStr);
				setFieldObj(idField, complexObject, id);

			} else if (idField.getType().equals(String.class)) {
				setFieldObj(idField, complexObject, refIdStr);
			}

			refList.add(complexObject);
		}

		return refList;
	}

	private Object parseProtoRefAttr(RowData rowData, ProtoProperty protoProperty) {
		String configName = protoProperty.configName();
		boolean canEmpty = protoProperty.canEmpty();

		// 属性是对另一种模板的引用

		// 建立模板对象的实例，赋予ID，方便之后的引用关联。
		ProtoBase complexObject = null;
		Class<?> refClass = protoProperty.refName();
		try {
			complexObject = (ProtoBase) refClass.newInstance();

		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到名字为 " + refClass.getSimpleName() + " 的引用类");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到名字为 " + refClass.getSimpleName() + " 的引用类");
		}
		if (complexObject == null) {
			throw new RuntimeException("找不到名字为 " + refClass.getSimpleName() + " 的引用类");
		}

		Field idField = null;
		try {
			idField = refClass.getDeclaredField("id");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("无法访问到引用类 " + refClass.getSimpleName() + " 的ID域");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new RuntimeException("无法访问到引用类 " + refClass.getSimpleName() + " 的ID域");
		}

		try {
			if (idField.getType().equals(int.class)) {
				int value = rowData.fetchIntValue(configName);
				if (value == 0) {
					if (canEmpty) {
						return null;
					} else {
						throw new RuntimeException("名字为" + configName + " 的ID域不能为空，但当前配置中使用了表示空值的ID值");
					}
				}

				setFieldObj(idField, complexObject, value);

			} else if (idField.getType().equals(String.class)) {
				String value = rowData.fetchStrValue(configName);
				if (value.equals(ProtoBase.NULL_MARK)) {
					if (canEmpty) {
						return null;
					} else {
						throw new RuntimeException("名字为" + configName + " 的ID域不能为空，但当前配置中使用了表示空值的ID值");
					}
				}

				setFieldObj(idField, complexObject, value);
			}

			return complexObject;

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + configName + "的值");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + configName + "的值");
		}
	}

	private Object parseComplexAttr(CfgPool configManager, RowData rowData, ProtoProperty protoProperty) {
		// 创建解析器
		Class<?> parserClass = protoProperty.parserClass();
		ProtoPropAdapter protoPropAdapter = createParser(parserClass);

		// 解析字符串属性值，生成属性实例列表。
		String configName = protoProperty.configName();
		int attrIndex = protoProperty.attrIndex();
		Object complexObject = protoPropAdapter.compile(configManager, rowData, configName, attrIndex);

		// 注册链接方法。
		AppConfig.cfgs.registerLinker(protoPropAdapter);

		return complexObject;
	}

	/**
	 * 创建解析器实例
	 * 
	 * @param clazz
	 * @return
	 */
	private ProtoPropAdapter createParser(Class<?> clazz) {
		ProtoPropAdapter complexObject = null;
		try {
			complexObject = (ProtoPropAdapter) clazz.newInstance();

		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到名字为 " + clazz.getSimpleName() + " 的属性类");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到名字为 " + clazz.getSimpleName() + " 的属性类");
		}
		if (complexObject == null) {
			throw new RuntimeException("找不到名字为 " + clazz.getSimpleName() + " 的属性类");
		}
		return complexObject;
	}

	private Object parseLangRefAttr(RowData rowData, ProtoProperty protoProperty) {
		String configName = protoProperty.configName();
		boolean canEmpty = protoProperty.canEmpty();

		// 属性是对另一种模板的引用

		// 建立模板对象的实例，赋予ID，方便之后的引用关联。
		ProtoBase complexObject = null;
		Class<?> refClass = protoProperty.refName();

		try {
			complexObject = (ProtoBase) refClass.newInstance();

		} catch (InstantiationException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到名字为 " + refClass.getSimpleName() + " 的引用类");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("找不到名字为 " + refClass.getSimpleName() + " 的引用类");
		}
		if (complexObject == null) {
			throw new RuntimeException("找不到名字为 " + refClass.getSimpleName() + " 的引用类");
		}

		Field idField = null;
		try {
			idField = refClass.getDeclaredField("id");
		} catch (SecurityException e) {
			e.printStackTrace();
			throw new RuntimeException("无法访问到引用类 " + refClass.getSimpleName() + " 的ID域");
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			throw new RuntimeException("无法访问到引用类 " + refClass.getSimpleName() + " 的ID域");
		}

		if (idField.getType().equals(String.class)) {
			int value = rowData.fetchIntValue(configName);
			if (value == 0) {
				if (canEmpty) {
					return null;
				} else {
					throw new RuntimeException("名字为" + configName + " 的ID域不能为空，但当前配置中使用了表示空值的ID值");
				}
			}

			String id = protoProperty.langRefPrefix() + value + protoProperty.langRefPostfix();
			setFieldObj(idField, complexObject, id);

		} else {
			throw new RuntimeException("名字为" + configName + " 的ID域不是字符串");
		}

		return complexObject;

	}

	/**
	 * 获取特定对象特定域的值。
	 * 
	 * @param field
	 * @param obj
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private Object getFieldObj(Field field, Object obj) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		String getMethodName = buildGetSetMethodName(TYPE_GET, field).toString();
		Method method = obj.getClass().getDeclaredMethod(getMethodName);
		return method.invoke(obj);
	}

	/**
	 * 设定特定对象特定域的值。
	 * 
	 * @param field
	 * @param obj
	 * @param saveObj
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	private void setFieldObj(Field field, Object obj, Object saveObj) {
		try {
			String setMethodName = buildGetSetMethodName(TYPE_SET, field).toString();
			Method method = obj.getClass().getDeclaredMethod(setMethodName, field.getType());
			method.invoke(obj, saveObj);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + field.getName() + "的值");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + field.getName() + "的值");
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException("无法存入配置" + field.getName() + "的值");
		}
	}

	/**
	 * 构建set或get方法的方法名。
	 * 
	 * @param field
	 * @return
	 */
	private static StringBuilder buildGetSetMethodName(int methodType, Field field) {
		StringBuilder methodName = new StringBuilder();
		if (methodType == TYPE_SET) {
			methodName.append("set");
		} else {
			methodName.append("get");
		}

		StringBuilder fieldName = new StringBuilder(field.getName());
		char firstChar = fieldName.charAt(0); // 首字母变为大写。
		firstChar = Character.toUpperCase(firstChar);
		fieldName.setCharAt(0, firstChar);

		methodName.append(fieldName);

		return methodName;
	}

}
