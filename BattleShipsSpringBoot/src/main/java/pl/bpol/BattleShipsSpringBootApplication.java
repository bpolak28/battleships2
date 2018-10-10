package pl.bpol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableConfigurationProperties
@SpringBootApplication
public class BattleShipsSpringBootApplication extends SpringBootServletInitializer {
	
	

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		System.out.println("Inicjalizuję się...");
		return super.configure(builder);
	}

	public static void main(String[] args) {
		SpringApplication.run(BattleShipsSpringBootApplication.class, args);
	}
}
