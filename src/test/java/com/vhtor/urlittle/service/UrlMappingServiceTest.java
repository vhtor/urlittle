package com.vhtor.urlittle.service;

import com.vhtor.urlittle.domain.UrlMapping;
import com.vhtor.urlittle.request.ShortKeyRequest;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import com.vhtor.urlittle.validator.ShortKeyRequestValidator;
import com.vhtor.urlittle.validator.UrlRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UrlMappingServiceTest {
  @Mock
  private DatabaseStrategy databaseStrategy;

  @Mock
  private ShortKeyRequestValidator shortKeyRequestValidator;

  @Mock
  private UrlRequestValidator urlRequestValidator;

  private UrlMappingService urlMappingService;

  @BeforeEach
  void setUp() {
    urlMappingService = spy(new UrlMappingService(
        shortKeyRequestValidator,
        urlRequestValidator,
        databaseStrategy
    ));
  }

  @Test
  void generateShortKey_MustReturnStringWithSpecifiedLength() {
    // ARRANGE
    final var length = 6;

    // ACT
    final var shortKey = urlMappingService.generateShortKey(length);

    // ASSERT
    assertEquals(length, shortKey.length());
  }

  @Test
  void generateShortKey_MustReturnExistingShortKeyForMappedUrl() {
    // ARRANGE
    final var existingMapping = UrlMapping.builder()
      .shortKey("shortKey")
      .longUrl("longUrl")
      .build();

    when(databaseStrategy.findByLongUrl(existingMapping.getLongUrl()))
        .thenReturn(Optional.of(existingMapping));

    // ACT
    final var shortKeyRequest = new ShortKeyRequest(existingMapping.getLongUrl(), 6);
    final var shortKey = urlMappingService.generateShortKey(shortKeyRequest);

    // ASSERT
    assert shortKey.equals(existingMapping.getShortKey());
  }

  @Test
  void generateShortKey_MustGenerateMoreThanOnceIfShortKeyAlreadyExists() {
    // ARRANGE
    final var existingShortKey = urlMappingService.generateShortKey(6);
    final var existingMapping = UrlMapping.builder()
      .shortKey(existingShortKey)
      .longUrl("longUrl")
      .build();

    when(databaseStrategy.findByShortKey(existingMapping.getShortKey()))
        .thenReturn(Optional.of(existingMapping))
        .thenReturn(Optional.empty());

    doReturn(existingShortKey)
        .doReturn("mockedShortKey")
        .when(urlMappingService).generateShortKey(anyInt());

    // ACT
    final var shortKeyRequest = new ShortKeyRequest(existingMapping.getLongUrl(), 6);
    final var generatedShortKey = urlMappingService.generateShortKey(shortKeyRequest);

    // ASSERT
    assertNotEquals(generatedShortKey, existingMapping.getShortKey());
    verify(urlMappingService, times(3)).generateShortKey(6);
  }

  @Test
  void generateShortKey_MustGenerateOnlyOnceIfShortKeyDoesNotExist() {
    // ARRANGE
    final var shortKey = "shortKey";
    final var longUrl = "longUrl";

    when(databaseStrategy.findByShortKey(shortKey))
        .thenReturn(Optional.empty());

    doReturn(shortKey)
        .when(urlMappingService).generateShortKey(anyInt());

    // ACT
    final var shortKeyRequest = new ShortKeyRequest(longUrl, 6);
    final var generatedShortKey = urlMappingService.generateShortKey(shortKeyRequest);

    // ASSERT
    assertEquals(shortKey, generatedShortKey);
    verify(urlMappingService, times(1)).generateShortKey(6);
  }
}
