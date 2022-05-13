package com.ticketing.server.global.validator.constraintvalidators;

import com.ticketing.server.global.validator.constraints.Phone;
import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {

	private static final String PATTERN = "\\d{3}-\\d{4}-\\d{4}";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return Pattern.matches(PATTERN, value);
	}

}
