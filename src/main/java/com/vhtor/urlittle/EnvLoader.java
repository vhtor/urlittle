package com.vhtor.urlittle;

import io.github.cdimascio.dotenv.Dotenv;

public class EnvLoader {
  public static void load() {
    final var dotEnv = Dotenv.configure().load();
    System.setProperty("spring.datasource.url", dotEnv.get("DB_URL"));
    System.setProperty("spring.datasource.username", dotEnv.get("DB_USERNAME"));
    System.setProperty("spring.datasource.password", dotEnv.get("DB_PASSWORD"));
  }
}
