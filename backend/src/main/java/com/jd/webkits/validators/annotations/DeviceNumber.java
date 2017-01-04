package com.jd.webkits.validators.annotations;

import com.jd.webkits.validators.DeviceNumberConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by Jintao on 2015/5/28.
 */

@Documented
@Constraint(validatedBy = DeviceNumberConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DeviceNumber {
    String message() default "{DeviceNumber is invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
