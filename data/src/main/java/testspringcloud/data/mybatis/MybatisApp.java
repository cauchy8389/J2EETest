package testspringcloud.data.mybatis;

import org.nutz.json.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import testspringcloud.data.mybatis.entity.CifCode;
import testspringcloud.data.mybatis.service.CifCodeService;

import javax.servlet.http.HttpServletRequest;

//xkcoding 借鉴
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@RestController
public class MybatisApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(MybatisApp.class).
				properties("spring.config.location=classpath:/springcloud/data-mybatis.yml").run(args);
	}

	@Autowired
	public CifCodeService cifcodeService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public String index() {
		CifCode cifCode = cifcodeService.testSelect(1);
		StringBuilder sb = new StringBuilder();
		sb.append("cifCodeType:" + cifCode.getTYPENAME());
		sb.append("  cifCode" + Json.toJson(cifCode));

		return sb.toString();
	}

	@RequestMapping(value = "/index2", method = RequestMethod.GET)
	@ResponseBody
	public String index2() {
		cifcodeService.DoJedis();

		return "SUCCESS!";
	}
}
