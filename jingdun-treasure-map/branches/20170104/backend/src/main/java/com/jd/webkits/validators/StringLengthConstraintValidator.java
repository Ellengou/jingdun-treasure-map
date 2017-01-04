package com.jd.webkits.validators;

import com.jd.core.utils.StringUtils;
import com.jd.webkits.validators.annotations.StringLength;
import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 判断String字符串的长度（中文占2个字符，null值时长度为0）
 *
 * @author Guoqw
 * @since 2015-07-28 10:08
 */
public class StringLengthConstraintValidator implements ConstraintValidator<StringLength, String> {

    private static Logger logger = Logger.getLogger(DateStringConstraintValidator.class);

    private int maxLength;

    @Override
    public void initialize(StringLength stringLength) {
        this.maxLength = stringLength.maxLength();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return StringUtils.getStringLength(value) <= this.maxLength;
    }
}
