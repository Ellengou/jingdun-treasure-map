package com.jd.webkits.validators.annotations;

import com.jd.webkits.validators.IllegalPageConstraintValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created by nt on 2015-06-19.
 */
@Documented
@Constraint(validatedBy = IllegalPageConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IllegalPage {

    String message() default "{page parameters are  illegal}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
