package testspringcloud.zuul.gateway.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SourceController {

	@RequestMapping(value = "/source/hello/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String hello(@PathVariable("name") String name) {
		return "hello " + name;
	}

	@RequestMapping(value = "/book/{name}", method = RequestMethod.GET)
	@ResponseBody
	public String testBook(@PathVariable("name") String name) {
		return "book " + name;
	}

	@RequestMapping(value = "/sale/noRoute", method = RequestMethod.GET)
	@ResponseBody
	public String saleNoRoute(HttpServletRequest request) {
		System.out.println("设置了ignoredPatterns，该方法将会执行");
		return "/sale/noRoute";
	}

}
