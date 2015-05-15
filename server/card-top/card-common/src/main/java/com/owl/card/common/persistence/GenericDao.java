package com.owl.card.common.persistence;

import java.io.Serializable;
import java.util.List;

/**
 * 这个接口定义了Dao的CRUD操作
 * 
 * @author witch
 * 
 * @param <T>
 * @param <ID>
 */
public interface GenericDao<T extends Domain<?>, ID extends Serializable> {

	/**
	 * 保存实体对象
	 * 
	 * @param transientInstance
	 *            实体对象
	 * @return 主键
	 */
	public ID save(Domain<?> transientInstance);

	/**
	 * 利用ID单查实体
	 * 
	 * @param id
	 *            实体对象的ID
	 * @return 实体
	 */
	public T findById(ID id);

	/**
	 * 找到所有对象
	 * 
	 * @return 实体集合。
	 */
	public List<T> findAll();

	/**
	 * 保存实体 包括添加和修改
	 * 
	 * @param instance
	 *            实体对象
	 */
	public void saveOrUpdate(T instance);

	/**
	 * 更新实体 可用于添加、修改、删除操作
	 * 
	 * @param transientInstance
	 *            实体对象
	 */
	public void update(T transientInstance);

	/**
	 * 删除实体
	 * 
	 * @param persistentInstance
	 *            实体对象
	 */
	public void delete(T persistentInstance);

	/**
	 * 根据ID删除特定对象。
	 * 
	 * @param id
	 */
	public void deleteById(ID id);

	/**
	 * 立即刷新数据库
	 */
	public void flush();

	/**
	 * 清空一级缓存
	 */
	public void clear();

	/**
	 * 获取最新的备选主键。
	 * 
	 * @return
	 */
	public long getLatestId();

	public void opeateServerId();
}
