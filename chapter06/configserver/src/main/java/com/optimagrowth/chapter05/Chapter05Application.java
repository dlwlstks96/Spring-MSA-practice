package com.optimagrowth.chapter05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class Chapter05Application {

	public static void main(String[] args) {
		SpringApplication.run(Chapter05Application.class, args);
	}

}
