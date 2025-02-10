package com.vhtor.urlittle.validator;

import com.vhtor.urlittle.exception.BusinessRuleException;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.util.ValidatorUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.regex.Pattern;

@Service
public class UrlRequestValidator {
  private static final Pattern SHORT_KEY_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]+$");
  private static final Pattern URL_PATTERN = Pattern.compile("^(https?|ftp)://[^\\s/$.?#].\\S*$");
  private static final int LONG_URL_MAX_SIZE = 2048;

  public void validate(UrlMappingRequest request) {
    validateShortKey(request.shortKey());
    validateLongUrl(request.longUrl());
    validateExpiration(request.expiration());
    validateClickCount(request.clickCount());
  }

  public void validateShortKey(String shortKey) {
    if (ValidatorUtils.isEmpty(shortKey)) {
      throw new IllegalArgumentException("Short key is required");
    }

    if (shortKey.length() < 5 || shortKey.length() > 10) {
      throw new BusinessRuleException("Short key must be between 5 and 10 characters");
    }

    if (!SHORT_KEY_PATTERN.matcher(shortKey).matches()) {
      throw new BusinessRuleException("Short key can only contain alphanumeric characters, underscores and hyphens");
    }
  }

  public void validateLongUrl(String longUrl) {
    if (ValidatorUtils.isEmpty(longUrl)) {
      throw new IllegalArgumentException("Long URL is required");
    }

    if (longUrl.length() > LONG_URL_MAX_SIZE) {
      throw new BusinessRuleException("Long URL must be less than 2048 characters");
    }

    if (!URL_PATTERN.matcher(longUrl).matches()) {
      throw new BusinessRuleException("Long URL must be a valid HTTP/HTTPS/FTP URL");
    }
  }

  public void validateExpiration(Instant expiration) {
    if (ValidatorUtils.isNotEmpty(expiration) && expiration.isBefore(Instant.now())) {
      throw new IllegalArgumentException("Expiration date must be greater than current date");
    }
  }

  public void validateClickCount(Long clickCount) {
    if (ValidatorUtils.isNotEmpty(clickCount) && clickCount < 0) {
      throw new IllegalArgumentException("Click count cannot be negative");
    }
  }
}
