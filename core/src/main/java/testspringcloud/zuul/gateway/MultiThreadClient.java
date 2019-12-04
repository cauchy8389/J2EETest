package testspringcloud.zuul.gateway;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.discovery.PatternServiceRouteMapper;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

public class MultiThreadClient {

	public static void main(String[] args) throws Exception {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger logger = loggerContext.getLogger("root");
		logger.setLevel(Level.toLevel("INFO"));
		// 创建默认的HttpClient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 调用6次服务并输出结果
		for(int i = 0; i < 1; i++) {
			// 调用 GET 方法请求服务
			HttpGet httpget = new HttpGet("http://localhost:8080/routes");
			// 获取响应
			HttpResponse response = httpclient.execute(httpget);
			// 根据 响应解析出字符串
			System.out.println(EntityUtils.toString(response.getEntity()));
		}
		
		
//		String SERVICE_PATTERN = "(?<book>^\\w+)(-(?<name>\\w+)-|-)";
//		String ROUTE_PATTERN = "${book}/${name}";
//		PatternServiceRouteMapper p = new PatternServiceRouteMapper(
//				"(zuul)-(?<module>.+)-(service)", "${module}/**");
//		String s = p.apply("zuul-abcxyz-service");
//		System.out.println(s);
	}
}
