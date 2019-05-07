package testspringcloud.zuul.gateway.web;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class MyErrorController extends BasicErrorController {

	public MyErrorController(ErrorAttributes errorAttributes) {
		super(errorAttributes, new ErrorProperties());
	}

	@Override
	public ModelAndView errorHtml(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("===  输出异常信息   ===");
		System.out.println(request.getAttribute("javax.servlet.error.status_code"));
		System.out.println(request.getAttribute("javax.servlet.error.exception"));
		System.out.println(request.getAttribute("javax.servlet.error.message"));		
		return super.errorHtml(request, response);
	}
}
