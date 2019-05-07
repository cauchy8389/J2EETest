package testspringcloud.zuul.bookservice.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

	@RequestMapping(value = "/book/{bookId}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Book findBook(@PathVariable Integer bookId) {
		Book book = new Book();
		book.setId(bookId);
		book.setName("计算数学高手");
		book.setAuthor("张老板");
		return book;
	}
}
