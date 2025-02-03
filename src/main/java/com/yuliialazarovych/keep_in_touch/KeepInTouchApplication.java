package com.yuliialazarovych.keep_in_touch;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()

public class KeepInTouchApplication {

	public static void main(String[] args) {

		SpringApplication.run(KeepInTouchApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
