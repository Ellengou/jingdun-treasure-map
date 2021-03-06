package com.jd.utils;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 针对数组的工具类
 * 
 * @author zhaowl
 * 
 */
public class ArrayUtils extends org.apache.commons.lang3.ArrayUtils {
	/**
	 * 新建一个数组
	 * 
	 * @param elemets
	 * @return
	 */
	public static <T> T[] newArray(T... elemets) {
		return elemets;
	}

	/**
	 * 添加一个元素进入数组末尾
	 * 
	 * @param array
	 *            the array to add the element to, may be <code>null</code>
	 * @param element
	 *            the object to add
	 * @return A new array containing the existing elements and the new element
	 */
	public static <T> T[] addItem(T[] array, T element) {
		return addItem(array, getLength(array), element, false);
	}

	/**
	 * org.apache.commons.lang.ArrayUtils.add的泛型方法
	 * 
	 * @param array
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addItems(T[] array, T... elements) {
		return (T[]) ArrayUtils.add(array, elements);
	}

	/**
	 * org.apache.commons.lang.ArrayUtils.add的泛型方法
	 * 
	 * @param array
	 * @param index
	 * @param elements
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addItems(T[] array, int index, T... elements) {
		return (T[]) ArrayUtils.add(array, index, elements);
	}

	/**
	 * 将一个数值添加到指定位置
	 * 
	 * <pre>
	 * ArrayUtils.addItem([1,2],1,3) = [1,3,2]
	 * </pre>
	 * 
	 * @param array
	 *            the array to add the element to, may be <code>null</code>
	 * @param index
	 *            the position of the new object
	 * @param element
	 *            the object to add
	 * @return A new array containing the existing elements and the new element
	 */
	public static <T> T[] addItem(T[] array, int index, T element) {
		return addItem(array, index, element, false);
	}

	/**
	 * 将一个数值添加到指定位置
	 * 
	 * <pre>
	 * ArrayUtils.addItem([1,2],1,3,false) = [1,3,2]
	 * ArrayUtils.addItem([1,2],1,3,true)  = [1,3]
	 * </pre>
	 * 
	 * @param array
	 *            the array to add the element to, may be <code>null</code>
	 * @param index
	 *            the position of the new object
	 * @param element
	 *            the object to add
	 * @param cover
	 *            use cover old element
	 * @return A new array containing the existing elements and the new element
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] addItem(T[] array, int index, T element, boolean cover) {
		T[] result = array.clone();
		if (index > getLength(array))
			result = Arrays.copyOf(result, index);
		else if (cover) {
			result[index] = element;
			return result;
		}
		return (T[]) add(result, index, element);
	}

	/**
	 * 取出数组中的一个数值
	 * 
	 * <pre>
	 * ArrayUtils.get([1,2],1,3) = 2
	 * ArrayUtils.get([1,2],3,3) = 3
	 * </pre>
	 * 
	 * @param array
	 *            the array to add the element to, may be <code>null</code>
	 * @param index
	 *            the position of the new object
	 * @param def
	 *            if ArrayIndexOutOfBoundsException to return default
	 * @return
	 */
	public static <T> T get(T[] array, int index, T def) {
		try {
			return array == null ? def : array[index];
		} catch (ArrayIndexOutOfBoundsException e) {
			return def;
		}
	}

	/**
	 * 取出数组中的一个数值
	 * 
	 * <pre>
	 * ArrayUtils.get([1,2],1) = 2
	 * ArrayUtils.get([1,2],3) = null
	 * </pre>
	 * 
	 * @param array
	 *            the array to add the element to, may be <code>null</code>
	 * @param index
	 *            the position of the new object
	 * @return
	 */
	public static <T> T get(T[] array, int index) {
		return get(array, index, null);
	}

	/**
	 * 获取数组最后一个值
	 * 
	 * <pre>
	 * ArrayUtils.getLast(null)    = null;
	 * ArrayUtils.getLast({1,2,3}) = 3;
	 * </pre>
	 * 
	 * @param array
	 *            数组
	 * @return 数组最后一个值
	 * 
	 */
	public static <T> T getLast(T[] array) {
		return getLast(array, null);
	}

	/**
	 * 获取数组最后一个值
	 * 
	 * <pre>
	 * ArrayUtils.getLast(null,1)        =1;
	 * ArrayUtils.getLast({1,2,3},null)  =3;
	 * </pre>
	 * 
	 * @param array
	 *            数组
	 * @param def
	 *            默认值
	 * @return 数组最后一个值
	 */
	public static <T> T getLast(T[] array, T def) {
		try {
			return array == null ? def : array[array.length - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			return def;
		}
	}

	/**
	 * 获取数组的长度
	 * 
	 * <pre>
	 * ArrayUtils.length(null)     = 0;
	 * ArrayUtils.length({1,2,3})  = 3;
	 * </pre>
	 * 
	 * @param array
	 * @return 数组长度
	 */
	public static int length(Object[] array) {
		if (array == null)
			return 0;
		return array.length;
	}

	/**
	 * 通过数组字符串转换为数组
	 * 
	 * @param source
	 * @return
	 */
	public static String[] fromString(String source) {
		return fromString(source, new String[0]);
	}

	/**
	 * 返回一个用逗号分开的字符串
	 * 
	 * @param array
	 * @return 1,2,3
	 */
	public static String toSimpleString(Object[] array) {
		StringBuffer sb = new StringBuffer(org.apache.commons.lang.ArrayUtils.toString(array));
		if (sb.length() > 0)
			return sb.substring(1, sb.length() - 1);
		return "";
	}

	public static String toSimpleString(Object[] array, String stringIfNull) {
		StringBuffer sb = new StringBuffer(org.apache.commons.lang.ArrayUtils.toString(array, stringIfNull));
		if (sb.length() > 0)
			return sb.substring(1, sb.length() - 1);
		return "";
	}

	/**
	 * 将字符串转为相应的数组
	 * 
	 * @param source
	 * @param array
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] fromString(String source, T[] array) {
		try {
			return (T[]) ConvertUtils.converts(source, array.getClass());
		} catch (Exception e) {
			return (T[]) nullToEmpty(array);
		}
	}

	public static void main(String[] args) {
		List<String> s = ListUtils.newArrayList("sdfasd", "asdfsad", "asdfsad", null);
		List<Date> dd = ListUtils.newArrayList(new Date(), new Date(), null);
		String[] ss = s.toArray(new String[0]);
		System.out.println(toSimpleString(ss));
		System.out.println(ListUtils.toString(s, ""));
		System.out.println(ListUtils.toSimpleString(dd));
	}
}
