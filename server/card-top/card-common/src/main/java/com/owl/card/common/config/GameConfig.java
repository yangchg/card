package com.owl.card.common.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.owl.card.common.define.ConfigFilePath;
import com.owl.card.common.utils.LoadPrint;
import com.owl.card.common.utils.XmlConfig;

/**
 * 游戏的全局配置。
 * 
 * 
 */
public class GameConfig {

	/**
	 * 卡组最大卡牌数量
	 */
	public int GROUP_MAX_CARD_CNT = 1;

	/**
	 * 卡组最大数量
	 */
	public int MAX_GROUP_CNT = 1;

	public GameConfig() {

	}

	public void init() {

		Map<String, String> configMap = new HashMap<String, String>();
		XmlConfig baseValueConfig = new XmlConfig(ConfigFilePath.BASE_PATH, "r/l", true);
		List<RowData> baseValueRowDatas = baseValueConfig.load();
		for (RowData rowData : baseValueRowDatas) {
			String idStr = rowData.fetchStrValue("id");
			String valueStr = rowData.fetchStrValue("value");
			configMap.put(idStr, valueStr);
		}

		String groupEditCardUpperlimit = configMap.get("groupEditCardUpperlimit");
		GROUP_MAX_CARD_CNT = Integer.valueOf(groupEditCardUpperlimit);

		String cardGroupUpperlimit = configMap.get("CardGroupUpperlimit");
		MAX_GROUP_CNT = Integer.valueOf(cardGroupUpperlimit);

		LoadPrint.loadPrint("Game Config", 1);
	}

}
