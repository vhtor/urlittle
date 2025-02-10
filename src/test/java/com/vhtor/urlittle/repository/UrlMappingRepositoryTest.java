package com.vhtor.urlittle.repository;

import com.vhtor.urlittle.domain.UrlMapping;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class UrlMappingRepositoryTest {
  @Autowired
  private UrlMappingRepository urlMappingRepository;

  @Test
  void save_ShouldPersistNewUrlMapping() {
    // ARRANGE
    final var urlMapping = UrlMapping.builder()
      .shortKey("abc123")
      .longUrl("https://example.com")
      .build();

    // ACT
    urlMappingRepository.save(urlMapping);
    final var retrievedUrlMapping = urlMappingRepository.findUrlMappingByShortKey("abc123");

    // ASSERT
    assertTrue(retrievedUrlMapping.isPresent());
    assertEquals("abc123", retrievedUrlMapping.get().getShortKey());
    assertEquals("https://example.com", retrievedUrlMapping.get().getLongUrl());
  }
}
