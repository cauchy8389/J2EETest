package testspringcloud.data.mybatis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import testspringcloud.data.mybatis.dao.write.CifCodeMapper;
import testspringcloud.data.mybatis.entity.CifCode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@Transactional(rollbackFor = Exception.class)
public class CifCodeService {

    @Autowired
    private CifCodeMapper cifCodeMapper;

    public CifCode testSelect(int serialNo){
        CifCode cifCode = cifCodeMapper.selectByPrimaryKey(serialNo);
        return cifCode;
    }

    @Autowired
    @Qualifier("stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Autowired
    //private RedisTemplate<String, Object> redisTemplate;

    public void DoJedis(){

        //JedisCluster jc = new JedisCluster();
        //redisTemplate.opsForValue().get()

        // 登录本地的Redis
        Jedis jedis = new Jedis("zhy.cauchy8389.com");
        //jedis.auth("123456");
        // 将数据保存为hash类型

        if (!jedis.exists("person_test")) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("name", "Angus");
            data.put("age", "33");
            data.put("company", "crazyit");
            jedis.hmset("person_test", data);
        }
        // 查询hash类型的数据
        List<String> dbDatas = jedis.hmget("person_test", "name", "age");
        for(String dbData : dbDatas) {
            System.out.println(dbData);
        }
        /* =========分隔线==========  */
        // 将数据保存为set类型
        if (!jedis.exists("person_test_ids")) {
            jedis.sadd("person_test_ids", "1", "2");
        }
        // 查询数据
        Set<String> dbDatasSet = jedis.smembers("person_test_ids");
        for(String dbData : dbDatasSet) {
            System.out.println(dbData);
        }

    }
}
