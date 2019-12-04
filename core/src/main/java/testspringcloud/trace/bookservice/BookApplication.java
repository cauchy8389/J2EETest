package testspringcloud.trace.bookservice;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class BookApplication {
	
	@RequestMapping(method = RequestMethod.GET, value = "/book/{id}", 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Book getBook(@PathVariable("id") Integer id) {
		System.out.println("书本模块查询，书本id：" + id);
		Book book = new Book();
		book.setId(id);
		book.setName("spark高手");
		book.setAuthor("老板");
		return book;
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(BookApplication.class)
				.properties("spring.config.location=classpath:/springcloud/trace-book-service.yml").run(args);
	}
}
