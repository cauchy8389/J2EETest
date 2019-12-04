package testspringcloud.zuul.saleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class SaleApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SaleApplication.class).
				properties("spring.config.location=classpath:/springcloud/zuul-sale-service.yml").run(args);
	}
}
