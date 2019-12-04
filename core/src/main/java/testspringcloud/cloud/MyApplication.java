package testspringcloud.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(excludeName={"com.github.trang.druid.autoconfigure.DruidAutoConfiguration",
		"com.github.trang.druid.autoconfigure.DruidDataSourceInitializerAutoConfiguration",
		"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
		"org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"})
//@ComponentScan
public class MyApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyApplication.class, args);
	}

}
