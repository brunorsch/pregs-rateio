package br.app.pregsrateio.common.validation;

import static org.apache.commons.lang3.StringUtils.isBlank;

import org.bson.types.ObjectId;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MongoIdValidoValidator implements ConstraintValidator<MongoIdValido, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (isBlank(value)) {
            return false;
        }

        return ObjectId.isValid(value);
    }
}
