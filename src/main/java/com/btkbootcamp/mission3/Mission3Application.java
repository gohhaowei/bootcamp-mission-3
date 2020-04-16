package com.btkbootcamp.mission3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class Mission3Application {

	private static JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(Mission3Application.class, args);


	}

}
