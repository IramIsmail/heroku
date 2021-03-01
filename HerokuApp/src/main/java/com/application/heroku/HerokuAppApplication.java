package com.application.heroku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class},scanBasePackages={
		"com.heroku.controllers","com.heroku.service","com.heroku.utils"})
//@ComponentScan({"com.heroku.controllers","com.heroku.service"})
public class HerokuAppApplication  extends SpringBootServletInitializer{

	public static void main(String[] args) {
		System.out.println("Application run hui");
		SpringApplication.run(HerokuAppApplication.class, args);
	}
}
