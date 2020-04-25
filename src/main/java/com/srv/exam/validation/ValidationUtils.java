package com.srv.exam.validation;

import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {
  public <T> String getValidationErrorMessage(T createRequest) {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    StringBuffer errorMessage = new StringBuffer();

    Set<ConstraintViolation<T>> violations = validator.validate(createRequest);
    errorMessage.append(
        violations.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(",")));
    return errorMessage.length() > 0 ? errorMessage.toString() : null;
  }
}
