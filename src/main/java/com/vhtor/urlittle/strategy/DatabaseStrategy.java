package com.vhtor.urlittle.strategy;

import com.vhtor.urlittle.domain.UrlMapping;

import java.util.Optional;

public interface DatabaseStrategy {
  UrlMapping save(UrlMapping urlMapping);

  Optional<UrlMapping> findByShortKey(String shortKey);

  Optional<UrlMapping> findByLongUrl(String longUrl);
}
