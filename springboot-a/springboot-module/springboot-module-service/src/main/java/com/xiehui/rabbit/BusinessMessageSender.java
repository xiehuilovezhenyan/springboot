package com.xiehui.rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BusinessMessageSender {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void sendMsg(String msg) {
		rabbitTemplate.convertSendAndReceive(RabbitMQConfig.BUSINESS_EXCHANGE_NAME, "", msg);
	}

}
