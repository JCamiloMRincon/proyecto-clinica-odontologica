package com.dh.clas35;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Clas35Application {

	public static void main(String[] args) {
		SpringApplication.run(Clas35Application.class, args);
		PropertyConfigurator.configure("src/log4j.properties");
	}

}
