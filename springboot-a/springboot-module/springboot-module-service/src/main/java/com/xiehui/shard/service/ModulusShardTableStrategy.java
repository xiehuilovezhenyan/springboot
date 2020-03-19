package com.xiehui.shard.service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import com.xiehui.plugin.shard.ShardTableStrategy;

import lombok.Setter;

/**
 * 取模分表策略类
 *
 * @author xiehui
 */
public class ModulusShardTableStrategy implements ShardTableStrategy {

	/**
	 * 分表数量
	 */
	@Setter
	private Integer shardCount;
	/**
	 * 是否补零
	 */
	@Setter
	private Boolean isZeroized;

	/**
	 * 配置属性
	 * 
	 * @param shardCount
	 * @param isZeroized
	 */
	public ModulusShardTableStrategy(Integer shardCount, Boolean isZeroized) {
		// 检查属性取值
		Assert.notNull(shardCount, "属性shardCount(分表数量)未设置");
		Assert.isTrue(shardCount > 0, "属性shardCount(分表数量)不为正整数");
		Assert.notNull(isZeroized, "属性isZeroized(是否补零)未设置");

		// 设置属性
		this.shardCount = shardCount;
		this.isZeroized = isZeroized;
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
		Assert.notNull(parameterValue, "分表参数取值为空");

		// 检查参数取值
		long shardValue = 0L;
		if (parameterValue instanceof Long) {
			shardValue = ((Long) parameterValue).longValue();
		} else if (parameterValue instanceof Integer) {
			shardValue = ((Integer) parameterValue).longValue();
		} else if (parameterValue instanceof Short) {
			shardValue = ((Short) parameterValue).longValue();
		} else if (parameterValue instanceof Double) {
			shardValue = ((Double) parameterValue).longValue();
		} else if (parameterValue instanceof Float) {
			shardValue = ((Float) parameterValue).longValue();
		} else {
			throw new IllegalArgumentException("无效的取模数据类型(" + parameterValue.getClass() + ")");
		}

		// 获取分表后缀
		String tableSuffix = null;
		long shardIndex = shardValue % shardCount;
		if (Boolean.TRUE.equals(isZeroized)) {
			int length = String.valueOf(shardCount - 1).length();
			tableSuffix = String.format("%0" + length + "d", shardIndex);
		} else {
			tableSuffix = String.valueOf(shardIndex);
		}

		// 组装表格名称
		StringBuilder sb = new StringBuilder(64);
		sb.append(tableName);
		sb.append(UNDER_LINE);
		sb.append(tableSuffix);

		// 返回表格名称
		return sb.toString();
	}

}
