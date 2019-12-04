package testspringcloud.zuul.gateway.filter;

import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;

public class MyFilter extends ZuulFilter {

	// 过滤器执行条件
	public boolean shouldFilter() {
		return true;
	}

	// 执行方法
	public Object run() {
		System.out.println("执行 MyFilter 过滤器");
		return null;
	}

	// 表示将在路由阶段执行
	public String filterType() {
		return FilterConstants.ROUTE_TYPE;
	}

	// 返回1，路由阶段，该过滤将会最先执行
	public int filterOrder() {
		return 1;
	}
}
