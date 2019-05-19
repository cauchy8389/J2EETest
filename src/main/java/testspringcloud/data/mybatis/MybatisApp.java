package testspringcloud.data.mybatis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MybatisApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MybatisApp.class).
				properties("spring.config.location=classpath:/springcloud/eureka-server.yml").run(args);
	}

}
