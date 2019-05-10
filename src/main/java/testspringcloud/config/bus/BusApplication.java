package testspringcloud.config.bus;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BusApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(BusApplication.class).
				properties("spring.config.location=classpath:/springcloud/config-bus.yml").web(WebApplicationType.SERVLET).run(args);
	}
}
