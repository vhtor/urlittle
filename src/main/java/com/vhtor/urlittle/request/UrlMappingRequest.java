package com.vhtor.urlittle.request;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.util.From;

import java.time.Instant;

public record UrlMappingRequest (
    String shortKey,
    String longUrl,
    Instant expiration
) implements From<UrlMapping> {
  @Override
  public UrlMapping from() {
    return UrlMapping.builder()
        .shortKey(shortKey())
        .longUrl(longUrl())
        .expiration(expiration())
        .build();
  }
}
