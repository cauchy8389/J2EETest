package testspringcloud.trace.streamclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication(excludeName={"com.github.trang.druid.autoconfigure.DruidAutoConfiguration",
		"com.github.trang.druid.autoconfigure.DruidDataSourceInitializerAutoConfiguration",
		"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"})
@RestController
public class StreamClientApp {

	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String hell() {
		System.out.println("调用业务方法");
		return "Hello";
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(StreamClientApp.class)
				.properties("spring.config.location=classpath:/springcloud/trace-stream-client.yml").run(args);
	}
}
