package com.owl.card.common.utils;

import org.apache.log4j.Logger;

/**
 * 加载打印类。
 * 
 * @author ariane
 * 
 */
public class LoadPrint {

	private final static Logger logger = Logger.getLogger(LoadPrint.class);

	/*
	static {
		logger.setLevel(Level.INFO);
	}
	*/

	/**
	 * 打印加载过程。
	 * 
	 * @param loadType 加载项名称。
	 * @param num 加载数量。
	 */
	public static void loadPrint(String loadType, int num) {
		int lineLength = 80;
		int size = loadType.length();

		int remaining = lineLength - size;
		StringBuilder sb = new StringBuilder("加载: ");
		remaining -= sb.length();
		remaining -= 8;

		sb.append(loadType);
		for (int i = 0; i < remaining; i++) {
			sb.append('.');
		}
		sb.append("  " + num);

		logger.info(sb.toString());
	}
}
