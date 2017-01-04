package com.jd.core.ensure.extensions;

import com.jd.core.ensure.EnsureParam;
import com.jd.core.exceptions.XExceptionFactory;

/**
 * Created by Jintao on 2015/6/9.
 */
public class EnsureParamBooleanExtension extends EnsureParam<Boolean> {
    private Boolean condition;

    public EnsureParamBooleanExtension(Boolean condition) {
        super(condition);
        this.condition = condition;
    }

    public EnsureParamBooleanExtension isFalse(String errorCode){
        if(condition){
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }

    public EnsureParamBooleanExtension isTrue(String errorCode){
        if(!condition){
            throw XExceptionFactory.create(errorCode);
        }
        return this;
    }
}
