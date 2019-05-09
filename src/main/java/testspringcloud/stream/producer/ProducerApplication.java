package testspringcloud.stream.producer;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableEurekaClient
@EnableBinding(value = {SendService.class, Source.class})
public class ProducerApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ProducerApplication.class).
				properties("spring.config.location=classpath:/springcloud/stream-producer.yml").run(args);
	}
}
