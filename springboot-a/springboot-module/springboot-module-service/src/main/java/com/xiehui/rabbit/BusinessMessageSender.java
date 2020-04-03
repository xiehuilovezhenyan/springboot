package com.xiehui.rabbit;

import java.util.UUID;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessMessageSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMsg(String msg) {

		// 测试死信队列一
		for (int i = 0; i < 10000; i++) {
			int number = RandomUtils.nextInt(0, 30);
			CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
			rabbitTemplate.convertAndSend("direct.pay.exchange", "OrderPay", UUID.randomUUID().toString(), message -> {
				// 设置5秒过期
				message.getMessageProperties().setExpiration(number * 1000 + "");
				return message;
			}, correlationData);
		}

		// 测试私信队列二
		for (int i = 0; i < 100000; i++) {
			int number = RandomUtils.nextInt(0, 30);
			CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
			rabbitTemplate.convertAndSend("direct.msg.exchange", "pushMsg", UUID.randomUUID().toString(), message -> {
				// 设置5秒过期
				message.getMessageProperties().setExpiration(number * 1000 + "");
				return message;
			}, correlationData);
		}

	}

}
