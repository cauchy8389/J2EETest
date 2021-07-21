package nacos.test.controller;


import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@RequestMapping("config")
@RefreshScope
public class ConfigController {

//    @NacosInjected
//    private ConfigService configService;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Value(value = "${test.user.name: dodo}")
    private String myJack;

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public String get() {
        return myJack;
    }

    /**
     * curl -X POST "http://localhost:8799/config?dataId=spring-nacos-test&content=user.name=jamesgangluan1"
     */
    @RequestMapping(method = POST)
    @ResponseBody
    public ResponseEntity<String> publish(@RequestParam String dataId,
                                          @RequestParam(defaultValue = "DEFAULT_GROUP") String group,
                                          @RequestParam String content) throws NacosException {
        boolean result = nacosConfigManager.getConfigService().publishConfig(dataId, group, content);
        if (result) {
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
