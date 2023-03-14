package com.example.proyectoapiv1;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoApiV1Application {

	public static void main(String[] args) {
		/*PropertyConfigurator.configure("log4j.properties");*/
		SpringApplication.run(ProyectoApiV1Application.class, args);
	}

}
