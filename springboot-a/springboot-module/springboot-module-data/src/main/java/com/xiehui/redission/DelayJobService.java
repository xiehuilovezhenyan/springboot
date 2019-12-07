package com.xiehui.redission;

import java.util.concurrent.TimeUnit;

public interface DelayJobService {
	
	/**
	 * 提交延迟任务
	 * @param queueName
	 * @param msg
	 * @param delay
	 * @param timeUnit
	 */
	public void submitJob(String queueName,Object msg,Long delay,TimeUnit timeUnit);

}
