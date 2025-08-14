package com.minhkha.backend.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class BirthDateValidator implements ConstraintValidator<BirthDate, LocalDate> {

    private int min;

    @Override
    public void initialize(BirthDate constraintAnnotation) {
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)) {
            return true;
        }
        long year = ChronoUnit.YEARS.between(value, LocalDate.now());
        return year >= min;
    }
}
