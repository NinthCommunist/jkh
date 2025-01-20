package ru.fast.bills;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JkhApplication {

	public static void main(String[] args) {
		SpringApplication.run(JkhApplication.class, args);
	}

}

//TODO refresh token + hasAuthority+развесть @PREAuthorize      SQL n+1    + validators   + pagination   +inspection for this.   + patcher for all services
