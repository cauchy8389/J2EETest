package testspringcloud.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import testspringcloud.feign.contract.HelloClient;

@RestController
@Configuration
public class InvokerController {

	@Autowired
	private PersonClient personClient;
	
	@RequestMapping(value = "/invokeHello", method = RequestMethod.GET)
	public String invokeHello() {
		return personClient.hello();
	}

	@RequestMapping(value = "/router", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String router() {
		// 调用服务提供者的接口
		Person p = personClient.getPerson(2);
		return p.getMessage();
	}

	@RequestMapping(value = "/router2", method = RequestMethod.GET,
			produces = MediaType.TEXT_PLAIN_VALUE)
	@ResponseBody
	public String router2() {
		// 调用服务提供者的接口
		String p = personClient.createPerson(new Person(6, "zhy", 22));
		return p;
	}
	
	@Autowired
	private HelloClient helloClient;
	
	@RequestMapping(value = "/testContract", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String testContract() {
		String springResult = helloClient.springHello();
		System.out.println("使用 @RequestMapping 注解的接口返回结果：" + springResult);
		String myResult = helloClient.myHello();
		System.out.println("使用 @MyUrl 注解的接口返回结果：" + myResult);
		return "";
	}
	
	/**
	 * 测试请求拦截器
	 */
	@RequestMapping(value = "/testInterceptors", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String testInterceptors() {
		String springResult = helloClient.springHello();
		return springResult;
	}
}
