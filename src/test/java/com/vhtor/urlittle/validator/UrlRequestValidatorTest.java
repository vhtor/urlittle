package com.vhtor.urlittle.validator;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.exception.BusinessRuleException;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import com.vhtor.urlittle.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlRequestValidatorTest {
  private UrlRequestValidator validator;

  @Mock
  private DatabaseStrategy databaseStrategy;

  @BeforeEach
  void setUp() {
    validator = new UrlRequestValidator(databaseStrategy);
  }

  @Test
  void validateShortKey_MustValidateRequiredField() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "",
        "https://github.com",
        null);
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
        null);
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
        null);

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateShortKey_MustValidatePattern_Invalid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde@",
        "https://github.com",
        null);
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
        null);

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateShortKey_MustValidateExistingShortKey() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "https://github.com",
        null);

    final var existingMapping = UrlMapping.builder()
        .shortKey("abcde")
        .longUrl("https://existing.com")
        .build();

    final var expectedMessage = "Short key already exists, please choose another one";

    // ACT
    when(databaseStrategy.findByShortKey("abcde")).thenReturn(Optional.of(existingMapping));

    // ASSERT
    assertThrows(DuplicateKeyException.class, () -> validator.validate(request), expectedMessage);
  }

  @Test
  void validateLongUrl_MustValidateRequiredField() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "",
        null);
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
        null);
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
        null);

    // ACT & ASSERT
    assertDoesNotThrow(() -> validator.validate(request));
  }

  @Test
  void validateLongURL_MustValidatePattern_Invalid() {
    // ARRANGE
    final var request = new UrlMappingRequest(
        "abcde",
        "a".repeat(2048),
        null);

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
        Instant.now().minusSeconds(1));
    final var expectedMessage = "Expiration date must be greater than current date";

    // ACT & ASSERT
    assertThrows(IllegalArgumentException.class, () -> validator.validate(request), expectedMessage);
  }
 }
