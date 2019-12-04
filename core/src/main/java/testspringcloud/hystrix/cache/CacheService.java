package testspringcloud.hystrix.cache;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import org.springframework.stereotype.Component;
import testspringcloud.Person;

@Component
public class CacheService {

	@CacheResult
	@HystrixCommand
	public Person getPerson(Integer id) {
		System.out.println("执行 getPerson 方法");
		Person p = new Person();
		p.setId(id);
		p.setName("angus");
		return p;
	}

	/**
	 * 测试删除缓存
	 * 
	 * @param name
	 * @return
	 */
	@CacheResult()
	@HystrixCommand(commandKey = "removeKey")
	public String cacheMethod(String name) {
		System.out.println("执行命令");
		return "hello";
	}

	@CacheRemove(commandKey = "removeKey")
	@HystrixCommand
	public String updateMethod(String name) {
		return "update";
	}
}
