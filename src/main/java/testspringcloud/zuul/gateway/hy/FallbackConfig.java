package testspringcloud.zuul.gateway.hy;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.context.annotation.Bean;

//@Configuration
public class FallbackConfig {

	@Bean
	public FallbackProvider saleFallbackProvider() {
		return new MyFallbackProvider();
	}
}
