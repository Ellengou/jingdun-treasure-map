package com.jd.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 处理字符串的工具类
 * 
 * @author zhaowl
 */
public class StringUtil extends org.apache.commons.lang3.StringUtils {

    public final static String COMMA     = ",";

    public final static String SEMICOLON = ";";

    public final static String DOT       = ".";

    public final static String bond(String... strs) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++)
            sb.append(strs[i]);
        return sb.toString();
    }

    /**
     * <p>
     * Splits the provided text into an array, using whitespace as the separator. Whitespace is defined by
     * {@link Character#isWhitespace(char)}.
     * </p>
     * <p>
     * The separator is not included in the returned String array. Adjacent separators are treated as one separator. For
     * more control over the split use the StrTokenizer class.
     * </p>
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.split(null)       = null
     * StringUtils.split("")         = []
     * StringUtils.split("abc def")  = ["abc", "def"]
     * StringUtils.split("abc  def") = ["abc", "def"]
     * StringUtils.split(" abc ")    = ["abc"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @return an array of parsed Strings, <code>null</code> if null String input
     */
    public static String[] splitToEmpty(String str) {
        return ArrayUtils.nullToEmpty(org.apache.commons.lang.StringUtils.split(str, COMMA));
    }

    public static boolean isAllBlank(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            if (org.apache.commons.lang.StringUtils.isNotBlank(str)) return false;
        }
        return true;
    }

    public static boolean isAllNotBlank(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String str = strings[i];
            if (org.apache.commons.lang.StringUtils.isBlank(str)) return false;
        }
        return true;
    }

    /**
     * <p>
     * Splits the provided text into an array, separator specified. This is an alternative to using StringTokenizer.
     * </p>
     * <p>
     * The separator is not included in the returned String array. Adjacent separators are treated as one separator. For
     * more control over the split use the StrTokenizer class.
     * </p>
     * <p>
     * A <code>null</code> input String returns <code>null</code>.
     * </p>
     * 
     * <pre>
     * StringUtils.split(null, *)         = null
     * StringUtils.split("", *)           = []
     * StringUtils.split("a.b.c", '.')    = ["a", "b", "c"]
     * StringUtils.split("a..b.c", '.')   = ["a", "b", "c"]
     * StringUtils.split("a:b:c", '.')    = ["a:b:c"]
     * StringUtils.split("a b c", ' ')    = ["a", "b", "c"]
     * </pre>
     * 
     * @param str the String to parse, may be null
     * @param separatorChar the character used as the delimiter
     * @return an array of parsed Strings, <code>null</code> if null String input
     * @since 2.0
     */
    public static String[] splitToEmpty(String str, String separatorChar) {
        return ArrayUtils.nullToEmpty(org.apache.commons.lang.StringUtils.split(str, separatorChar));
    }

    /**
     * 将字符串换成对应类型的值
     * 
     * @param source 来源字符串
     * @param defaultValue 转换未成功的默认值
     * @return
     */
    public static <T extends Object> T exchangeType(String source, T defaultValue) {
        return exchangeType(source, defaultValue.getClass(), defaultValue);
    }

    /**
     * 将字符串换成对应类型的值
     * 
     * @param s 来源字符串
     * @param clazz 目的类型
     * @return
     */
    public static <T extends Object> T exchangeType(String s, Class<T> clazz) {
        return exchangeType(s, clazz, null);
    }

    /**
     * 将字符串换成对应类型的值
     * 
     * @param s 来源字符串
     * @param clazz 目的类型
     * @param defaultValue 转换未成功的默认值
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Object> T exchangeType(String s, Class<? extends Object> clazz, T defaultValue) {
        Object o = null;
        try {
            if (clazz == Integer.class) {
                o = Integer.parseInt(s);
            } else if (clazz == Double.class) {
                o = Double.parseDouble(s);
            } else if (clazz == Long.class) {
                o = Long.parseLong(s);
            } else if (clazz == Short.class) {
                o = Short.parseShort(s);
            } else if (clazz == Float.class) {
                o = Float.parseFloat(s);
            } else if (clazz == Character.class) {
                o = s.charAt(0);
            } else if (clazz == Boolean.class) {
                o = Boolean.valueOf(s);
            } else if (clazz == Date.class) {
                o = TimeUtil.parseDate(s);
            } else if (clazz == java.sql.Date.class) {
                Date date = TimeUtil.parseDate(s);
                o = new java.sql.Date(date.getTime());
            }
        } catch (Exception e) {
            return defaultValue;
        }
        return (T) o;
    }

    /**
     * Check that the given CharSequence is neither <code>null</code> nor of length 0. Note: Will return
     * <code>true</code> for a CharSequence that purely consists of whitespace.
     * <p>
     * 
     * <pre>
     * StringUtils.hasLength(null) = false
     * StringUtils.hasLength("") = false
     * StringUtils.hasLength(" ") = true
     * StringUtils.hasLength("Hello") = true
     * </pre>
     * 
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not null and has length
     * @see #hasText(String)
     */
    public static boolean hasLength(CharSequence str) {
        return (str != null && str.length() > 0);
    }

    /**
     * Check that the given String is neither <code>null</code> nor of length 0. Note: Will return <code>true</code> for
     * a String that purely consists of whitespace.
     * 
     * @param str the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not null and has length
     * @see #hasLength(CharSequence)
     */
    public static boolean hasLength(String str) {
        return hasLength((CharSequence) str);
    }

    /**
     * Check whether the given CharSequence has actual text. More specifically, returns <code>true</code> if the string
     * not <code>null</code>, its length is greater than 0, and it contains at least one non-whitespace character.
     * <p>
     * 
     * <pre>
     * StringUtils.hasText(null) = false
     * StringUtils.hasText("") = false
     * StringUtils.hasText(" ") = false
     * StringUtils.hasText("12345") = true
     * StringUtils.hasText(" 12345 ") = true
     * </pre>
     * 
     * @param str the CharSequence to check (may be <code>null</code>)
     * @return <code>true</code> if the CharSequence is not <code>null</code>, its length is greater than 0, and it does
     * not contain whitespace only
     * @see Character#isWhitespace
     */
    public static boolean hasText(CharSequence str) {
        if (!hasLength(str)) {
            return false;
        }
        int strLen = str.length();
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check whether the given String has actual text. More specifically, returns <code>true</code> if the string not
     * <code>null</code>, its length is greater than 0, and it contains at least one non-whitespace character.
     * 
     * @param str the String to check (may be <code>null</code>)
     * @return <code>true</code> if the String is not <code>null</code>, its length is greater than 0, and it does not
     * contain whitespace only
     * @see #hasText(CharSequence)
     */
    public static boolean hasText(String str) {
        return hasText((CharSequence) str);
    }

    /**
     * 将Bean中的字符串去除所有的前后空格并且置为NULL
     * 
     * @param bean
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static <T> T beanTrimToNull(T bean) throws IllegalAccessException, InvocationTargetException,
                                              NoSuchMethodException {
        PropertyDescriptor[] descriptors = PropertyUtils.getPropertyDescriptors(bean);
        for (int i = 0; i < descriptors.length; i++) {
            String name = descriptors[i].getName();
            if (descriptors[i].getReadMethod() != null && descriptors[i].getWriteMethod() != null) {
                Object obj = PropertyUtils.getProperty(bean, name);
                if (obj != null && obj instanceof String) {
                    PropertyUtils.setProperty(bean, name, StringUtil.trimToNull((String) obj));
                }
            }
        }
        return bean;
    }

    /**
     * 字符串前补齐字符，如"123"补齐8位后为"00000123"
     * 
     * @param src 源字符串
     * @param c 补齐字符
     * @param num 补齐后总长度
     * @return
     */
    public static String fillCharacter(String src, char c, int num) {
        if (isBlank(src)) {
            return src;
        }
        char[] srcArr = src.toCharArray();
        if (srcArr.length >= num) {
            return src;
        }
        char[] charArr = new char[num];
        Arrays.fill(charArr, c);
        System.arraycopy(srcArr, 0, charArr, num - srcArr.length, srcArr.length);

        return new String(charArr);
    }

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException,
                                          NoSuchMethodException {
        // System.out.print(StringUtils.exchangeType("20141112", Date.class) +
        // "");
        // System.out.println(System.nanoTime());
        // System.out.println("tes" + "123123213");
        // System.out.println(System.nanoTime());
        // System.out.println(StringUtil.bond("tes", "123123213"));
        // System.out.println(System.nanoTime());
        System.out.println(fillCharacter("123", '0', 8));
    }
}
