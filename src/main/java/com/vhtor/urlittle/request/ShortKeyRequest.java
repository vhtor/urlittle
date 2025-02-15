package com.vhtor.urlittle.request;

public record ShortKeyRequest(
  String longUrl,
  int length
) {}
