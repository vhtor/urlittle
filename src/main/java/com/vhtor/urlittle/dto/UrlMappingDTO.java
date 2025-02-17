package com.vhtor.urlittle.dto;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.util.From;

import java.time.Instant;

public record UrlMappingDTO(
  String shortKey,
  String longUrl,
  Instant expiration,
  Long clickCount
) implements From<UrlMapping> {
  @Override
  public UrlMapping from() {
    return UrlMapping.builder()
      .shortKey(shortKey())
      .longUrl(longUrl())
      .expiration(expiration())
      .clickCount(clickCount())
      .build();
  }
}
