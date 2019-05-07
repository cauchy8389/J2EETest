package testspringcloud.zuul.gateway.hy;

import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 回退的处理类
 * @author 杨恩雄
 *
 */
public class MyFallbackProvider implements FallbackProvider {

	// 返回路由的名称
	public String getRoute() {
		return "*";
	}

	// 回退触发时，返回默认的响应
	public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
		System.out.println(route);

		return new ClientHttpResponse() {

			public InputStream getBody() throws IOException {
				return new ByteArrayInputStream("fallback".getBytes());
			}

			public HttpHeaders getHeaders() {
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.TEXT_PLAIN);
				return headers;
			}

			public HttpStatus getStatusCode() throws IOException {
				return HttpStatus.OK;
			}

			public int getRawStatusCode() throws IOException {
				return 200;
			}

			public String getStatusText() throws IOException {
				return "OK";
			}

			public void close() {
				
			}
		};
	}
}
