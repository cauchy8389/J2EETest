package testspringcloud;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TestYaml {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = new SpringApplicationBuilder(
				TestYaml.class).properties(
				"spring.config.location=classpath:/my-config.yml").run(args);
		// 输出变量
		System.out.println(context.getEnvironment().getProperty("jdbc.user"));
		System.out.println(context.getEnvironment().getProperty("jdbc.passwd"));
		System.out.println(context.getEnvironment().getProperty("jdbc.driver"));
	}

}
