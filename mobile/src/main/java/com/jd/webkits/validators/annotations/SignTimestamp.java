package com.jd.webkits.validators.annotations;

import com.jd.webkits.validators.TimestampConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Zhenwei on 2015/5/30.
 */
@Documented
@Constraint(validatedBy = TimestampConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SignTimestamp {

    String message() default "{时间戳范围不正确}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
