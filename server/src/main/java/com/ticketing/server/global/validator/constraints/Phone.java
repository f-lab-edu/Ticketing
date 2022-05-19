package com.ticketing.server.global.validator.constraints;

import com.ticketing.server.global.validator.constraintvalidators.PhoneValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PhoneValidator.class})
@Documented
public @interface Phone {

	String message() default "{validation.phone}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
