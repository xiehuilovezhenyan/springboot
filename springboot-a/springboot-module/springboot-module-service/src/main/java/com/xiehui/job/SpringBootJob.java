package com.xiehui.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 测试job
 * 
 * @author xiehui
 *
 */
public class SpringBootJob implements SimpleJob {

	@Override
	public void execute(ShardingContext shardingContext) {
		System.out.println(">>>>>>>>>>>>>>>>>>>" + shardingContext + "<<<<<<<<<<<<<<<<<<<<<<");
	}

}
