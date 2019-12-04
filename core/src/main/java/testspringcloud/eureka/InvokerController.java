package testspringcloud.eureka;

import com.netflix.appinfo.InstanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@Configuration
public class InvokerController {

	@Bean
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/router", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String router() {
		RestTemplate restTpl = getRestTemplate();
		// 根据应用名称调用服务
		String json = restTpl.getForObject(
				"http://first-service-provider/person/1", String.class);
		return json;
	}

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/router2", method = RequestMethod.GET)
    @ResponseBody
    public String router2() {
        // 查找服务列表
        List<ServiceInstance> ins = getServiceInstances();
        // 输出服务信息及状态
        for (ServiceInstance service : ins) {
            EurekaDiscoveryClient.EurekaServiceInstance esi = (EurekaDiscoveryClient.EurekaServiceInstance) service;
            InstanceInfo info = esi.getInstanceInfo();
            System.out.println(info.getAppName() + "---" + info.getInstanceId()
                    + "---" + info.getStatus());
        }
        return "";
    }

    /**
     * 查询可用服务
     */
    private List<ServiceInstance> getServiceInstances() {
        List<String> ids = discoveryClient.getServices();
        List<ServiceInstance> result = new ArrayList<ServiceInstance>();
        for (String id : ids) {
            List<ServiceInstance> ins = discoveryClient.getInstances(id);
            result.addAll(ins);
        }
        return result;
    }

}
