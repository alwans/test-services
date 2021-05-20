package com.services.core;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.services.core.mapper")
public class TestServicesCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServicesCoreApplication.class, args);
	}

}
