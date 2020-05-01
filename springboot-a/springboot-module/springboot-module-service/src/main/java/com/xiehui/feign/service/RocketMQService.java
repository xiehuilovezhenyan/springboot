package com.xiehui.feign.service;

import com.xiehui.common.core.exception.KnowledgeException;

public interface RocketMQService {

	/**
	 * 立即发送主题消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	public void sendMsg(String topic, Object msg) throws KnowledgeException;

	/**
	 * 立即发送主题-tag消息
	 * 
	 * @param topic
	 * @param tag
	 * @param msg
	 * @throws CustomException
	 */
	public void sendMsg(String topic, String tag, Object msg) throws KnowledgeException;

	/**
	 * 发送即发即失消息（不关心发送结果）
	 * 
	 * @param topic
	 * @param msgg
	 * @throws CustomException
	 */
	public void sendOneWarMsg(String topic, Object msg) throws KnowledgeException;

	/**
	 * 发送顺序消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	public void sendOrderlyMsg(String topic, Object msg) throws KnowledgeException;
	
	/**
	 * 发送异步消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	public void sendSyncMsg(String topic, Object msg) throws KnowledgeException;

	/**
	 * 发送延迟消息
	 * 
	 * @param topic
	 * @param msg
	 * @throws CustomException
	 */
	public void sendDelayMsg(String topic, Object msg,Integer delayLevel) throws KnowledgeException;

}
