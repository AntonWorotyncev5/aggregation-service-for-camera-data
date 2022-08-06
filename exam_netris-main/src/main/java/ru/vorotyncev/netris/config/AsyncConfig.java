package ru.vorotyncev.netris.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync // активировать асинхронность
public class AsyncConfig implements AsyncConfigurer {

  @Value("${ru.vorotyncev.poolSize}")
  private int pollSize;

  @Value("${ru.vorotyncev.maxPoolSize}")
  private int maxPoolSize;

  @Value("${ru.vorotyncev.queueCapacity}")
  private int queueCapacity;

  @Bean
  @Override
  public Executor getAsyncExecutor() {
    ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
    threadPoolTaskExecutor.setThreadNamePrefix("AsyncHandler-");
    threadPoolTaskExecutor.setCorePoolSize(pollSize);
    threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
    threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
    threadPoolTaskExecutor.afterPropertiesSet();
    return threadPoolTaskExecutor;
  }

}
