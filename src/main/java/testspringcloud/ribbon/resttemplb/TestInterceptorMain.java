package testspringcloud.ribbon.resttemplb;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestInterceptorMain {

	public static void main(String[] args) {
		new SpringApplicationBuilder(TestInterceptorMain.class).
				properties("server.port=8080").run(args);

		//SpringApplication.run(TestInterceptorMain.class, args);
	}

}
