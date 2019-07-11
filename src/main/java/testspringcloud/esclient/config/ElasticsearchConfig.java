package testspringcloud.esclient.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: zhy
 * @Description: 搜索引擎配置类
 * @Date 2019-7-11
 */
@Configuration()
public class ElasticsearchConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchConfig.class);

    /**
     * elk集群地址
     */
    @Value("${elasticsearch.hosts}")
    private String hosts;

    /**
     * 端口
     */
    @Value("${elasticsearch.tcp-port}")
    private String tcp_port;

    /**
     * 集群名称
     */
    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    /**
     * 连接池
     */
    @Value("${elasticsearch.pool}")
    private String poolSize;

    /**
     * 配置主体(不推荐使用)
     * 建议使用RestClient
     *
     * @return
     */
    @Deprecated
    @Bean(name = "transportClient")
    public TransportClient transportClient() {
        LOGGER.info("Elasticsearch初始化开始。。。。。");
        TransportClient transportClient = null;
        try {
            // 配置信息
            Settings esSetting = Settings.builder()
                    .put("cluster.name", clusterName) //集群名字
                    .put("client.transport.sniff", true)//增加嗅探机制，找到ES集群
                    .put("thread_pool.search.size", Integer.parseInt(poolSize))//增加线程池个数，暂时设为5
                    // .put("shield.user", "admin:sysadmin")
                    .build();
            //配置信息Settings自定义
//            transportClient = new PreBuiltTransportClient(esSetting);
//            TransportAddress transportAddress = new TransportAddress(InetAddress.getByName(hostName), Integer.valueOf(port));
//            transportClient.addTransportAddresses(transportAddress); //可以增加多个
        } catch (Exception e) {
            LOGGER.error("elasticsearch TransportClient create error!!", e);
        }
        return transportClient;
    }

    /**
     * 配置主体
     * @Date 2019-07-11
     * @return
     */
    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient restHighLevelClient(){

        List<HttpHost> hostList = new ArrayList<>();
        String[] addressArray = hosts.split(",");
        Arrays.stream(addressArray).forEach(e -> {
            String[] strArray  = e.split(":");
            if(null != strArray && strArray.length == 2){
                hostList.add(new HttpHost(strArray[0], Integer.valueOf(strArray[1]),"http"));
            }
        });
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(hostList.toArray(new HttpHost[]{})));
        //resthlClient.
        return restHighLevelClient;
    }

}