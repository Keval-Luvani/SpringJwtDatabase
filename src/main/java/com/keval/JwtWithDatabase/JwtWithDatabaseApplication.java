package com.keval.JwtWithDatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JwtWithDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtWithDatabaseApplication.class, args);
	}

}
