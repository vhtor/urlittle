package com.vhtor.urlittle.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "url_mappings")
public class UrlMapping {
  @Id
  @Column(name = "short_key")
  private String shortKey;

  @Column(name = "long_url")
  private String longUrl;

  @Column(name = "created_at")
  private Instant createdAt = Instant.now();

  @Column(name = "expiration")
  private Instant expiration;

  @Column(name = "click_count")
  private Long clickCount = 0L;

  public UrlMapping() {
  }

  public UrlMapping(String shortKey, String longUrl, Instant createdAt, Instant expiration, Long clickCount) {
    this.shortKey = shortKey;
    this.longUrl = longUrl;
    this.createdAt = createdAt;
    this.expiration = expiration;
    this.clickCount = clickCount;
  }

  public static UrlMapping builder() {
    return new UrlMapping();
  }

  public UrlMapping shortKey(String shortKey) {
    this.shortKey = shortKey;
    return this;
  }

  public UrlMapping longUrl(String longUrl) {
    this.longUrl = longUrl;
    return this;
  }

  public UrlMapping createdAt(Instant createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  public UrlMapping expiration(Instant expiration) {
    this.expiration = expiration;
    return this;
  }

  public UrlMapping clickCount(Long clickCount) {
    this.clickCount = clickCount;
    return this;
  }

  public UrlMapping build() {
    return new UrlMapping(shortKey, longUrl, createdAt, expiration, clickCount);
  }

  public Instant getExpiration() {
    return expiration;
  }

  public void setExpiration(Instant expiration) {
    this.expiration = expiration;
  }

  public String getShortKey() {
    return shortKey;
  }

  public void setShortKey(String shortKey) {
    this.shortKey = shortKey;
  }

  public String getLongUrl() {
    return longUrl;
  }

  public void setLongUrl(String longUrl) {
    this.longUrl = longUrl;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

  public Long getClickCount() {
    return clickCount;
  }

  public void setClickCount(Long clickCount) {
    this.clickCount = clickCount;
  }
}
