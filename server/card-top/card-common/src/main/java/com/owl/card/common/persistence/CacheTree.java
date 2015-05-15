package com.owl.card.common.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 数据缓存树。
 * 
 * 在每个worker中，worker根据需要，在特定的时候遍历这棵树，建立数据更新对象。
 * 
 * @author ariane
 * 
 */
public class CacheTree {

	/**
	 * 以Domain为Key的表。
	 */
	@JsonIgnore
	private Map<String, DomainCache> caches = new HashMap<String, DomainCache>();

	/**
	 * 根缓存。
	 */
	@JsonIgnore
	private List<DomainCache> rootCaches = new ArrayList<DomainCache>();

	public List<DomainCache> getRootCaches() {
		return rootCaches;
	}

	public void setRootCaches(List<DomainCache> rootCaches) {
		this.rootCaches = rootCaches;
	}

	public Map<String, DomainCache> getCaches() {
		return caches;
	}

	public void setCaches(Map<String, DomainCache> caches) {
		this.caches = caches;
	}

	/**
	 * 获取指定类型的缓存仓库。
	 * 
	 * @param domainName
	 * @return
	 */
	public DomainCache fetchSpecDomainCache(String domainName) {
		return caches.get(domainName);
	}

}
