package testspringcloud.zuul.saleservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("zuul-book-service") // 声明调用书本服务
public interface BookService {

	/**
	 * 调用书本服务的接口，获取一个Book实例
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/book/{bookId}")
	Book getBook(@PathVariable("bookId") Integer bookId);
}
