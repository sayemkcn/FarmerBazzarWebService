package csecarnival.uapadventurers.farmerbazzar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "csecarnival.uapadventurers")
@EntityScan(basePackages = "csecarnival.uapadventurers.dto")
@EnableJpaRepositories(basePackages = "csecarnival.uapadventurers.repository")
public class FarmerBazzarApplication {


	public static void main(String[] args) {
		SpringApplication.run(FarmerBazzarApplication.class, args);
	}
}
