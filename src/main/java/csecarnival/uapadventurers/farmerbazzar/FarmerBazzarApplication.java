package csecarnival.uapadventurers.farmerbazzar;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.MultipartConfigFactory;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "csecarnival.uapadventurers")
@EntityScan(basePackages = "csecarnival.uapadventurers.dto")
@EnableJpaRepositories(basePackages = "csecarnival.uapadventurers.repository")
public class FarmerBazzarApplication {


	public static void main(String[] args) {
		SpringApplication.run(FarmerBazzarApplication.class, args);
	}
}
