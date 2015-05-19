package com.owl.card.game.db.service.game.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.owl.card.common.domain.Role;
import com.owl.card.game.db.dao.RoleDao;
import com.owl.card.game.db.service.game.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public Role findByRoleId(long roleId) {
		return roleDao.findById(roleId);
	}

	@Override
	public long createRole(Role role) {
		return roleDao.save(role);
	}
}
