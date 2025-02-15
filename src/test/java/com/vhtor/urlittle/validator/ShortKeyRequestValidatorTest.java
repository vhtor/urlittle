package com.vhtor.urlittle.validator;

import com.vhtor.urlittle.exception.BusinessRuleException;
import com.vhtor.urlittle.request.ShortKeyRequest;
import com.vhtor.urlittle.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ShortKeyRequestValidatorTest {
  private ShortKeyRequestValidator validator;

  @BeforeEach
  void setUp() {
    validator = new ShortKeyRequestValidator();
  }

  @Test
  void validateShortKey_MustValidateCharacterLength_Invalid() {
    // ARRANGE
    final var request = new ShortKeyRequest("https://github.com", 3);
    final var expectedMessage = "Short key must be between 5 and 10 characters";

    // ACT & ASSERT
    assertThrows(BusinessRuleException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateShortKey_MustValidateCharacterLength_Valid() {
    // ARRANGE
    final var request = new ShortKeyRequest("https://github.com", 5);

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateLongUrl_MustValidateUrlLength_Invalid() {
    // ARRANGE
    final var request = new ShortKeyRequest(TestUtils.reallyLongUrl(), 3);
    final var expectedMessage = "Long URL must be less than 2048 characters";

    // ACT & ASSERT
    assertThrows(BusinessRuleException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateLongUrl_MustValidateUrlLength_Valid() {
    // ARRANGE
    final var request = new ShortKeyRequest("https://github.com", 5);

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }
}
