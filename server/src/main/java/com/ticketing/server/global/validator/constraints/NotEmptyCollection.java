package com.ticketing.server.global.validator.constraints;

import com.ticketing.server.global.validator.constraintvalidators.NotEmptyCollectionValidator;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyCollectionValidator.class)
public @interface NotEmptyCollection {

	String message() default "목록이 존재하지 않습니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
