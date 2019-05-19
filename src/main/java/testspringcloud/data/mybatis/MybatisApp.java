package testspringcloud.data.mybatis;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

//xkcoding 借鉴
@SpringBootApplication
public class MybatisApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MybatisApp.class).
				properties("spring.config.location=classpath:/springcloud/eureka-server.yml").run(args);
	}

}
