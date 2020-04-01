package com.xiehui.job;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.simple.SimpleJobConfiguration;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;

/**
 * Elastic job
 * 
 * @author xiehui
 *
 */
@Configuration
public class SimpleJobConfig {

	@Resource
	private ZookeeperRegistryCenter regCenter;

	@Bean(initMethod = "init")
	public ZookeeperRegistryCenter regCenter() {
		ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration("192.168.1.224:2181",
				"elastic-job-lite-springboot");
		zookeeperConfiguration.setBaseSleepTimeMilliseconds(1000);
		zookeeperConfiguration.setMaxSleepTimeMilliseconds(3000);
		zookeeperConfiguration.setMaxRetries(3);
		return new ZookeeperRegistryCenter(zookeeperConfiguration);
	}

	@Bean
	public SpringBootJob simpleJob() {
		return new SpringBootJob();
	}

	@Bean(initMethod = "init")
	public JobScheduler simpleJobScheduler(final SpringBootJob simpleJob) {
		return new SpringJobScheduler(simpleJob, regCenter,
				getLiteJobConfiguration(simpleJob.getClass(), "0 0 1 * * ?", 1, "0=A"));
	}

	private LiteJobConfiguration getLiteJobConfiguration(final Class<? extends SimpleJob> jobClass, final String cron,
			final int shardingTotalCount, final String shardingItemParameters) {
		return LiteJobConfiguration.newBuilder(new SimpleJobConfiguration(JobCoreConfiguration
				.newBuilder(jobClass.getName(), cron, shardingTotalCount).shardingItemParameters(shardingItemParameters)
				.jobParameter(UUID.randomUUID().toString()).build(), jobClass.getCanonicalName())).overwrite(true)
				.build();
	}

}
