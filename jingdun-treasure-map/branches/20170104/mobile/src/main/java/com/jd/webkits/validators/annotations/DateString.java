package com.jd.webkits.validators.annotations;

import com.jd.webkits.validators.DateStringConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 是否是能按照指定格式转换为Date类型的String字符串
 *
 * @author Guoqw
 * @since 2015-07-28 09:53
 */
@Documented
@Constraint(validatedBy = DateStringConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface DateString {

    String message() default "{DateString is invalid}";

    String pattern() default "yyyyMMdd";

    /**
     * 是否可为空
     * @return
     */
    boolean canEmpty() default true;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
