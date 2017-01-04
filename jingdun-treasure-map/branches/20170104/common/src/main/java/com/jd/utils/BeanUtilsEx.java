package com.jd.utils;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

/**
 * Created by dongxc on 2015/6/3. 为了解决BeanUtils在copy空的Date型属性时会报错的问题
 */
public class BeanUtilsEx extends BeanUtils {

    static {
        ConvertUtils.register(new DateConvert(), java.util.Date.class);
        ConvertUtils.register(new DateConvert(), java.sql.Date.class);
    }

    public static void copyProperties(Object dest, Object orig) {
        /*try {
            BeanUtils.copyProperties(dest, orig);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InvocationTargetException ex) {
            ex.printStackTrace();
        }*/
        org.springframework.beans.BeanUtils.copyProperties(orig, dest);
    }

    public static void copyProperties2(Object dest, Object orig, String[] filed) {
        /*
         * try { BeanUtils.copyProperties(dest, orig); } catch (IllegalAccessException ex) { ex.printStackTrace(); }
         * catch (InvocationTargetException ex) { ex.printStackTrace(); }
         */
        org.springframework.beans.BeanUtils.copyProperties(orig, dest, filed);
    }

    public static void copyProperties2(Object dest, Object orig) {
        org.springframework.beans.BeanUtils.copyProperties(orig, dest);
    }
}
