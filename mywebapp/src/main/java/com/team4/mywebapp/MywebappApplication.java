package com.team4.mywebapp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("UserMapper")
public class MywebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MywebappApplication.class, args);
	}

}
