package testspringcloud.config.configclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RefreshScope
public class ClientApplication {

	@Autowired
	private Environment env;

	@Value("${mybook}")
	private String mybook;

	@RequestMapping("/")
	public String home() {
		String name = env.getProperty("test.user.name");
		return "Hello " + name + mybook;
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ClientApplication.class).
				web(WebApplicationType.SERVLET).run(args);
		//VM option :
		//-Dspring.cloud.bootstrap.location=classpath:/springcloud/config-client-boot.yml
	}

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setIgnoreUnresolvablePlaceholders(true);
        return c;
    }
}
