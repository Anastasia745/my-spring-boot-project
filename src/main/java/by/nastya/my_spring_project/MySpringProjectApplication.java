package by.nastya.my_spring_project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("by.nastya.my_spring_project.database.repository")
public class MySpringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySpringProjectApplication.class, args);
	}

}
