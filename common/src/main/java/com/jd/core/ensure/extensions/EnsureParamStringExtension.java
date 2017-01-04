package com.jd.core.ensure.extensions;

import com.jd.core.ensure.EnsureParam;
import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.utils.StringUtils;

/**
 * Created by weiduan on 2015/10/15.
 */
public class EnsureParamStringExtension extends EnsureParam<Object> {
    private String string;

    public EnsureParamStringExtension(String string) {
        super(string);
        this.string = string;
    }

    public EnsureParamStringExtension isNotNull(String errorCode) {
        if (string == null) {
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isNotEmpty(String errorCode) {
        if (StringUtils.isEmpty(string)) {
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isNotBlank(String errorCode){
        if(StringUtils.isBlank(string)){
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isEqualTo(String comparedString, String errorCode) {
        if (!StringUtils.equals(string, comparedString)) {
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamStringExtension isNotEqualTo(String comparedString, String errorCode) {
        if (StringUtils.equals(string, comparedString)) {
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }

}
