package com.jd.webkits.validators;

import com.jd.webkits.validators.annotations.DateString;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 判断String字符串能否按照指定格式转换为Date类型
 *
 * @author Guoqw
 * @since 2015-07-28 10:08
 */
public class DateStringConstraintValidator implements ConstraintValidator<DateString, String> {

    private static Logger logger = Logger.getLogger(DateStringConstraintValidator.class);

    private String pattern;

    private boolean canEmpty;

    @Override
    public void initialize(DateString dateString) {
        this.pattern = dateString.pattern();
        this.canEmpty = dateString.canEmpty();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (canEmpty && StringUtils.isBlank(value)) {
            return true;
        }
        if (StringUtils.isBlank(pattern)) {
            return false;
        }
        DateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(value);
        } catch (ParseException e) {
            logger.error("DateStringConstraintValidator parse to date fail", e);
        }
        return date != null;
    }
}
