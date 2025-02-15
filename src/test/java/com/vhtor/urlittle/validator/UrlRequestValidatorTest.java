package com.vhtor.urlittle.validator;

import com.vhtor.urlittle.domain.User;
import com.vhtor.urlittle.exception.BusinessRuleException;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlRequestValidatorTest {
  private UrlRequestValidator validator;

  @BeforeEach
  void setUp() {
    validator = new UrlRequestValidator();
  }

  @Test
  void validateShortKey_MustValidateRequiredField() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "",
        "https://github.com",
        new User(),
        null,
        0L
    );
    final var expectedMessage = "Short key is required";

    // ACT & ASSERT
    assertThrows(IllegalArgumentException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateShortKey_MustValidateCharacterLength_Invalid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcd",
        "https://github.com",
        new User(),
        null,
        0L
    );
    final var expectedMessage = "Short key must be between 5 and 10 characters";

    // ACT & ASSERT
    assertThrows(BusinessRuleException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateShortKey_MustValidateCharacterLength_Valid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "https://github.com",
        new User(),
        null,
        0L
    );

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateShortKey_MustValidatePattern_Invalid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde@",
        "https://github.com",
        new User(),
        null,
        0L
    );
    final var expectedMessage = "Short key can only contain alphanumeric characters, underscores and hyphens";

    // ACT & ASSERT
    assertThrows(BusinessRuleException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateShortKey_MustValidatePattern_Valid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde_",
        "https://github.com",
        new User(),
        null,
        0L
    );

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateLongUrl_MustValidateRequiredField() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "",
        new User(),
        null,
        0L
    );
    final var expectedMessage = "Long URL is required";

    // ACT & ASSERT
    assertThrows(IllegalArgumentException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateLongUrl_MustValidateUrlLength_Invalid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "a".repeat(2049),
        new User(),
        null,
        0L
    );
    final var expectedMessage = "Long URL must be less than 2048 characters";

    // ACT & ASSERT
    assertThrows(BusinessRuleException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateLongUrl_MustValidateUrlLength_Valid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        TestUtils.reallyLongUrl(),
        new User(),
        null,
        0L
    );

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateLongURL_MustValidatePattern_Invalid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "a".repeat(2048),
        new User(),
        null,
        0L
    );

    final var expectedMessage = "Long URL must be a valid HTTP/HTTPS/FTP URL";

    // ACT & ASSERT
    assertThrows(BusinessRuleException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateExpiration_MustValidatePastExpirationDate() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "https://github.com",
        new User(),
        Instant.now().minusSeconds(1),
        0L
    );
    final var expectedMessage = "Expiration date must be greater than current date";

    // ACT & ASSERT
    assertThrows(IllegalArgumentException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateClickCount_MustValidateNegativeClickCount() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "https://github.com",
        new User(),
        null,
        -1L
    );
    final var expectedMessage = "Click count cannot be negative";

    // ACT & ASSERT
    assertThrows(IllegalArgumentException.class, () -> validator.validate(request), expectedMessage);
  }
 }
