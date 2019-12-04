package testspringcloud.ribbon.config;

import org.springframework.cloud.netflix.ribbon.RibbonClient;

@RibbonClient(name="first-service-provider", configuration=MyConfig.class)
public class CloudProviderConfig {

}
