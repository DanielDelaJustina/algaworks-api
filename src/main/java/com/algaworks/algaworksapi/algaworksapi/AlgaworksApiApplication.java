package com.algaworks.algaworksapi.algaworksapi;

import com.algaworks.algaworksapi.algaworksapi.config.property.AlgaworksApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AlgaworksApiProperty.class)
public class AlgaworksApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgaworksApiApplication.class, args);
	}

}

