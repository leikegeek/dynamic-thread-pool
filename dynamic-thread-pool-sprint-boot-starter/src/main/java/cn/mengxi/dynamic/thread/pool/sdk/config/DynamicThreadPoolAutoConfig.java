package cn.mengxi.dynamic.thread.pool.sdk.config;

import cn.mengxi.dynamic.thread.pool.sdk.domain.DynamicThreadPoolService;
import com.alibaba.fastjson.JSON;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author zhumang
 * @description 动态配置入口
 * @date 2025/3/11 14:06
 */
@Configuration
public class DynamicThreadPoolAutoConfig {
     private final Logger logger = LoggerFactory.getLogger(DynamicThreadPoolAutoConfig.class);

     @Bean("dynamicThreadRedissonClient")
     public RedissonClient redissonClient(DynamicThreadPoolAutoProperties properties) {
          Config config = new Config();
          // 根据需要可以设定编解码器；https://github.com/redisson/redisson/wiki/4.-%E6%95%B0%E6%8D%AE%E5%BA%8F%E5%88%97%E5%8C%96
          config.setCodec(JsonJacksonCodec.INSTANCE);

          config.useSingleServer()
                  .setAddress("redis://" + properties.getHost() + ":" + properties.getPort())
                  .setPassword(properties.getPassword())
                  .setConnectionPoolSize(properties.getPoolSize())
                  .setConnectionMinimumIdleSize(properties.getMinIdleSize())
                  .setIdleConnectionTimeout(properties.getIdleTimeout())
                  .setConnectTimeout(properties.getConnectTimeout())
                  .setRetryAttempts(properties.getRetryAttempts())
                  .setRetryInterval(properties.getRetryInterval())
                  .setPingConnectionInterval(properties.getPingInterval())
                  .setKeepAlive(properties.isKeepAlive())
          ;

          RedissonClient redissonClient = Redisson.create(config);

          logger.info("动态线程池，注册器（redis）链接初始化完成。{} {} {}", properties.getHost(), properties.getPoolSize(), !redissonClient.isShutdown());

          return redissonClient;
     }

     /**
      * @author zhumang
      * @date 2025/3/11 15:40
      * @description
      * @params
      * @return
      */
     @Bean("dynamicThreadPollService")
     public DynamicThreadPoolService dynamicThreadPollService(ApplicationContext applicationContext, Map<String, ThreadPoolExecutor> threadPoolExecutorMap){
          String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");
          if(applicationName == null){
               applicationName = "default";
               logger.warn("动态线程池启动，启动提示：springboot 应用未配置，无法获取到应用名称");
               return null;
          }
          return new DynamicThreadPoolService(threadPoolExecutorMap,applicationName);
     }
}
