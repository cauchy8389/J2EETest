package testspringcloud.trace;


import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import zipkin2.server.internal.EnableZipkinServer;
import zipkin2.server.internal.RegisterZipkinHealthIndicators;

@SpringBootApplication
@EnableZipkinServer
public class Zipkin2Server {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Zipkin2Server.class)
				.listeners(new RegisterZipkinHealthIndicators())
                .properties("spring.config.name=zipkin-server").run(args);

        //$ HTTP_COLLECTOR_ENABLED=false KAFKA_BOOTSTRAP_SERVERS=192.168.1.51:9092 java -jar zipkin.jar
    }
}
