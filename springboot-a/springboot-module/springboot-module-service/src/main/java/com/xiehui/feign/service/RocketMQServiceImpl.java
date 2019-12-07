package com.xiehui.feign.service;

import javax.annotation.Resource;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.xiehui.common.core.exception.CustomException;
import com.xiehui.plugin.snowflake.IdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RocketMQServiceImpl implements RocketMQService {

	@Resource
	private RocketMQTemplate rocketMQTemplate;
	@Autowired
	private IdGenerator idGenerator;

	/** 超时时间 */
	private static final Integer TIMEOUT = 2000;

	/**
	 * 立即发送主题消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	@Override
	public void sendMsg(String topic, Object msg) throws CustomException {
		try {
			rocketMQTemplate.convertAndSend(topic, msg);
			log.info(String.format("发送MQ标准消息,主题:%s,内容:%s", topic, JSON.toJSONString(msg)));
		} catch (Exception e) {
			log.info("发送消息失败", e);
		}
	}

	/**
	 * 立即发送主题-tag消息
	 * 
	 * @param topic
	 * @param tag
	 * @param msg
	 * @throws CustomException
	 */
	@Override
	public void sendMsg(String topic, String tag, Object msg) throws CustomException {
		try {
			String key = String.format("%s:%s", topic, tag);
			rocketMQTemplate.convertAndSend(key, msg);
			log.info(String.format("发送MQ标准消息,主题:%s,内容:%s", topic, JSON.toJSONString(msg)));
		} catch (Exception e) {
			log.info("发送消息失败", e);
		}
	}

	/**
	 * 发送即发即失消息（不关心发送结果）
	 * 
	 * @param topic
	 * @param msgg
	 * @throws CustomException
	 */
	@Override
	public void sendOneWarMsg(String topic, Object msg) throws CustomException {
		try {
			rocketMQTemplate.sendOneWay(topic, MessageBuilder.withPayload(msg).build());
			log.info(String.format("发送MQ即发即失消息,主题:%s,内容:%s", topic, JSON.toJSONString(msg)));
		} catch (Exception e) {
			log.info("发送消息失败", e);
		}
	}

	/**
	 * 发送顺序消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	@Override
	public void sendOrderlyMsg(String topic, Object msg) throws CustomException {
		try {
			rocketMQTemplate.syncSendOrderly(topic, msg, idGenerator.nextId() + "", TIMEOUT);
			log.info(String.format("发送MQ顺序消息,主题:%s,内容:%s", topic, JSON.toJSONString(msg)));
		} catch (Exception e) {
			log.info("发送消息失败", e);
		}
	}

	/**
	 * 发送异步消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	@Override
	public void sendSyncMsg(String topic, Object msg) throws CustomException {

		rocketMQTemplate.asyncSend(topic, MessageBuilder.withPayload(msg).build(), new SendCallback() {

			@Override
			public void onSuccess(SendResult sendResult) {
				log.info(String.format("异步发送成功:消息主体:%s,消息内容:%s,消息结果:%s", topic, JSON.toJSONString(msg),
						JSON.toJSONString(sendResult)));
			}

			@Override
			public void onException(Throwable e) {
				log.info(String.format("异步发送失败:主题:%s,内容:%s", topic, JSON.toJSONString(msg)));
			}
		});
	}

	/**
	 * 发送延迟消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	@Override
	public void sendDelayMsg(String topic, Object msg, Integer delayLevel) throws CustomException {
		try {
			rocketMQTemplate.syncSend(topic, MessageBuilder.withPayload(msg).build(), TIMEOUT, delayLevel);
			log.info(String.format("发送MQ异步消息,主题:%s,内容:%s", topic, JSON.toJSONString(msg)));
		} catch (Exception e) {
			log.info("发送消息失败", e);
		}
	}

}
