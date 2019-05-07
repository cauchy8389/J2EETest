package testspringcloud.zuul.gateway;

import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class MyConfig {

	/**
	 * 访问网关的 /xxxxx/**，将会被路由到 zuul-xxxxx-service 服务进行处理
	 */
	@Bean
	public PatternServiceRouteMapper patternServiceRouteMapper() {
		return new PatternServiceRouteMapper(
				"(zuul)-(?<module>.+)-(service)", "${module}/**");
	}
}
