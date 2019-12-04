package testspringcloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import testspringcloud.Person;
import testspringcloud.hystrix.cache.CacheService;
import testspringcloud.hystrix.collapse.CollapseService;

import java.util.concurrent.Future;

@RestController
@Configuration
public class InvokerController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private CollapseService collapseService;

	@RequestMapping(value = "/router/{personId}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person router(@PathVariable Integer personId) {
		Person p = personService.getPerson(personId);
		return p;
	}
	
	@RequestMapping(value = "/testConfig", method = RequestMethod.GET)
	public String testConfig() {
		String result = personService.testConfig();
		return result;
	}
	
	@RequestMapping(value = "/testException/{my}", method = RequestMethod.GET)
	public String testException(@PathVariable String my) {
		String result = personService.testException(my);
		return result;
	}
	
	@RequestMapping(value = "/cache1/{personId}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person testCacheResult(@PathVariable Integer personId) {
		// 调用多次服务
		for(int i = 0; i < 3; i++) {
			Person p = cacheService.getPerson(personId);
			System.out.println("控制器调用服务 " + i);
		}
		return new Person();
	}
	
	@RequestMapping(value = "/cache2", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String testCacheRemove() {
		for(int i = 0; i < 3; i++) {
			cacheService.cacheMethod("a");
			System.out.println("控制器调用服务 " + i);
		}
		System.out.println("执行b：");
		cacheService.cacheMethod("b");
		// 清空缓存
		cacheService.updateMethod("a");		
		System.out.println("==========  清空了缓存");
		// 再执行多次
		for(int i = 0; i < 3; i++) {
			cacheService.cacheMethod("a");
			System.out.println("控制器调用服务 " + i);
		}		
		return "";
	}
	
	@RequestMapping(value = "/collapse", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public String testCollapse() throws Exception {
		// 连续执行3次请求
		Future<Person> f1 = collapseService.getSinglePerson(1);
		Future<Person> f2 = collapseService.getSinglePerson(2);
		Future<Person> f3 = collapseService.getSinglePerson(3);
		Person p1 = f1.get();
		Person p2 = f2.get();
		Person p3 = f3.get();
		System.out.println(p1.getId() + "---" + p1.getName());
		System.out.println(p2.getId() + "---" + p2.getName());
		System.out.println(p3.getId() + "---" + p3.getName());		
		return "";
	}
}
