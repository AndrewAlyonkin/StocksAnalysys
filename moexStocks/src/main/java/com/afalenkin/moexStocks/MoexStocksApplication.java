package com.afalenkin.moexStocks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MoexStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoexStocksApplication.class, args);
	}

}
