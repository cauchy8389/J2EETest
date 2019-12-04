package testspringcloud.zuul.gateway.filter;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class FilterConfig {

	@Bean
	public MyFilter myFilter() {
		return new MyFilter();
	}
	
	@Bean
	public RestTemplateFilter restTemplateFilter(RestTemplate restTemplate) {
		return new RestTemplateFilter(restTemplate); // 注入RestTemplate
	}
	
	@Bean
	public ExceptionFilter exceptionFilter() {
		return new ExceptionFilter();
	}

	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
}
