package com.vhtor.urlittle.validator;

import com.vhtor.urlittle.domain.UrlMapping;
import org.springframework.stereotype.Service;

@Service
public class UrlMappingValidator {
  public void validate(UrlMapping urlMapping) {
    if (urlMapping.getShortKey() == null || urlMapping.getShortKey().isEmpty()) {
      throw new IllegalArgumentException("Short key cannot be null or empty");
    }

    if (urlMapping.getLongUrl() == null || urlMapping.getLongUrl().isEmpty()) {
      throw new IllegalArgumentException("Long URL cannot be null or empty");
    }

    if (urlMapping.getUser() == null) {
      throw new IllegalArgumentException("User cannot be null");
    }

    if (urlMapping.getCreatedAt() == null) {
      throw new IllegalArgumentException("Created at cannot be null");
    }

    if (urlMapping.getExpiration() == null) {
      throw new IllegalArgumentException("Expiration cannot be null");
    }

    if (urlMapping.getClickCount() == null) {
      throw new IllegalArgumentException("Click count cannot be null");
    }
  }
}
