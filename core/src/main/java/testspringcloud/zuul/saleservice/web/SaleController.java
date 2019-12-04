package testspringcloud.zuul.saleservice.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import testspringcloud.zuul.saleservice.feign.Book;
import testspringcloud.zuul.saleservice.feign.BookService;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@RestController
public class SaleController {

	@Autowired
	private BookService bookService;

	/**
	 * 进行图书销售
	 */
	@RequestMapping(value = "/sale-book/{bookId}", method = RequestMethod.GET)
	@ResponseBody
	public String saleBook(@PathVariable Integer bookId) {
		// 调用book服务查找
		Book book = bookService.getBook(bookId);
		// 控制台输入，模拟进行销售
		System.out.println("销售模块处理销售，要销售的图书id: " + book.getId() + ", 书名："
				+ book.getName());
		// 销售成功
		return "SUCCESS";
	}
	
	@RequestMapping(value = "/testHeader", method = RequestMethod.GET)
	@ResponseBody
	public String testHeader(HttpServletRequest request) {
		Enumeration<String> headers = request.getHeaderNames();
		while(headers.hasMoreElements()) {
			String headerName = headers.nextElement();
			System.out.println("#############" + headerName);
		}
		return "testHeader";
	}
	
	@RequestMapping(value = "/errorTest", method = RequestMethod.GET)
	@ResponseBody
	public String errorTest() throws Exception {
		Thread.sleep(3000);
		return "errorTest";
	}

}
