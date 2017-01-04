package com.jd.utils;


import java.util.Collection;

/**
 * 
 * 针对Collection接口的工具类
 * 
 * @author Wesley
 * 
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {
	/**
	 * 将数组添加书Collection
	 * 
	 * @param collection
	 * @param elements
	 */
	@SuppressWarnings("rawtypes")
	public static void addAll(Collection collection, Object[] elements) {
		if (ArrayUtils.isEmpty(elements))
			return;
		org.apache.commons.collections.CollectionUtils.addAll(collection, elements);
	}

	/**
	 * 如果已经存在对象则不再添加
	 * 
	 * @param collection
	 * @param elements
	 */
	public static <T> void uniqAdd(Collection<T> collection, T elements) {
		if (collection == null)
			return;
		if (!collection.contains(elements))
			collection.add(elements);
	}

	/**
	 * 得到第一个返回值
	 * 
	 * @param coll
	 * @param def
	 *            默认值
	 * @return
	 */
	public static <T> T firstResult(Collection<T> coll, T def) {
		if (CollectionUtils.isEmpty(coll)) {
			return def;
		}
		T result = coll.iterator().next();
		if (result == null)
			return def;
		return result;
	}

	/**
	 * 得到第一个返回值
	 * 
	 * @param coll
	 * @return
	 */
	public static <T> T firstResult(Collection<T> coll) {
		return firstResult(coll, null);
	}

	/**
	 * 获取长度
	 * 
	 * <pre>
	 * CollectionUtils.length(null) = 0
	 * </pre>
	 * 
	 * @param object
	 * @return
	 */
	public static int length(Object object) {
		if (object == null)
			return 0;
		return CollectionUtils.size(object);
	}
}
