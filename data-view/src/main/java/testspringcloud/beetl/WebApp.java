package testspringcloud.beetl;

import com.google.gson.Gson;
import com.zhy.test.entity.CifCode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(excludeName={"com.github.trang.druid.autoconfigure.DruidAutoConfiguration",
		"com.github.trang.druid.autoconfigure.DruidDataSourceInitializerAutoConfiguration",
		"org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
		"org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"})
@Controller
public class WebApp {
	public static ConfigurableApplicationContext BootContext= null;

	public static void main(String[] args) {
		BootContext = new SpringApplicationBuilder(WebApp.class)
				.properties("spring.config.location=classpath:/springcloud/view-beetl.yml").run(args);
	}

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest req) {
		ModelAndView view = new ModelAndView("myfirst");
		//total 是模板的全局变量，可以直接访问
		view.addObject("total","mytest1");


		CifCode p1 = new CifCode();
		p1.setCODECNAME("zhy");
		p1.setCODECODE("10");
		p1.setREMARK("haha");

		Gson gson = (Gson)WebApplicationContextUtils.getWebApplicationContext(req.getServletContext()).getBean("theGson");
		Gson gson2 = (Gson)BootContext.getBean("theGson");
		view.addObject("gsonyes", gson == gson2);


		String p1Str = gson.toJson(p1);
		view.addObject("p1",p1Str);
		view.addObject("person",p1);

		List<Integer> arrInt = new ArrayList<Integer>();
		arrInt.add(123);
		arrInt.add(456);
		arrInt.add(789);
		view.addObject("arr",arrInt);

		return view;
	}

	@Bean(name="theGson")
	public Gson getGson(){
		return new Gson();
	}
}
