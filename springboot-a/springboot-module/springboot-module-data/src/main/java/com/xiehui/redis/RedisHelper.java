package com.xiehui.redis;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class RedisHelper {

	@Autowired
	RedisTemplate<String, Object> redisTemplate;

	private static final Long DEFAULT_INCREMENT = 1L;

	public Long nextId(String KEY_PATTERN) {
		return redisTemplate.opsForValue().increment(KEY_PATTERN, DEFAULT_INCREMENT);
	}

	public Long nextId(String KEY_PATTERN, long id) {
		return redisTemplate.opsForValue().increment(KEY_PATTERN, DEFAULT_INCREMENT);
	}

	/**
	 * 获取最新的指定序列
	 *
	 * @param KEY_PATTERN
	 * @param MIN_INCREMENT
	 * @param MAX_INCREMENT
	 * @return
	 */
	public Long nextId(String KEY_PATTERN, Integer MIN_INCREMENT, Integer MAX_INCREMENT) {
		Random increment = new Random();
		return redisTemplate.opsForValue().increment(KEY_PATTERN,
				increment.ints(MIN_INCREMENT, (MAX_INCREMENT + 1)).findFirst().getAsInt());
	}

	/**
	 * 检查是否存在指定Key
	 *
	 * @param KEY_PATTERN
	 * @param key_value
	 * @return
	 */
	public boolean existsKey(String KEY_PATTERN, Object... key_value) {
		return redisTemplate.hasKey(String.format(KEY_PATTERN, key_value));
	}

	/**
	 * 重名名key，如果newKey已经存在，则newKey的原值被覆盖
	 *
	 * @param oldKey
	 * @param newKey
	 */
	public void renameKey(String oldKey, String newKey) {
		redisTemplate.rename(oldKey, newKey);
	}

	/**
	 * newKey不存在时才重命名
	 *
	 * @param oldKey
	 * @param newKey
	 * @return 修改成功返回true
	 */
	public boolean renameKeyNotExist(String oldKey, String newKey) {
		return redisTemplate.renameIfAbsent(oldKey, newKey);
	}

	/**
	 * 删除key
	 *
	 * @param KEY_PATTERN
	 */
	public void deleteKey(String KEY_PATTERN, String key_value) {
		redisTemplate.delete(String.format(KEY_PATTERN, key_value));
	}

	/**
	 * 删除key
	 *
	 * @param KEY_PATTERN
	 */
	public void deleteKey(String KEY_PATTERN) {
		redisTemplate.delete(KEY_PATTERN);
	}

	/**
	 * 删除多个key
	 *
	 * @param keys
	 */
	public void deleteKey(String... keys) {
		Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
		redisTemplate.delete(kSet);
	}

	/**
	 * 删除Key的集合
	 *
	 * @param keys
	 */
	public void deleteKey(Collection<String> keys) {
		Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
		redisTemplate.delete(kSet);
	}

	/**
	 * 设置key的生命周期
	 *
	 * @param key
	 * @param time
	 * @param timeUnit
	 */
	public void expireKey(String key, long time, TimeUnit timeUnit) {
		redisTemplate.expire(key, time, timeUnit);
	}

	/**
	 * 指定key在指定的日期过期
	 *
	 * @param key
	 * @param date
	 */
	public void expireKeyAt(String key, Date date) {
		redisTemplate.expireAt(key, date);
	}

	/**
	 * 查询key的生命周期
	 *
	 * @param key
	 * @param timeUnit
	 * @return
	 */
	public long getKeyExpire(String key, TimeUnit timeUnit) {
		return redisTemplate.getExpire(key, timeUnit);
	}

	/**
	 * 将key设置为永久有效
	 *
	 * @param key
	 */
	public void persistKey(String key) {
		redisTemplate.persist(key);
	}

	/**
	 * 存入一个对象
	 *
	 * @param KEY_PATTERN
	 * @param KEY_VALUE
	 * @param objectClass
	 * @return
	 */
	public void saveObject(String KEY_PATTERN, Object KEY_VALUE, Object objectClass) {
		redisTemplate.opsForValue().set(String.format(KEY_PATTERN, KEY_VALUE), objectClass);
	}

	/**
	 * 存入一个对象
	 * 
	 * @param KEY_PATTERN
	 * @param objectClass
	 * @return
	 */
	public void saveObject(String KEY_PATTERN, Object objectClass, Long expireTime, TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(KEY_PATTERN, objectClass, expireTime, timeUnit);
	}

	/**
	 * 存入一个对象
	 *
	 * @param KEY_PATTERN
	 * @param objectClass
	 * @return
	 */
	public void saveObject(String KEY_PATTERN, Object objectClass) {
		redisTemplate.opsForValue().set(KEY_PATTERN, objectClass);
	}

	/**
	 * 存入一个对象
	 *
	 * @param KEY_PATTERN
	 * @param KEY_VALUE
	 * @param objectClass
	 * @return
	 */
	public void saveObject(String KEY_PATTERN, Object KEY_VALUE, Object objectClass, Long expireTime,
			TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(String.format(KEY_PATTERN, KEY_VALUE), objectClass, expireTime, timeUnit);
	}

	public <T> T getObject(String KEY_PATTERN, Object KEY_VALUE) {

		return (T) redisTemplate.opsForValue().get(String.format(KEY_PATTERN, KEY_VALUE));
	}

	public <T> T getObject(String KEY_PATTERN, Object KEY_VALUE, Class<T> clazz) {
		Object valueObj = redisTemplate.opsForValue().get(String.format(KEY_PATTERN, KEY_VALUE));
		if (clazz.isInstance(valueObj)) {
			return (T) valueObj;
		} else if (clazz == Long.class && valueObj instanceof Integer) {
			Integer obj = (Integer) valueObj;
			return (T) Long.valueOf(obj.longValue());
		}
		return null;
	}

	public <T> T getObject(String KEY_PATTERN) {
		return (T) redisTemplate.opsForValue().get(KEY_PATTERN);
	}

	/**
	 * 存入一个Hash值对象
	 *
	 * @param KEY_PATTERN
	 * @param KEY_VALUE
	 * @param objectClass
	 * @return
	 */
	public void saveObjectHash(String KEY_PATTERN, Object KEY_VALUE, Object objectClass) {
		JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(objectClass));
		Map<String, Object> map = new HashMap<>();
		for (String key : jsonObject.keySet()) {
			map.put(key, jsonObject.get(key));
		}
		redisTemplate.opsForHash().putAll(String.format(KEY_PATTERN, KEY_VALUE), map);
	}

	public <T> T getObjectHash(String KEY_PATTERN, Object KEY_VALUE, Class<T> clazz) {
		String key = String.format(KEY_PATTERN, KEY_VALUE);
		HashOperations<String, String, Object> operations = redisTemplate.opsForHash();
		Map<String, Object> mapInRedis = operations.entries(key);

		if (Objects.isNull(mapInRedis)) {
			return null;
		}
		JSONObject jsonObject = new JSONObject();
		for (String mapKey : mapInRedis.keySet()) {
			jsonObject.put(mapKey, mapInRedis.get(mapKey));
		}

		return JSON.parseObject(jsonObject.toJSONString(), clazz);
	}

}
