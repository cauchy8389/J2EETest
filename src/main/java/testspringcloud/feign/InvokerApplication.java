package testspringcloud.feign;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class InvokerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(InvokerApplication.class).
				properties("spring.config.location=classpath:/springcloud/feign-invoker.yml").run(args);
	}
}
