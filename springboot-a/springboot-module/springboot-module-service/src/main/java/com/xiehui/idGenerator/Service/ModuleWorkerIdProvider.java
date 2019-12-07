package com.xiehui.idGenerator.Service;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.xiehui.common.util.IpAddressHelper;
import com.xiehui.data.DSnowflakeWorker;
import com.xiehui.data.DSnowflakeWorkerDAO;
import com.xiehui.plugin.snowflake.WorkerIdProvider;

/**
 * 模块工作者标识提供者类
 *
 * @author xiehui
 */
@Service
public class ModuleWorkerIdProvider implements WorkerIdProvider {

	/**
	 * 雪花工作者DAO
	 */
	@Autowired
	private DSnowflakeWorkerDAO dSnowflakeWorkerDAO;

	/**
	 * 模块名称
	 */
	@Value("${module.name}")
	private String moduleName;

	/**
	 * 获取工作者标识
	 *
	 * @return 工作者标识
	 */
	@Override
	public long getWorkerId() {
		// 检查模块名称
		Assert.isTrue(StringUtils.isNoneBlank(moduleName), "模块名称未设置");

		// 获取本机地址
		String hostAddress = IpAddressHelper.getLocalIpAddress();
		Assert.isTrue(StringUtils.isNoneBlank(hostAddress), "获取本机地址无效");

		// 获取工作者标识
		Long workerId = dSnowflakeWorkerDAO.getMyWorkerId(moduleName, hostAddress);
		if (Objects.isNull(workerId)) {
			// 创建工作者信息
			DSnowflakeWorker workerCreate = new DSnowflakeWorker();
			workerCreate.setModuleName(moduleName);
			workerCreate.setHostAddress(hostAddress);
			dSnowflakeWorkerDAO.create(workerCreate);

			// 获取工作者标识
			workerId = dSnowflakeWorkerDAO.getMyWorkerId(moduleName, hostAddress);

		}
		Assert.notNull(workerId, "获取工作者标识为空");

		// 返回工作标识
		return workerId.longValue();
	}

}
