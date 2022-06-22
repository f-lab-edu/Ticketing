package com.ticketing.server.global.validator.constraintvalidators;

import com.ticketing.server.global.validator.constraints.FieldsValueNotMatch;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class FieldsValueNotMatchValidator implements ConstraintValidator<FieldsValueNotMatch, Object> {

	private String field;
	private String fieldMatch;

	public void initialize(FieldsValueNotMatch constraintAnnotation) {
		this.field = constraintAnnotation.field();
		this.fieldMatch = constraintAnnotation.fieldMatch();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		Object fieldValue = new BeanWrapperImpl(value).getPropertyValue(field);
		Object fieldMatchValue = new BeanWrapperImpl(value).getPropertyValue(fieldMatch);

		if (fieldValue != null) {
			return !fieldValue.equals(fieldMatchValue);
		} else {
			return fieldMatchValue != null;
		}
	}
}
