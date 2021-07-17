package nacos.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class TestServer {
    public static void main(String[] args) {
        new SpringApplicationBuilder(TestServer.class).run(args);
    }
}
