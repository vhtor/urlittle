package com.vhtor.urlittle.service;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.request.ShortKeyRequest;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import com.vhtor.urlittle.validator.ShortKeyRequestValidator;
import com.vhtor.urlittle.validator.UrlRequestValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class UrlMappingService {
  private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
  private final ShortKeyRequestValidator shortKeyRequestValidator;
  private final UrlRequestValidator urlRequestValidator;
  private final DatabaseStrategy databaseStrategy;

  public UrlMappingService(ShortKeyRequestValidator shortKeyRequestValidator,
                           UrlRequestValidator urlRequestValidator,
                           DatabaseStrategy databaseStrategy
  ) {
    this.shortKeyRequestValidator = shortKeyRequestValidator;
    this.urlRequestValidator = urlRequestValidator;
    this.databaseStrategy = databaseStrategy;
  }

  public UrlMapping save(UrlMappingRequest request) {
    urlRequestValidator.validate(request);

    final var urlMapping = request.from();
    return this.databaseStrategy.save(urlMapping);
  }

  public Optional<UrlMapping> findByShortKey(String shortkey) {
    return databaseStrategy.findByShortKey(shortkey);
  }

  public String createShortKey(ShortKeyRequest request) {
    shortKeyRequestValidator.validate(request);

    // If the long URL already has a short key, return it
    final var existingMapping = databaseStrategy.findByLongUrl(request.longUrl());
    if (existingMapping.isPresent()) {
      return existingMapping.get().getShortKey();
    }

    // Generate a new short key without conflicts on the database
    String shortKey;
    do {
      shortKey = this.generateShortKey(request.length());
    } while (databaseStrategy.findByShortKey(shortKey).isPresent());

    return shortKey;
  }

  public String generateShortKey(int length) {
    final var random = new Random();
    final var shortKey = new StringBuilder(length);

    for (int i = 0; i < length; i++) {
      final var randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
      final var randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
      shortKey.append(randomChar);
    }

    return shortKey.toString();
  }
}
