package com.betha.cursomc;

import com.betha.cursomc.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursomcApplication {
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}
}
