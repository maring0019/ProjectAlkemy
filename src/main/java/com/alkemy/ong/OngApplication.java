package com.alkemy.ong;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OngApplication {

	public static void main(String[] args) {
		SpringApplication.run(OngApplication.class, args);
	}

	//Para tener una instancia ModelMapper global para no estar instanciando cada rato
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		return mapper;
	}

}
