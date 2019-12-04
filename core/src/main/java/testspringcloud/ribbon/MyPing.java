package testspringcloud.ribbon;

import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.Server;

public class MyPing implements IPing {

	public boolean isAlive(Server server) {
		System.out.println("自定义Ping类，服务器信息：" + server.getHostPort());
		return true;
	}
}
