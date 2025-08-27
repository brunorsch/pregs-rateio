package br.app.pregsrateio.common.validation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<Telefone, String> {
    // Brazilian phone format: +55 (country code) + area code (2 digits) + number
    // Mobile: +55 XX 9XXXX-XXXX (11 digits after +55) - always 9 digits after area code, starts with 9
    // Landline: +55 XX XXXX-XXXX (10 digits after +55) - always 8 digits after area code, starts with 2-8
    private static final Pattern PHONE_REGEX = Pattern.compile("\\+55\\d{2}(9\\d{8}|[2-8]\\d{7})");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isBlank(value)) {
            return false;
        }

        return PHONE_REGEX.matcher(value).matches();
    }
}