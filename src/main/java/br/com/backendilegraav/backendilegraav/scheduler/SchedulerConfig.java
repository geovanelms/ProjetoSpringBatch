package br.com.backendilegraav.backendilegraav.scheduler;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableAsync
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskExecutor());
	}

	/**
	 * @return executor for the scheduler (naming convention)
	 */
	@Bean(name = "taskScheduler")
	public Executor taskExecutor() {
		ThreadPoolTaskScheduler configuracao = new ThreadPoolTaskScheduler();
		configuracao.setPoolSize(5);
		configuracao.setThreadNamePrefix("myTaskScheduler-");
		configuracao.setWaitForTasksToCompleteOnShutdown(true);
		return configuracao;
	}
}
