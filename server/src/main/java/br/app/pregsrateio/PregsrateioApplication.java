package br.app.pregsrateio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PregsrateioApplication {

	public static void main(String[] args) {
		SpringApplication.run(PregsrateioApplication.class, args);
	}

}
