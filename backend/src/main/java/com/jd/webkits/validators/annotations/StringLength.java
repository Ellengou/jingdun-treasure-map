package com.jd.webkits.validators.annotations;
import com.jd.webkits.validators.StringLengthConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 验证字符串最大长度（中文占2个字符）
 * Created by Liling on 2015/11/10.
 */

@Documented
@Constraint(validatedBy = StringLengthConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringLength {

    String message() default "{StringLength is invalid}";

    int maxLength();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
