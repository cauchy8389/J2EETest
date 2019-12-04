package testspringcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestProfiles {

	public static void main(String[] args) {
		// 启动第二个Spring容器，指定端口为8081
		ConfigurableApplicationContext context2 = new SpringApplicationBuilder(
				TestProfiles.class)
				.properties(
						"spring.config.location=classpath:/test-profiles.yml")
				.properties("spring.profiles.active=mysql").properties("server.port=8081").run(args);
		// 输出变量
		System.out.println(context2.getEnvironment().getProperty("jdbc.driver"));
	}

}
