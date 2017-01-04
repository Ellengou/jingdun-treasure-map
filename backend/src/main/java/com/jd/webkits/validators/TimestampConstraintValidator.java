package com.jd.webkits.validators;

import com.jd.core.utils.DateUtils;
import com.jd.webkits.validators.annotations.SignTimestamp;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Date;

/**
 * Created by Zhenwei on 2015/5/30.
 */
public class TimestampConstraintValidator implements ConstraintValidator<SignTimestamp, String> {

    @Override
    public void initialize(SignTimestamp signTimestamp) {

    }

    @Override
    public boolean isValid(String timestamp, ConstraintValidatorContext constraintValidatorContext) {
        Date timestampDate = new Date(Long.valueOf(timestamp)*1000);
        //比较当前时间是否是和当前相差10分钟
        int betweenSeconds = DateUtils.getSecondsBetween(new Date(), timestampDate);
        if (Math.abs(betweenSeconds) > 300) {
            return false;
        }
        return true;
    }
}
