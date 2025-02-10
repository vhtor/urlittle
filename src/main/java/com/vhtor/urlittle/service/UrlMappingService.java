package com.vhtor.urlittle.service;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import com.vhtor.urlittle.validator.UrlRequestValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlMappingService {
  private final UrlRequestValidator urlRequestValidator;
  private final DatabaseStrategy databaseStrategy;

  public UrlMappingService(UrlRequestValidator urlRequestValidator, DatabaseStrategy databaseStrategy) {
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
}
