package testspringcloud.ribbon.config;

import testspringcloud.ribbon.MyPing;
import testspringcloud.ribbon.MyRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;

//此处Configuration 可以不用
@Configuration
public class MyConfig {
	@Bean
	public IRule getRule() {
		return new MyRule();
	}
	@Bean
	public IPing getPing() {
		return new MyPing();
	}
}
