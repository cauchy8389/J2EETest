package testspringcloud.config.zuul;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableZuulProxy
@SpringBootApplication
@RestController
public class ZuulApplication {
	
	@Autowired
	private Environment env;

	@RequestMapping("/")
	public String home() {
		System.out.println("读取的值：" + env.getProperty("test.user.name"));
		return "Hello";
	}
	
	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulApplication.class).run(args);
		//VM option :
		//-Dspring.cloud.bootstrap.location=classpath:/springcloud/config-zuul-boot.yml
	}
}
