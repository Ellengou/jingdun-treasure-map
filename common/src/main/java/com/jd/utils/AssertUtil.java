package com.jd.utils;

/**
 * 常用断言处理
 * 
 * @author Li ZiJun
 * @Time 2007-5-23 上午09:32:26
 */
public abstract class AssertUtil {

    /**
     * 构造方法
     */
    private AssertUtil(){
    }

    /**
     * 字符串不能等于null或空，否则抛出异常
     * 
     * @param obj 被判断的字符串
     * @param objName 字符串变量的名称
     */
    public static void notEmpty(String obj, String msg) {
        if (obj == null || obj.trim().equals("")) {
            // TODO throw new MyBizLayerException(msg);
        }
    }

    /**
     * 对象不能为null，否则抛出异常
     * 
     * @param obj 被判断的对象
     * @param objName 对象的名字
     */
    public static void notNull(Object obj, String msg) {
        if (obj == null) {
            // TODO throw new MyBizLayerException(msg);
        }
    }

    /**
     * 对象不存在
     * 
     * @param obj
     */
    public static void notExist(String obj) {
        if (obj == null || obj.trim().equals("")) {
            // TODO throw new MyBizLayerException("操作失败");
        }
    }

    /**
     * 对象不存在
     * 
     * @param obj
     */
    public static void notExist(String[] obj) {
        if (obj == null || obj.length == 0) {
            // TODO throw new MyBizLayerException("操作失败");
        }
    }
}
