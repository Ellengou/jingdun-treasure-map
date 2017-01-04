package com.jd.webkits.validators.annotations;

import com.jd.webkits.validators.GreaterThanConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * The annotated element must be a number whose value must be higher the specified minimum.
 * <p/>
 * Supported types are:
 * <ul>
 *     <li>{@code BigDecimal}</li>
 *     <li>{@code BigInteger}</li>
 *     <li>{@code byte}, {@code short}, {@code int}, {@code long}, and their respective
 *     wrappers</li>
 * </ul>
 * Note that {@code double} and {@code float} are not supported due to rounding errors
 * <p/>
 *
 * @author Guoqw
 * @since 2015-07-27 19:16
 */
@Documented
@Constraint(validatedBy = GreaterThanConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GreaterThan {

    String message() default "{GreaterThan is invalid}";

    /**
     * @return value the element must be higher
     */
    long value();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
