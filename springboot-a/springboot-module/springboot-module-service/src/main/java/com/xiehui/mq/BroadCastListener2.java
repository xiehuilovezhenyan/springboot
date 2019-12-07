package com.xiehui.mq;

import java.util.concurrent.TimeUnit;

import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 测试广播消息
 * @author xiehui
 *
 */
@Slf4j
@RocketMQMessageListener(consumerGroup = "board-cast-group-2", topic = "broad-cast", messageModel = MessageModel.BROADCASTING)  //广播模式  consumerGroup 不能重复
@Component
public class BroadCastListener2 implements RocketMQListener<String> {

	@Override
	public void onMessage(String message) {
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
		}
		log.info("BroadCastListener2接收到消息:[{}]", message);
		
	}

}
