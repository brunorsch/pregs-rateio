package br.app.pregsrateio.common.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintValidatorContext;

class TelefoneValidatorTest {
    
    private TelefoneValidator validator;
    private ConstraintValidatorContext context;
    
    @BeforeEach
    void setUp() {
        validator = new TelefoneValidator();
        context = null; // Not used in our implementation
    }
    
    @Test
    void shouldValidateBrazilianMobileNumbers() {
        // Valid Brazilian mobile numbers
        assertTrue(validator.isValid("+5511987654321", context)); // São Paulo mobile
        assertTrue(validator.isValid("+5521987654321", context)); // Rio de Janeiro mobile
        assertTrue(validator.isValid("+5585987654321", context)); // Ceará mobile
    }
    
    @Test
    void shouldValidateBrazilianLandlineNumbers() {
        // Valid Brazilian landline numbers (8 digits after area code, starting with 2-8)
        assertTrue(validator.isValid("+551123456789", context)); // São Paulo landline
        assertTrue(validator.isValid("+552123456789", context)); // Rio de Janeiro landline
        assertTrue(validator.isValid("+558534567890", context)); // Ceará landline
    }
    
    @Test
    void shouldRejectInvalidNumbers() {
        // Invalid numbers
        assertFalse(validator.isValid("", context)); // Empty
        assertFalse(validator.isValid(null, context)); // Null
        assertFalse(validator.isValid("  ", context)); // Blank
        assertFalse(validator.isValid("5511987654321", context)); // Missing country code
        assertFalse(validator.isValid("+551198765432", context)); // Too short
        assertFalse(validator.isValid("+55119876543210", context)); // Too long
        assertFalse(validator.isValid("+1234567890123", context)); // Wrong country code
        assertFalse(validator.isValid("+55abc87654321", context)); // Contains letters
    }
    
    @Test
    void shouldRejectIncompleteBrazilianNumbers() {
        // Brazilian format but incomplete
        assertFalse(validator.isValid("+55", context));
        assertFalse(validator.isValid("+5511", context));
        assertFalse(validator.isValid("+551198765", context));
    }
}