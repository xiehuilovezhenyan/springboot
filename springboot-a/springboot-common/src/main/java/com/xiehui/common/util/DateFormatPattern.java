/*
 * @Copyright: Copyright (C) 2018 BlueSea, Inc. All rights reserved.
 * 
 * @Company: 北京巅峰同道科技有限公司
 */

package com.xiehui.common.util;

/**
 * 日期格式常量
 *
 * @author Suncm
 * @since 2018.11.22 14:12
 */
public interface DateFormatPattern {

	interface CommonDatePattern {
		/** yyyy-MM-dd */
		String YYYY_MM_DD = "yyyy-MM-dd";
		/** yyyyMMdd */
		String YYYYMMDD = "yyyyMMdd";
	}

	interface CommonTimePattern {
		/** HH:mm:ss */
		String HH_MM_SS = "HH:mm:ss";
		/** HHmmss */
		String HHMMSS = "HHmmss";
	}

	interface CommonDateTimePattern {
		/** yyyy-MM-dd HH:mm:ss */
		String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
		/** yyyyMMddHHmmss */
		String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
		/** yyyyMMdd HH:mm:ss */
		String YYYYMMDDHH_MM_SS = "yyyyMMdd HH:mm:ss";
		/** yyyy-MM-dd HH:mm */
		String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
		/** yyyy-MM-dd HH */
		String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
	}

	interface ExtendPattern {
		/** yyyy-MM-dd 00:00:00 */
		String YEAR_MONTH_DAY_00_00_00 = "yyyy-MM-dd 00:00:00";
		/** yyyy-MM-dd 23:59:59 */
		String YEAR_MONTH_DAY_23_59_59 = "yyyy-MM-dd 23:59:59";
		/** yyyy-MM-dd 00:00:01 */
		String YEAR_MONTH_DAY_00_00_01 = "yyyy-MM-dd 00:00:01";
	}

}
