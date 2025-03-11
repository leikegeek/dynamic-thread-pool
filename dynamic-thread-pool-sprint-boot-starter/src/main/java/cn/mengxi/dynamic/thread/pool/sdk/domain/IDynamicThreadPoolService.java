package cn.mengxi.dynamic.thread.pool.sdk.domain;

import cn.mengxi.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import java.util.List;

/**
 * @author zhumang
 * @description 动态线程池服务
 * @date 2025/3/11 17:06
 */
public interface IDynamicThreadPoolService {

    List<ThreadPoolConfigEntity> queryThreadPoolList();

    ThreadPoolConfigEntity queryThreadPoolConfigByName(String name);

    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);

}
