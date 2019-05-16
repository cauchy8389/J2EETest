package testspringcloud.trace.payservice;

import java.awt.print.Book;
import java.math.BigDecimal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class PayApplication {

	@RequestMapping(method = RequestMethod.GET, value = "/pay")
	public void doPay(@RequestParam("money") BigDecimal money) {
		System.out.println("支付模块处理支付，金额：" + money);
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(PayApplication.class)
				.properties("spring.config.location=classpath:/springcloud/trace-pay-service.yml").run(args);
	}
}
