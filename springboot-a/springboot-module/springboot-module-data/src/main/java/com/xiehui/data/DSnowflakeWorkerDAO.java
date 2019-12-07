package com.xiehui.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * 雪花工作者DAO接口
 *
 * @author cychen
 */
@Component
@Mapper
public interface DSnowflakeWorkerDAO {

	/**
	 * 创建雪花工作者
	 *
	 * @param create 雪花工作者创建
	 * @return 创建行数
	 */
	public Integer create(@Param("create") DSnowflakeWorker create);

	/**
	 * 获取我的工作者标识
	 *
	 * @param moduleName  模块名称
	 * @param hostAddress 主机地址
	 * @return 工作者标识
	 */
	public Long getMyWorkerId(@Param("moduleName") String moduleName, @Param("hostAddress") String hostAddress);

}
