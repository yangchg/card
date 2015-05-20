package com.owl.card.common.define;

import java.util.HashSet;
import java.util.Set;

/**
 * 英雄职业
 * 
 * @author YangChg
 *
 */
public class HeroCareerDefine {
	public final static int WARRIOR = 1; // 战士
	public final static int KNINGHT = 2; // 骑士
	public final static int WIZARD = 3; // 术士
	public final static int PASTOR = 4; // 牧师

	public final static Set<Integer> allCareers = new HashSet<Integer>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = -1973353756350595859L;

		{
			add(WARRIOR);
			add(KNINGHT);
			add(WIZARD);
			add(PASTOR);
		}
	};
}
