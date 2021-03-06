package com.tring.customer.app;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ComponentScan(basePackages={"com.tring.customer"})
@PropertySource(value = { "classpath:/log4j.properties"})
public class CustomerApiApplication  {
	
	static Logger logger = LogManager.getLogger(CustomerApiApplication.class);
	
	public static void main(String[] args) {
		logger.debug("CustomerApiApplication is started intializing...........");
		SpringApplication.run(CustomerApiApplication.class, args);
	}
}
