package testspringcloud.eureka;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirstController {

	@RequestMapping(value = "/person/{personId}", method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Person findPerson(@PathVariable("personId") Integer personId, HttpServletRequest request) {
		Person person = new Person(personId, "Crazyit", 30);
		// 为了查看结果，将请求的URL设置到Person实例中
		person.setMessage(request.getRequestURL().toString());
		return person;
	}

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	@ResponseBody
	public String hello() {
		return "Hello World";
	}
}
