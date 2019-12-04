package testspringcloud.zuul.bookservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BookApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(BookApplication.class).
				properties("spring.config.location=classpath:/springcloud/zuul-book-service.yml").properties(
				"server.port=9000").run(args);
	}
}
