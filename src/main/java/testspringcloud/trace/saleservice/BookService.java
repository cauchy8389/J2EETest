package testspringcloud.trace.saleservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("book-service")
public interface BookService {
	
	@RequestMapping(method = RequestMethod.GET, value = "/book/{id}")
	Book getBook(@PathVariable("id") Integer id);

	static class Book {
		Integer id;
		String name;
		String author;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
	}
}
