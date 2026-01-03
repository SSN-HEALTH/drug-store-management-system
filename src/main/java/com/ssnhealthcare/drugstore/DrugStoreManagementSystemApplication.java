package com.ssnhealthcare.drugstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.ssnhealthcare.drugstore")
public class DrugStoreManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DrugStoreManagementSystemApplication.class, args);
	}

}
