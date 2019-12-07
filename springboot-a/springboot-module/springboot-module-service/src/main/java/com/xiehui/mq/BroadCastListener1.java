package com.xiehui.mq;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试广播消息
 * @author xiehui
 *
 */
@Slf4j
@RocketMQMessageListener(consumerGroup = "board-cast-group-1", topic = "broad-cast", messageModel = MessageModel.BROADCASTING)
@Service
public class BroadCastListener1 implements RocketMQListener<String> {

	@Override
	public void onMessage(String message) {
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
		}
		log.info("BroadCastListener1接收到消息:[{}]", message);
		
	}

}
