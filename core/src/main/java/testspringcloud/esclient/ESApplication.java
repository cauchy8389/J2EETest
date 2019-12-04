package testspringcloud.esclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(excludeName={"com.github.trang.druid.autoconfigure.DruidAutoConfiguration",
        "com.github.trang.druid.autoconfigure.DruidDataSourceInitializerAutoConfiguration",
        "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"})
@EnableDiscoveryClient
public class ESApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ESApplication.class).
                properties("spring.config.location=classpath:/springcloud/elasticsearch-client.yml"
                ).run(args);
    }


}
