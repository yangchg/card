package com.owl.card.common.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 缓存记录。
 * 
 * @author ariane
 * 
 */
public class DomainCache {

	public static final int CHILD_DIRTY = 1; // 子对象数据已被修改。
	public static final int OBJECT_DIRTY = 2; // 当前对象数据已被修改。

	/**
	 * 领取缓存名。
	 */
	private String domainCacheName;

	/**
	 * 缓存树。
	 */
	public CacheTree cacheTree;

	/**
	 * 父数据树
	 */
	public List<DomainCache> parantDomainCaches = new ArrayList<DomainCache>();

	/**
	 * 子数据树
	 */
	public List<DomainCache> childDomainCaches = new ArrayList<DomainCache>();

	/**
	 * 脏标记。
	 */
	private int dirty;

	/**
	 * 发生改变的记录。
	 */
	public Map<Long, Domain<?>> insertDomain = new HashMap<Long, Domain<?>>();
	public Map<Long, Domain<?>> updateDomain = new HashMap<Long, Domain<?>>();
	public Map<Long, Domain<?>> deleteDomain = new HashMap<Long, Domain<?>>();

	public int getDirty() {
		return dirty;
	}

	public void setDirty(int dirty) {
		this.dirty = dirty;
	}

	public DomainCache() {

	}

	public DomainCache(String domainCacheName, CacheTree cacheTree) {
		this.domainCacheName = domainCacheName;
		this.cacheTree = cacheTree;
	}

	/**
	 * 添加新增的数据对象（记录）
	 * 
	 * @param id
	 * @param domain
	 */
	public void addInsert(Long id, Domain<?> domain) {
		if (insertDomain.get(id) != null) {
			return;
		}
		insertDomain.put(id, domain);
		configDirty(OBJECT_DIRTY);
	}

	/**
	 * 添加更新的数据对象（记录）
	 * 
	 * @param id
	 * @param domain
	 */
	public void addUpdate(Long id, Domain<?> domain) {
		if (insertDomain.get(id) == null) {
			updateDomain.put(id, domain);
			configDirty(OBJECT_DIRTY);
		}
	}

	/**
	 * 添加删除的数据对象（记录）
	 * 
	 * @param id
	 * @param domain
	 */
	public void addDelete(Long id, Domain<?> domain) {
		if (insertDomain.get(id) != null) {
			insertDomain.remove(id);
			return;
		}
		deleteDomain.put(id, domain);
		if (updateDomain.get(id) != null) {
			updateDomain.remove(id);
		}
		configDirty(OBJECT_DIRTY);
	}

	/**
	 * 设置当前缓存或父缓存脏。
	 * 
	 * @param dirtyType
	 */
	public void configDirty(int dirtyType) {
		dirty = dirty | dirtyType;
		for (DomainCache parentDomainCache : parantDomainCaches) {
			if ((parentDomainCache.getDirty() & CHILD_DIRTY) > 0) {
				continue;
			}

			parentDomainCache.configDirty(CHILD_DIRTY);
		}
	}

	public String getDomainCacheName() {
		return domainCacheName;
	}

	public void clear() {
		insertDomain.clear();
		updateDomain.clear();
		deleteDomain.clear();

	}
}
