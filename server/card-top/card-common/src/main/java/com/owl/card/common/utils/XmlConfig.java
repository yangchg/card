package com.owl.card.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.owl.card.common.config.RowData;
import com.owl.card.common.define.ConfigFilePath;

/**
 * XML 配置
 * 
 * @author ariane
 * 
 */
public class XmlConfig {

	public static long allLoadTime = 0;

	private String xmlFile; // xml配置文件名
	private String nodePath;// 节点路径

	private List<RowData> rows = new ArrayList<RowData>();

	private boolean checkFileExist = true;

	public XmlConfig(String xmlFile, String nodePath) {
		this.xmlFile = ConfigFilePath.ROOT_PATH + xmlFile;
		this.nodePath = nodePath;
	}

	public XmlConfig(String xmlFile, String nodePath, boolean checkFileExist) {
		this.xmlFile = ConfigFilePath.ROOT_PATH + xmlFile;
		this.nodePath = nodePath;
		this.checkFileExist = checkFileExist;
	}

	/**
	 * 从配置文件中加载数据。
	 * 
	 * @return
	 */
	public List<RowData> load() {
		// 这里开始加载文本。
		long start = System.currentTimeMillis();
		try {
			// 获取文档构建器实例。
			Document doc = createDoc(this.xmlFile);
			if (doc == null) {
				return new ArrayList<RowData>();
			}

			// 利用XPath解析XML。
			XPath path = createPath();

			// 获取所有的l节点
			NodeList nodes = (NodeList) path.evaluate(this.nodePath, doc, XPathConstants.NODESET);
			loadSubNode(this.xmlFile, nodes, this.rows);

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			throw new RuntimeException("load " + xmlFile + "error: ParserConfigurationException");
		} catch (SAXException e) {
			e.printStackTrace();
			throw new RuntimeException("load " + xmlFile + "error: SAXException");
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("load " + xmlFile + "error: IOException");
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			throw new RuntimeException("load " + xmlFile + "error: XPathExpressionException");
		}

		long end = System.currentTimeMillis();
		allLoadTime += (end - start);
		return rows;
	}

	/**
	 * 加载子节点。
	 * 
	 * @param row
	 * @param nowE
	 */
	private void loadSubNode(String fileName, NodeList nodeList, List<RowData> rows) {
		int nodeNum = nodeList.getLength();
		for (int i = 0; i < nodeNum; i++) {
			Node no = nodeList.item(i);
			if (!(no instanceof Element)) {
				continue;
			}
			Element e = (Element) no;
			RowData rowData = new RowData(fileName, e);

			// 解析属性。
			NamedNodeMap map = e.getAttributes();
			int size = map.getLength();
			for (int index = 0; index < size; index++) {
				Node n = map.item(index);
				rowData.addProperty(n.getNodeName(), n.getTextContent());
			}

			// 解析内容
			String content = e.getTextContent();
			if (content != null) {
				rowData.setText(content);
			} else {
				rowData.setText("");
			}

			// 解析子行
			loadSubNode(fileName, e.getChildNodes(), rowData.getSubRows());

			rows.add(rowData);
		}
	}

	/**
	 * 构建XML文档实例。
	 * 
	 * @param fileName
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	private Document createDoc(String fileName) throws ParserConfigurationException, SAXException, IOException {
		// 获取文档构建器实例。
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// 读取XML
		File file = new File(fileName);
		InputStream is = null;
		if (file.exists()) {
			is = new FileInputStream(file);
		}
		if (is == null) {
			if (checkFileExist == false)
				return null;
			else
				throw new RuntimeException("找不到目标配置文件: " + fileName);
		}
		// 构建DOM文档。
		Document doc = builder.parse(is);

		return doc;
	}

	/**
	 * 构建XPath实例。
	 * 
	 * @return
	 */
	private XPath createPath() {
		XPathFactory f = XPathFactory.newInstance();
		XPath path = f.newXPath();
		return path;
	}

}
