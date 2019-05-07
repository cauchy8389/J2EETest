package testspringcloud.zuul.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.web.client.RestTemplate;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class RestTemplateFilter extends ZuulFilter {
	
	private RestTemplate restTemplate;
	
	public RestTemplateFilter(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		// 获取请求uri
		String uri = request.getRequestURI();
		// 为了不影响其他路由，uri中含有 rest-tpl-sale 才执行本路由器
		if(uri.indexOf("rest-tpl-sale") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		// 获取需要调用的服务id
		String serviceId = (String)ctx.get("serviceId");
		// 获取请求的uri
		String uri = (String)ctx.get("requestURI");
		// 组合成url给RestTemplate调用
		String url = "http://" + serviceId + uri;
		System.out.println("执行RestTemplateFilter， 调用的url：" + url);
		// 调用并获取结果
		String result = this.restTemplate.getForObject(url, String.class);
		// 设置路由状态，表示已经进行路由
		ctx.setResponseBody(result);
		// 设置响应标识
		ctx.sendZuulResponse();
		return null;
	}

	@Override
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 2;
	}
}
