package com.owl.card.game.db.dao.impl;

import org.springframework.stereotype.Repository;

import com.owl.card.common.domain.Role;
import com.owl.card.common.persistence.GameGenericDaoImpl;
import com.owl.card.game.db.dao.RoleDao;

@Repository("roleDao")
public class RoleDaoImpl extends GameGenericDaoImpl<Role, Long> implements RoleDao {

}
