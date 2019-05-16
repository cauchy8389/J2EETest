package testspringcloud.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FirstInvoker {

	public static void main(String[] args) {

		new SpringApplicationBuilder(FirstInvoker.class).
				properties("spring.config.location=classpath:/springcloud/eureka-invoker.yml").run(args);
	}
}
