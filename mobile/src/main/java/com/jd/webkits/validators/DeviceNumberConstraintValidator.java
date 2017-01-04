package com.jd.webkits.validators;

import com.jd.core.context.ApplicationContextHelper;
import com.jd.core.exceptions.XExceptionFactory;
import com.jd.core.utils.StringUtils;
import com.jd.webkits.context.XContext;
import com.jd.webkits.validators.annotations.DeviceNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Jintao on 2015/5/28.
 */
public class DeviceNumberConstraintValidator implements ConstraintValidator<DeviceNumber, String> {

    @Override
    public void initialize(DeviceNumber mid) {

    }

    @Override
    public boolean isValid(String deviceNumberParam, ConstraintValidatorContext constraintValidatorContext) {

        XContext context = XContext.getCurrentContext();

        return context.isInited();
    }
}
