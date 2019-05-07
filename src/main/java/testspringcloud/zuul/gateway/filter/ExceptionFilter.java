package testspringcloud.zuul.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class ExceptionFilter extends ZuulFilter {

	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();		
		HttpServletRequest request = ctx.getRequest();
		// 获取请求的uri
		String uri = request.getRequestURI();
		// 为不影响其他例子，uri含有exceptionTest才执行本过滤器
		if(uri.indexOf("exceptionTest") != -1) {
			return true;
		}
		return false;
	}

	public Object run() {
		System.out.println("执行  ExceptionFilter，将抛出异常");
		throw new ZuulRuntimeException(new ZuulException("exception msg", 201, "my cause"));
	}

	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	public int filterOrder() {
		return 3;
	}
}
