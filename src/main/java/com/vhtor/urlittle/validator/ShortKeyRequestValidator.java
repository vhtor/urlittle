package com.vhtor.urlittle.validator;

import com.vhtor.urlittle.exception.BusinessRuleException;
import com.vhtor.urlittle.request.ShortKeyRequest;
import com.vhtor.urlittle.util.ValidatorUtils;
import org.springframework.stereotype.Service;

@Service
public class ShortKeyRequestValidator {
  private static final int MIN_LENGTH = 5;
  private static final int MAX_LENGTH = 10;
  private static final int LONG_URL_MAX_SIZE = 2048;

  public void validate(ShortKeyRequest request) {
    validateLength(request.length());
    validateLongUrl(request.longUrl());
  }

  public void validateLength(int length) {
    if (ValidatorUtils.isEmpty(length)) {
      throw new BusinessRuleException("Length of the short key must be provided");
    }

    if (length < MIN_LENGTH || length > MAX_LENGTH) {
      throw new BusinessRuleException("Length of the short key must be between 5 and 10");
    }
  }

  public void validateLongUrl(String longUrl) {
    if (longUrl.length() > LONG_URL_MAX_SIZE) {
      throw new BusinessRuleException("Long URL must be less than 2048 characters");
    }
  }
}
