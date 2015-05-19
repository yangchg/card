package com.owl.card.common.config;

import java.util.ArrayList;
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

	public List<Integer> groupMaxCounts = new ArrayList<Integer>();

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
		String[] groupCntArr = groupEditCardUpperlimit.split(":");

		for (String groupCnt : groupCntArr) {
			if (groupCnt.isEmpty())
				continue;

			groupMaxCounts.add(Integer.valueOf(groupCnt));
		}

		LoadPrint.loadPrint("Game Config", 1);
	}

}
