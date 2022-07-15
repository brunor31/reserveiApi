package br.com.vemser.reservei.reserveiapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ReserveiApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReserveiApiApplication.class, args);
	}

}
