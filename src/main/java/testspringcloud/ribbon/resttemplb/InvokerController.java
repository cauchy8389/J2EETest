package testspringcloud.ribbon.resttemplb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@Configuration
public class InvokerController {
	
	@Bean
	@MyLoadBalanced
	public RestTemplate getMyRestTemplate() {
		return new RestTemplate();
	}
	
	/**
	 * 浏览器访问的请求
	 */
	@RequestMapping(value = "/router", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String router() {
		RestTemplate restTpl = getMyRestTemplate();
		// 根据名称来调用服务，这个URI会被拦截器所置换
		String json = restTpl.getForObject("http://my-server/hello", String.class);
		return json;
	}
	
	/**
	 * 最终的请求都会转到这个服务
	 */
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "Hello World";
	}
}
