package testspringcloud.feign.contract;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 客户端接口
 * @author 张海云
 *
 */
@FeignClient(name = "first-service-provider")
public interface HelloClient {
	
	@MyUrl(method = "GET", url = "/hello")
	String myHello();
	
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	String springHello();
}
