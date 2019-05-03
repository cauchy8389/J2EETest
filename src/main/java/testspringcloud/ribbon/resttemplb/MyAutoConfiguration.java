package testspringcloud.ribbon.resttemplb;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Configuration
public class MyAutoConfiguration {

	@Autowired(required=false)
	@MyLoadBalanced
	private List<RestTemplate> myTemplates = Collections.emptyList();
	
	@Bean
	public SmartInitializingSingleton myLoadBalancedRestTemplateInitializer() {
		System.out.println("====  这个Bean将在容器初始化时创建    =====");
		return new SmartInitializingSingleton() {
			
			public void afterSingletonsInstantiated() {
				for(RestTemplate tpl : myTemplates) {
					// 创建一个自定义的拦截器实例
					MyInterceptor mi = new MyInterceptor();
					// 获取RestTemplate原来的拦截器
					List list = new ArrayList(tpl.getInterceptors());
					// 添加到拦截器集合
					list.add(mi);
					// 将新的拦截器集合设置到RestTemplate实例
					tpl.setInterceptors(list);
				}
			}
		};
	}
}
