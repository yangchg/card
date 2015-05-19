package com.owl.card.game.db.service.game;

import com.owl.card.common.domain.Role;

public interface RoleService {

	/**
	 * 根据玩家编号获取玩家数据
	 * 
	 * @param roleId
	 * @return
	 */
	Role findByRoleId(long roleId);

	/**
	 * 创建玩家
	 * 
	 * @param role
	 * @return
	 */
	long createRole(Role role);

}
