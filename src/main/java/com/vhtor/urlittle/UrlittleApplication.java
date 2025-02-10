package com.vhtor.urlittle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class UrlittleApplication {
	public static void main(String[] args) {
		EnvLoader.load();
		SpringApplication.run(com.vhtor.urlittle.UrlittleApplication.class, args);
	}
}
