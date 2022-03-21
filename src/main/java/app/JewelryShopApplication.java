package app;

import app.Configuration.SecurityConfig;
import app.Persistance.Repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class JewelryShopApplication {
	public static void main(String[] args) {

		SpringApplication.run(JewelryShopApplication.class, args);
	}
}
