package com.zeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class HouseApplication {
	public static ApplicationContext ac;

	public static void main(String[] args) {
		ac = SpringApplication.run(HouseApplication.class, args);
	}
}
