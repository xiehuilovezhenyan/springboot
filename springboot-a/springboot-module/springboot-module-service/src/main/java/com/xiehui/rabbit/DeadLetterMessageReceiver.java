package com.xiehui.rabbit;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import org.springframework.amqp.rabbit.annotation.*;

import com.rabbitmq.client.Channel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DeadLetterMessageReceiver {

	/**
	 * @Description 延迟队列
	 * @Date 2019-04-04 16:34:28
	 */
	// 监听私信消息一
	@RabbitListener(bindings = {
			@QueueBinding(value = @Queue(value = "direct.delay.queue"), exchange = @Exchange(value = "direct.delay.exchange"), key = {
					"DelayKey" }) })
	public void getDLMessage(Message message, Channel channel) throws InterruptedException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 模拟执行任务
		log.info("这是延迟队列一消费：" + new String(message.getBody()) + "：" + sdf.format(new Date()));
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

	// 监听私信消息二
	@RabbitListener(bindings = {
			@QueueBinding(value = @Queue(value = "direct.push.queue"), exchange = @Exchange(value = "direct.msgDelay.exchange"), key = {
					"MsgKey" }) })
	public void getDLMessage2(Message message, Channel channel) throws InterruptedException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 模拟执行任务
		log.info("这是延迟队列二消费：" + new String(message.getBody()) + "：" + sdf.format(new Date()));
		channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
	}

}
