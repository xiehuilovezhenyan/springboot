package com.xiehui.feign.api;

import com.xiehui.common.core.exception.KnowledgeException;

public interface BusinessService {

	public void queryAllCourse() throws KnowledgeException;
	
	public void testIdGenerator();
	
	public void testMQ();

	public void testRedisssionSync();
}
