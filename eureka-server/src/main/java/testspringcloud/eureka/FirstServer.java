package testspringcloud.eureka;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication(excludeName={"com.github.trang.druid.autoconfigure.DruidAutoConfiguration",
        "com.github.trang.druid.autoconfigure.DruidDataSourceInitializerAutoConfiguration",
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"})
@EnableEurekaServer
public class FirstServer {

    public static void main(String[] args) {
        new SpringApplicationBuilder(FirstServer.class).
                properties("spring.config.location=classpath:/eureka-server.yml").run(args);
    }

}