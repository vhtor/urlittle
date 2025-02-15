package com.vhtor.urlittle.repository;

import com.vhtor.urlittle.domain.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, String> {
  Optional<UrlMapping> findUrlMappingByShortKey(String shortKey);

  Optional<UrlMapping> findUrlMappingByLongUrl(String longUrl);
}
