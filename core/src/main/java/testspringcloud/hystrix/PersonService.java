package testspringcloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import testspringcloud.Person;

@Component
public class PersonService {

	@Autowired
	private RestTemplate restTemplate;


	@HystrixCommand(fallbackMethod = "getPersonFallback")
	public Person getPerson(Integer id) {
		// 使用RestTemplate调用Eureka服务
		Person p = restTemplate.getForObject(
				"http://first-service-provider/person/{personId}",
				Person.class, id);
		return p;
	}

	/**
	 * 回退方法，返回一个默认的Person
	 */
	public Person getPersonFallback(Integer id) {
		Person p = new Person();
		p.setId(0);
		p.setName("Crazyit");
		p.setAge(-1);
		p.setMessage("request error");
		return p;
	}
	
	/**
	 * 测试配置，对3个key进行命名
	 * 设置命令执行超时时间为1000毫秒
	 * 设置命令执行的线程池大小为1
	 */
	@HystrixCommand(
			fallbackMethod="testConfigFallback", groupKey="MyGroup", 
			commandKey="MyCommandKey", threadPoolKey="MyCommandPool", 
			commandProperties={
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", 
							value = "1000")
			}, 
			threadPoolProperties={
					@HystrixProperty(name = "coreSize", 
							value = "1")
			})
	public String testConfig() {
		try {
			Thread.sleep(1500);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	public String testConfigFallback() {
		return "error";
	}
	
	/**
	 * 声明了忽略MyException，如果方法抛出MyException，则不会触发回退
	 */
	@HystrixCommand(ignoreExceptions = {MyException.class}, 
			fallbackMethod="testExceptionFallBack")
	public String testException(String what) {
		if (what.equals("my"))
			throw new MyException();
		else
			throw new RuntimeException("hahaha");

		//return "good";
	}
	
	public String testExceptionFallBack(String what) {
		return what + "error";
	}
	
	
}
