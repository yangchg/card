package com.owl.card.game.db.dao;

import java.util.Date;

import com.owl.card.common.domain.Role;
import com.owl.card.common.persistence.GenericDao;

public interface RoleDao extends GenericDao<Role, Long> {

	/**
	 * 更新最近登陆时间
	 * 
	 * @param rolId
	 * @param date
	 */
	void updateLastLoginDate(long rolId, Date date);

}
