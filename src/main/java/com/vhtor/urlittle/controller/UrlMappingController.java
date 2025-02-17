package com.vhtor.urlittle.controller;

import com.vhtor.urlittle.dto.UrlMappingDTO;
import com.vhtor.urlittle.request.ShortKeyRequest;
import com.vhtor.urlittle.request.UrlMappingRequest;
import com.vhtor.urlittle.service.UrlMappingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class UrlMappingController {
  private final UrlMappingService urlMappingService;

  public UrlMappingController(UrlMappingService urlMappingService) {
    this.urlMappingService = urlMappingService;
  }

  @GetMapping("/short-key")
  public String createShortKey(@RequestBody ShortKeyRequest request) {
    return urlMappingService.generateShortKey(request);
  }

  @PostMapping
  public UrlMappingDTO createUrlMapping(@RequestBody UrlMappingRequest request) {
    return urlMappingService.createUrlMapping(request);
  }
}
