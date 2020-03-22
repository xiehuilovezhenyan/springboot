package com.xiehui.shard.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.util.Assert;

import com.xiehui.plugin.shard.ShardTableStrategy;
import com.xiehui.plugin.snowflake.IdGenerator;

/**
 * 雪花分表策略类
 *
 * @author xiehui
 */
public class SnowflakeShardTableStrategy implements ShardTableStrategy {

	/**
	 * ID生成器
	 */
	private IdGenerator idGenerator;
	/**
	 * 日期格式
	 */
	private String dateFormat;

	// 初始化参数
	public SnowflakeShardTableStrategy(IdGenerator idGenerator, String dateFormat) {
		// 检查属性取值
		Assert.notNull(idGenerator, "属性idGenerator(标识生成器)未设置");
		Assert.isTrue(StringUtils.isNoneBlank(dateFormat), "属性dateFormat(日期格式)未设置");

		// 设置属性
		this.idGenerator = idGenerator;
		this.dateFormat = dateFormat;
	}

	/**
	 * 获取目标表格名称
	 *
	 * @param tableName      表格名称
	 * @param parameterValue 参数取值
	 * @return 目标表格名称
	 */
	@Override
	public String getTargetTableName(String tableName, Object parameterValue) {
		// 检查参数取值
		Assert.isInstanceOf(Long.class, parameterValue, "分表参数取值类型错误");

		// 获取参数取值
		Long snowflakeId = (Long) parameterValue;
		Assert.notNull(snowflakeId, "获取分表雪花标识取值为空");

		// 获取分表后缀
		long timestamp = idGenerator.getTime(snowflakeId.longValue());
		String tableSuffix = DateFormatUtils.format(timestamp, dateFormat);
		Assert.isTrue(StringUtils.isNotBlank(tableSuffix), "获取分表后缀为空");

		// 组装表格名称
		StringBuilder sb = new StringBuilder(64);
		sb.append(tableName);
		sb.append(UNDER_LINE);
		sb.append(tableSuffix);

		// 返回表格名称
		return sb.toString();
	}

}
