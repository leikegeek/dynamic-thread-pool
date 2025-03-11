package cn.mengxi.dynamic.thread.pool.sdk.register;

import cn.mengxi.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * @author zhumang
 * @description  注册中心接口
 * @date 2025/3/11 18:31
 */
public interface IRegistry {
    /**
     * @author zhumang
     * @date 2025/3/11 18:40
     * @description 上报线程池接口
     * @params
     * @return
     */
    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities);
    /**
     * @author zhumang
     * @date 2025/3/11 18:41
     * @description 上报线程配置接口
     * @params
     * @return
     */
    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);

}
