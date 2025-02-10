package com.vhtor.urlittle.service;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import com.vhtor.urlittle.validator.UrlMappingValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UrlMappingService {
  private final UrlMappingValidator urlMappingValidator;
  private final DatabaseStrategy databaseStrategy;

  public UrlMappingService(UrlMappingValidator urlMappingValidator, DatabaseStrategy databaseStrategy) {
    this.urlMappingValidator = urlMappingValidator;
    this.databaseStrategy = databaseStrategy;
  }

  public UrlMapping save(UrlMappingRequest request) {
    final var urlMapping = request.from();
    urlMappingValidator.validate(urlMapping);

    return this.databaseStrategy.save(urlMapping);
  }

  public Optional<UrlMapping> findByShortKey(String shortkey) {
    return databaseStrategy.findByShortKey(shortkey);
  }
}
