package com.xiehui.common.util;

import java.math.BigDecimal;

/**
 * @ClassName: CalculateUtil
 * @author: pxw
 * @date: 2019年1月24日 下午1:54:56
 * @Description:计算器
 */
public class CalculateUtil {
	private static String toStr(Object obj) {
		String result = String.valueOf(obj);
		return result;
	}

	/**
	 * 加法
	 *
	 * @param var1
	 * @param var2
	 * @return
	 */
	public static double add(Object var1, Object var2) {
		BigDecimal b1 = new BigDecimal(CalculateUtil.toStr(var1));
		BigDecimal b2 = new BigDecimal(CalculateUtil.toStr(var2));
		return b1.add(b2).doubleValue();

	}

	/**
	 * 减法
	 *
	 * @param var1
	 * @param var2
	 * @return
	 */

	public static double sub(Object var1, Object var2) {
		BigDecimal b1 = new BigDecimal(CalculateUtil.toStr(var1));
		BigDecimal b2 = new BigDecimal(CalculateUtil.toStr(var2));
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 乘法
	 *
	 * @param var1
	 * @param var2
	 * @return
	 */
	public static double mul(Object var1, Object var2) {
		BigDecimal b1 = new BigDecimal(CalculateUtil.toStr(var1));
		BigDecimal b2 = new BigDecimal(CalculateUtil.toStr(var2));
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 除法
	 *
	 * @param v1
	 * @param v2
	 * @param scale 精度，到小数点后几位
	 * @return
	 */

	public static double div(Object var1, Object var2, int scale) {
		if (var1 == null)
			return 0.0;
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or ");
		}
		BigDecimal b1 = new BigDecimal(CalculateUtil.toStr(var1));
		BigDecimal b2 = new BigDecimal(CalculateUtil.toStr(var2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * 四舍五入
	 * 
	 * @param v
	 * @param scale 精确位数
	 * @return
	 */
	public static double round(Object v, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		BigDecimal b = new BigDecimal(CalculateUtil.toStr(v));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
