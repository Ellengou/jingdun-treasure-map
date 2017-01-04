package com.jd.utils;

/**
 * 
 * 处理Object的工具类
 * 
 * @author Wesley
 * 
 */
public class ObjectUtils extends org.apache.commons.lang3.ObjectUtils {

	/**
	 * 默认的Short:0
	 */
	public static final Short SHORT_DEF = 0;
	/**
	 * 默认的Float:0
	 */
	public static final Float FLOAT_DEF = 0f;
	/**
	 * 默认的Double:0
	 */
	public static final Double DOUBLE_DEF = 0d;
	/**
	 * 默认的Long:0
	 */
	public static final Long LONG_DEF = 0l;
	/**
	 * 默认的Number:0
	 */
	public static final Number NUMBER_DEF = 0;

	/**
	 * 是否为空
	 * 
	 * <pre>
	 * ObjectUtils.isNull("1", "2", null)  = true
	 * ObjectUtils.isNull("1", "2", "3")   = false
	 * </pre>
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isNull(Object... args) {
		for (Object o : args) {
			if (o == null)
				return true;
		}
		return false;
	}

	/**
	 * 
	 * 是否不为空
	 * 
	 * <pre>
	 * ObjectUtils.isNotNull("1", "2", null)  = false
	 * ObjectUtils.isNotNull("1", "2", "3")   = true
	 * </pre>
	 * 
	 * @param args
	 * @return
	 */
	public static boolean isNotNull(Object... args) {
		return !isNull(args);
	}

	/**
	 * 全部不为空
	 * 
	 * <pre>
	 * ObjectUtils.allIsNotNull("1", "2", null)  = false
	 * ObjectUtils.allIsNotNull("1","2","3")     = true
	 * </pre>
	 * 
	 * @param args
	 * @return
	 */
	public static boolean allIsNotNull(Object... args) {
		for (Object o : args) {
			if (o == null)
				return false;
		}
		return true;
	}

	/**
	 * 全部为空
	 * 
	 * <pre>
	 * ObjectUtils.allIsNull("1", "2", null)  = false
	 * ObjectUtils.allIsNull(null,null,null)  = true
	 * </pre>
	 * 
	 * @param args
	 * @return
	 */
	public static boolean allIsNull(Object... args) {
		for (Object o : args) {
			if (o != null)
				return false;
		}
		return true;
	}

	/**
	 * 判断如果为空，则返回相应的默认值
	 * 
	 * <pre>
	 * ObjectUtils.nullToDefault(null,"test")   = test
	 * ObjectUtils.nullToDefault("test",null)   = null
	 * ObjectUtils.nullToDefault(1,"test")      = 1
	 * </pre>
	 * 
	 * @param object
	 * @param defaultValue
	 * @return
	 */
	public static <T> T nullToDefault(T object, T def) {
		return object == null ? def : object;
	}

	/**
	 * 克隆对象
	 * 
	 * @param o
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T cloneObject(final T o) {
		return (T) org.apache.commons.lang3.ObjectUtils.clone(o);
	}

	/**
	 * 对象匹配返回设定值
	 * 
	 * <pre>
	 * ObjectUtils.equalsToValue("1", "1", "2")  = 2
	 * ObjectUtils.equalsToValue(null, "1", "2") = 1
	 * </pre>
	 * 
	 * @param source
	 *            比较对象
	 * @param base
	 *            源对象
	 * @param result
	 *            返回值
	 * @return
	 */
	public static <T> T equalsToValue(T source, T base, T result) {
		return ObjectUtils.equals(source, base) ? result : source;
	}

	/**
	 * 对象不匹配返回设定值
	 * 
	 * <pre>
	 * ObjectUtils.notEqualsToValue("1", "1", "2")  = 1
	 * ObjectUtils.notEqualsToValue(null, "1", "2") = 2
	 * </pre>
	 * 
	 * @param source
	 *            比较对象
	 * @param base
	 *            源对象
	 * @param result
	 *            返回值
	 * @return
	 */
	public static <T> T notEqualsToValue(T source, T base, T result) {
		return ObjectUtils.equals(source, base) ? source : result;
	}

	/**
	 * 强制类型转换
	 * 
	 * <pre>
	 * [A extends B]
	 * source[A]能强转成target[B]返回[@param source]
	 * ObjectUtils.softCast(A, B)               = A
	 * source[B]能强转成target[A]返回[@param target]
	 * ObjectUtils.softCast(B, A)               = B
	 * 
	 * ObjectUtils.softCast(12312, "dsfads")    = dsfads
	 * ObjectUtils.softCast(null, "dsfads")     = dsfads
	 * ObjectUtils.softCast(1, 2)               = 1
	 * </pre>
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T softCast(Object source, T target) {
		T result = target;
		try {
			if (ObjectUtils.isNull(source, target))
				return target;
			if (target.getClass().isAssignableFrom(source.getClass()))
				result = (T) source;
			return result;
		} catch (ClassCastException c) {
			result = target;
		}
		return result;
	}

	public static void main(String[] args) {
		// List<String> link = Lists.newLinkedList();
		// List<String> array = Lists.newArrayList("1", "2");
		// List<String> array2 = Lists.newArrayList("3", "4");
		// System.out.println(softCast("12312", array));
	}
}
