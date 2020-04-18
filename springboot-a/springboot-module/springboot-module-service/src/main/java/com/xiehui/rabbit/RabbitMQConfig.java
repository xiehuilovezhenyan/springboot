package com.xiehui.rabbit;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * rabbitMQ配置
 * 
 * @author xiehui
 *
 */
//@Component
public class RabbitMQConfig {

	/**
	 * @Description 定义支付交换器
	 * @Date 2019-04-02 14:39:31
	 */
	@Bean
	private DirectExchange directPayExchange() {
		return new DirectExchange("direct.pay.exchange");
	}

	/**
	 * @Description 定义支付队列 绑定死信队列(其实是绑定的交换器，然后通过交换器路由键绑定队列) 设置过期时间
	 * @Date 2019-04-02 14:40:24
	 */
	@Bean
	private Queue directPayQueue() {
		Map<String, Object> args = new HashMap<>(3);
		// 声明死信交换器
		args.put("x-dead-letter-exchange", "direct.delay.exchange");
		// 声明死信路由键
		args.put("x-dead-letter-routing-key", "DelayKey");
		// 声明队列消息过期时间
		args.put("x-message-ttl", 24 * 24 * 60 * 60 * 1000);
		return new Queue("direct.pay.queue", true, false, false, args);
	}

	/**
	 * @Description 定义支付绑定
	 * @Date 2019-04-02 14:46:10
	 */
	@Bean
	private Binding bindingOrderDirect() {
		return BindingBuilder.bind(directPayQueue()).to(directPayExchange()).with("OrderPay");
	}
	
	
	
	
	@Bean
	private DirectExchange directMsgExchange() {
		return new DirectExchange("direct.msg.exchange");
	}

	/**
	 * @Description 定义支付队列 绑定死信队列(其实是绑定的交换器，然后通过交换器路由键绑定队列) 设置过期时间
	 * @Date 2019-04-02 14:40:24
	 */
	@Bean
	private Queue directMsgQueue() {
		Map<String, Object> args = new HashMap<>(3);
		// 声明死信交换器
		args.put("x-dead-letter-exchange", "direct.msgDelay.exchange");
		// 声明死信路由键
		args.put("x-dead-letter-routing-key", "MsgKey");
		// 声明队列消息过期时间
		args.put("x-message-ttl", 24 * 24 * 60 * 60 * 1000);
		return new Queue("direct.msg.queue", true, false, false, args);
	}

	/**
	 * @Description 定义支付绑定
	 * @Date 2019-04-02 14:46:10
	 */
	@Bean
	private Binding bindingMsgDirect() {
		return BindingBuilder.bind(directMsgQueue()).to(directMsgExchange()).with("pushMsg");
	}

}
