package com.jd.utils;

/**
 * @author Ellen.
 * @date 2016/11/30.
 * @since 1.0.
 * com.jd.utils .by jingdun.tech.
 */
import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.dozer.util.MappingValidator;

import java.util.ArrayList;
import java.util.List;

public final class DozerUtils {

    private static Mapper dozerMapper = new DozerBeanMapper();

    public static  List maps(final List source, Class destinationClass) {
        MappingValidator.validateMappingRequest(source, destinationClass);
        List desList = new ArrayList<>();
        for (Object src : source ){
            Object des = dozerMapper.map(src,destinationClass);
            desList.add(des);
        }
        return desList;
    }
}