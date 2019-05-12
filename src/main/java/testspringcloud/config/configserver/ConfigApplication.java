package testspringcloud.config.configserver;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
//import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@EnableConfigServer      //另外开一个项目 maven 引入 config server
public class ConfigApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ConfigApplication.class).
				properties("spring.config.location=classpath:/springcloud/config-server.yml").run(args);
	}

}

