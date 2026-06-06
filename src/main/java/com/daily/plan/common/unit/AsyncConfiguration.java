package com.daily.plan.common.unit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfiguration {

    @Bean(name = "asyncTaskExecutor")
    public ThreadPoolTaskExecutor asyncTaskExecutor() {

        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();

        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setQueueCapacity(25);
        pool.setThreadNamePrefix("AsyncTask-");
        pool.initialize();

        return pool;

    }
}
