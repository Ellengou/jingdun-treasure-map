package com.jd.webkits.validators;

import com.jd.webkits.validators.annotations.GreaterThan;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Check that the number being validated is greater than the minimum
 * value specified.
 *
 * @author Guoqw
 * @since 2015-07-27 19:14
 */
public class GreaterThanConstraintValidator implements ConstraintValidator<GreaterThan, Number> {

    private long minValue;

    @Override
    public void initialize(GreaterThan greaterThan) {
        this.minValue = greaterThan.value();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return false;
        }
        if (value instanceof BigDecimal) {
            return ((BigDecimal) value).compareTo(BigDecimal.valueOf(minValue)) == 1;
        } else if (value instanceof BigInteger) {
            return ((BigInteger) value).compareTo(BigInteger.valueOf(minValue)) == 1;
        } else {
            long longValue = value.longValue();
            return longValue > minValue;
        }
    }


}
