package testspringcloud.config.configclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class ClientApplication {

	@Autowired
	private Environment env;

	@RequestMapping("/")
	public String home() {
		String name = env.getProperty("test.user.name");
		return "Hello " + name;
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ClientApplication.class).
				web(WebApplicationType.SERVLET).run(args);
		//VM option :
		//-Dspring.cloud.bootstrap.location=classpath:/springcloud/config-client-boot.yml
	}
}
