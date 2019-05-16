package testspringcloud.trace.saleservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@SpringBootApplication
@EnableEurekaClient
@RestController
@EnableFeignClients
public class SaleApplication {
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	private PayService payService;
	
	@RequestMapping(method = RequestMethod.GET, value = "/sale/{bookId}")
	public String sale(@PathVariable("bookId") Integer bookId) {
		System.out.println("销售模块处理销售");
		// 查找书本
		BookService.Book book = bookService.getBook(bookId);
		// 进行支付
		payService.doPay(new BigDecimal(10));
		return "销售成功，书名: " + book.getName() + ", 作者：" + book.getAuthor();
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/hello")
	public String hell() {
		return "Hello";
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(SaleApplication.class)
				.properties("spring.config.location=classpath:/springcloud/trace-sale-service.yml").run(args);
	}
}
