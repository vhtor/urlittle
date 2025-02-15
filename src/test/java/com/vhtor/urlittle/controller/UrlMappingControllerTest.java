package com.vhtor.urlittle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vhtor.urlittle.request.ShortKeyRequest;
import com.vhtor.urlittle.service.UrlMappingService;
import com.vhtor.urlittle.strategy.DatabaseStrategy;
import com.vhtor.urlittle.validator.ShortKeyRequestValidator;
import com.vhtor.urlittle.validator.UrlRequestValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UrlMappingController.class)
@Import(UrlMappingControllerTest.TestConfig.class)
class UrlMappingControllerTest {
  @TestConfiguration
  static class TestConfig {
    @Bean
    public UrlMappingService urlMappingService() {
      return mock(UrlMappingService.class);
    }

    @Bean
    public ShortKeyRequestValidator shortKeyRequestValidator() {
      return mock(ShortKeyRequestValidator.class);
    }

    @Bean
    public UrlRequestValidator urlRequestValidator() {
      return mock(UrlRequestValidator.class);
    }

    @Bean
    public DatabaseStrategy databaseStrategy() {
      return mock(DatabaseStrategy.class);
    }
  }

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private UrlMappingService urlMappingService;

  @BeforeEach
  void setup() {
    when(urlMappingService.createShortKey(any())).thenReturn("abc123");
  }

  @Test
  @WithMockUser
  void createShortKey_ShouldReturnAValidHttpResponse() throws Exception {
    final var request = new ShortKeyRequest("https://example.com", 6);
    final var expectedShortKey = "abc123";

    when(urlMappingService.createShortKey(any())).thenReturn(expectedShortKey);

    mockMvc.perform(get("/api/url/short-key")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(content().string(expectedShortKey));
  }
}
