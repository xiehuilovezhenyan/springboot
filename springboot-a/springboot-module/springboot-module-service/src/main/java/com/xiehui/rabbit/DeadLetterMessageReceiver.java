package com.xiehui.rabbit;

import java.io.IOException;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeadLetterMessageReceiver {

	@RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUEA_NAME)
	public void receiveA(Message message, Channel channel) throws IOException {
		System.out.println("收到死信消息A：" + new String(message.getBody()));
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	@RabbitListener(queues = RabbitMQConfig.DEAD_LETTER_QUEUEB_NAME)
	public void receiveB(Message message, Channel channel) throws IOException {
		System.out.println("收到死信消息B：" + new String(message.getBody()));
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

}
