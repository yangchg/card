package com.owl.card.common.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Element;

public class RowData {

	private String fileName;
	private Element e;
	private String rowName;

	private Map<String, String> datas = new HashMap<String, String>();

	private String text; // xml包含的内容。

	/**
	 * 子行
	 */
	private List<RowData> subRows = new ArrayList<RowData>();

	public Element getE() {
		return e;
	}

	public void setE(Element e) {
		this.e = e;
	}

	public Map<String, String> getDatas() {
		return datas;
	}

	public void setDatas(Map<String, String> datas) {
		this.datas = datas;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	public RowData(String fileName, Element e) {
		this.fileName = fileName;
		this.rowName = e.getNodeName();
		this.e = e;
	}

	public List<RowData> getSubRows() {
		return subRows;
	}

	public void setSubRows(List<RowData> subRows) {
		this.subRows = subRows;
	}

	/**
	 * 添加列树形数据。
	 * 
	 * @param key
	 * @param value
	 */
	public void addProperty(String key, String value) {
		datas.put(key, value);
	}

	/**
	 * 以整数形式返回值。
	 * 
	 * @param key
	 * @return
	 */
	public int fetchIntValue(String key) {
		String value = datas.get(key);
		if (value == null) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "未配置。ID：" + datas.get("id"));
		}

		int num = 0;
		try {
			num = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "配置错误，未配置成整数。ID：" + datas.get("id"));
		}
		return num;
	}

	/**
	 * 以整数形式返回值。如果值不存在，返回默认值。
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public int fetchIntValueMayNull(String key, int defaultValue) {
		String value = datas.get(key);
		if (value == null) {
			return defaultValue;
		}

		int num = 0;
		try {
			num = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "配置错误，未配置成整数。ID：" + datas.get("id"));
		}
		return num;
	}

	/**
	 * 返回长整数形式的值。
	 * 
	 * @param key
	 * @return
	 */
	public long fetchLongValue(String key) {
		String value = datas.get(key);
		if (value == null) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "未配置。ID：" + datas.get("id"));
		}

		long num = 0;
		try {
			num = Long.parseLong(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "配置错误，未配置成整数。ID：" + datas.get("id"));
		}
		return num;
	}

	/**
	 * 返回字符串形式的值。
	 * 
	 * @param key
	 * @return
	 */
	public String fetchStrValue(String key) {
		String value = datas.get(key);
		if (value == null) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "未配置。ID：" + datas.get("id"));
		}
		return value;
	}

	/**
	 * 返回字符串形式的值。
	 * 
	 * @param key
	 * @return
	 */
	public String fetchStrValueCanNull(String key) {
		String value = datas.get(key);
		return value;
	}

	/**
	 * 返回浮点形式的值。
	 * 
	 * @param key
	 * @return
	 */
	public float fetchFloatValue(String key) {
		String value = datas.get(key);
		if (value == null) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "未配置。ID：" + datas.get("id"));
		}

		float num = 0;
		try {
			num = Float.parseFloat(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "配置错误，未配置成小数格式。ID：" + datas.get("id"));
		}
		return num;
	}

	/**
	 * 返回高精度浮点形式的值。
	 * 
	 * @param key
	 * @return
	 */
	public double fetchDoubleValue(String key) {
		String value = datas.get(key);
		if (value == null) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "未配置。ID：" + datas.get("id"));
		}

		double num = 0;
		try {
			num = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "配置错误，未配置成小数格式。ID：" + datas.get("id"));
		}
		return num;
	}
	
	/**
	 * 以整数形式返回值。如果值不存在，返回默认值。
	 * 
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public double fetchDoubleValueMayNull(String key, double defaultValue) {
		String value = datas.get(key);
		if (value == null) {
			return defaultValue;
		}

		double num = 0;
		try {
			num = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException("配置文件" + fileName + "中属性" + key + "配置错误，未配置成double。ID：" + datas.get("id"));
		}
		return num;
	}

}
