package testspringcloud.feign.contract;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Contract;
import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class MyConfig {

	/**
	 * 返回一个自定义的注解翻译器
	 */
    @Bean
    public Contract feignContract() {
        return new MyContract();
    }
    
    @Bean
    public RequestInterceptor getRequestInterceptorsA() {
    	return new RequestInterceptor() {

			public void apply(RequestTemplate template) {
				System.out.println("这是第一个请求拦截器");
			}    		
    	};
    }
    
    @Bean
    public RequestInterceptor getRequestInterceptorsB() {
    	return new RequestInterceptor() {
			public void apply(RequestTemplate template) {
				System.out.println("这是第二个请求拦截器");
			}
    	};
    }
}
