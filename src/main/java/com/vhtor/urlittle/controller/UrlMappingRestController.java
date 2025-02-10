package com.vhtor.urlittle.controller;

import com.vhtor.urlittle.service.UrlMappingService;
import org.springframework.web.bind.annotation.RestController;

@RestController("/mapping")
public class UrlMappingRestController {
  private final UrlMappingService urlMappingService;

  public UrlMappingRestController(UrlMappingService urlMappingService) {
    this.urlMappingService = urlMappingService;
  }
}
