package com.aprec.tristan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

//(exclude = { SecurityAutoConfiguration.class })
@SpringBootApplication
public class TristanApplication {

	public static void main(String[] args) {
		SpringApplication.run(TristanApplication.class, args);
	}

}
