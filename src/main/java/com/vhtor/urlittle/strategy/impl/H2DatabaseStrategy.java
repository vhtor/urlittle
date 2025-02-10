package com.vhtor.urlittle.strategy.impl;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.repository.UrlMappingRepository;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("test")
public class H2DatabaseStrategy implements DatabaseStrategy {
  private final UrlMappingRepository urlMappingRepository;

  public H2DatabaseStrategy(UrlMappingRepository urlMappingRepository) {
    this.urlMappingRepository = urlMappingRepository;
  }

  @Override
  public UrlMapping save(UrlMapping urlMapping) {
    return this.urlMappingRepository.save(urlMapping);
  }

  @Override
  public Optional<UrlMapping> findByShortKey(String shortKey) {
    return this.urlMappingRepository.findUrlMappingByShortKey(shortKey);
  }
}
