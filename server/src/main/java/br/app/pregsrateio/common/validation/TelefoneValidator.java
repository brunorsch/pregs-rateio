package br.app.pregsrateio.common.validation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<Telefone, String> {
    private static final Pattern PHONE_REGEX = Pattern.compile("\\+55\\d{10,11}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isBlank(value)) {
            return false;
        }

        return PHONE_REGEX.matcher(value).matches();
    }
}