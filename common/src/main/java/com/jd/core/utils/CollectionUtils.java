package com.jd.core.utils;

import java.util.Collection;
import java.util.List;

/**
 * Created by Zhenwei on 2015/5/22.
 */
public class CollectionUtils {

    public static boolean isEmpty(Collection coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isNotEmpty(Collection coll) {
        return !isEmpty(coll);
    }

    public static boolean isEmpty(Object... objs) {return objs == null || objs.length <= 0;}

    public static <T> T getLast(List<T> list){
        int last_index = list.size() -1;
        return list.get(last_index);
    }
    
}
