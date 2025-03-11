package cn.mengxi.dynamic.thread.pool.sdk.trigger.listener;

import cn.mengxi.dynamic.thread.pool.sdk.domain.IDynamicThreadPoolService;
import cn.mengxi.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.mengxi.dynamic.thread.pool.sdk.register.IRegistry;
import com.alibaba.fastjson.JSON;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author zhumang
 * @description 动态线程池变更监听
 * @date 2025/3/11 22:52
 */
public class ThreadPoolConfigAdjustListener implements MessageListener<ThreadPoolConfigEntity> {
     private Logger logger = LoggerFactory.getLogger(ThreadPoolConfigAdjustListener.class);

     private final IDynamicThreadPoolService dynamicThreadPoolService;

     private final IRegistry registry;

     public ThreadPoolConfigAdjustListener(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
         this.dynamicThreadPoolService = dynamicThreadPoolService;
         this.registry = registry;
     }
    /**
     * @author zhumang
     * @date 2025/3/11 22:55
     * @description 监听消息上报
     * @params
     * @return
     */
    @Override
    public void onMessage(CharSequence charSequence, ThreadPoolConfigEntity threadPoolConfigEntity) {
        logger.info("动态线程池，调整线程池配置。线程池名称:{} 核心线程数:{} 最大线程数:{}", threadPoolConfigEntity.getThreadPoolName(), threadPoolConfigEntity.getPoolSize(), threadPoolConfigEntity.getMaximumPoolSize());
        dynamicThreadPoolService.updateThreadPoolConfig(threadPoolConfigEntity);
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);

        ThreadPoolConfigEntity threadPoolConfigEntityCurrent = dynamicThreadPoolService.queryThreadPoolConfigByName(threadPoolConfigEntity.getThreadPoolName());
        registry.reportThreadPoolConfigParameter(threadPoolConfigEntityCurrent);
        logger.info("动态线程池，上报线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));
    }
}
