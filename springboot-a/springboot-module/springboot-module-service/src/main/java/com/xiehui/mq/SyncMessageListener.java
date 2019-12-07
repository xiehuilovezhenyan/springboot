package com.xiehui.mq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试顺序消息
 * 
 * @author xiehui
 *
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "sync-message-group-1", topic = "sync-message-1")
public class SyncMessageListener implements RocketMQListener<String> {

	@Override
	public void onMessage(String message) {
		log.info("SyncMessageListener:" + message);

	}

}
