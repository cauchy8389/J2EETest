package testspringcloud.zuul.gateway;

import java.io.File;
import javax.annotation.PostConstruct;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import com.netflix.zuul.FilterFileManager;
import com.netflix.zuul.FilterLoader;
import com.netflix.zuul.groovy.GroovyCompiler;
import com.netflix.zuul.groovy.GroovyFileFilter;

@EnableZuulProxy
@SpringBootApplication
public class GatewayApplication {
	
	@PostConstruct
	public void zuulInit() {
        FilterLoader.getInstance().setCompiler(new GroovyCompiler());
        // 读取配置，获取脚本根目录
        String scriptRoot = System.getProperty("zuul.filter.root", "src/main/java/testspringcloud/zuul/groovy/filters");
        // 获取刷新间隔
        String refreshInterval = System.getProperty("zuul.filter.refreshInterval", "5");
        if (scriptRoot.length() > 0) scriptRoot = scriptRoot + File.separator;
        try {
            FilterFileManager.setFilenameFilter(new GroovyFileFilter());
            FilterFileManager.init(Integer.parseInt(refreshInterval), scriptRoot + "pre", 
            		scriptRoot + "route", scriptRoot + "post");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
	}

	public static void main(String[] args) {
		new SpringApplicationBuilder(GatewayApplication.class).
                properties("spring.config.location=classpath:/springcloud/zuul-gateway.yml").
                properties("server.port=8080").run(args);
	}
}
