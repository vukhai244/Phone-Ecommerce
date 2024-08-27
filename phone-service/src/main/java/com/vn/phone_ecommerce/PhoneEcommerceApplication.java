package com.vn.phone_ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PhoneEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhoneEcommerceApplication.class, args);
	}

}
