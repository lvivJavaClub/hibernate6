package ua.lviv.javaclub.hibernate6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.ailef.snapadmin.external.SnapAdminAutoConfiguration;

@ImportAutoConfiguration(SnapAdminAutoConfiguration.class)
@SpringBootApplication
public class Hibernate6Application {

	public static void main(String[] args) {
		SpringApplication.run(Hibernate6Application.class, args);
	}

}
