package gr.aueb.cf.schoolspring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SchoolSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolSpringApplication.class, args);
	}

}
