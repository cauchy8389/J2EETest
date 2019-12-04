package testspringcloud.trace.zipkin;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.builder.SpringApplicationBuilder;
//import zipkin2.server.internal.EnableZipkinServer;
//import zipkin2.server.internal.RegisterZipkinHealthIndicators;

@SpringBootApplication
//@EnableZipkinServer
public class ZkServerApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ZkServerApp.class)
//				.listeners(new RegisterZipkinHealthIndicators())
				.properties("spring.config.name=zipkin-server").run(args);
	}
}
