package testspringcloud.hystrix.dashboard;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class MyDashboard {

	public static void main(String[] args) {
		// 设置启动的服务器端口
		new SpringApplicationBuilder(MyDashboard.class).properties(
				"server.port=8082").run(args);
	}
}
