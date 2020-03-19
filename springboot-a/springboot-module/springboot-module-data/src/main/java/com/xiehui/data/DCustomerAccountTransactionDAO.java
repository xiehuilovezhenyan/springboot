package com.xiehui.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.xiehui.plugin.shard.ShardTable;

/**
 * 客户账户交易DAO接口
 * 
 * @author xiehui
 *
 */
@Mapper
@Component
public interface DCustomerAccountTransactionDAO {

	/**
	 * 获取客户账户交易
	 * 
	 * @param id 交易标识
	 * @return 客户账户交易
	 */
	@ShardTable(value = "t_customer_account_transaction", shard = "customerId")
	public DCustomerAccountTransaction get(@Param("id") Long id, @Param("customerId") Long customerId);

	/**
	 * 创建客户账户交易
	 * 
	 * @param create 客户账户交易创建
	 * @return 创建行数
	 */
	@ShardTable(value = "t_customer_account_transaction", shard = "create.customerId")
	public Integer create(@Param("create") DCustomerAccountTransaction create);

	/**
	 * 修改客户账户交易
	 * 
	 * @param modify 客户账户交易修改
	 * @return 修改行数
	 */
	@ShardTable(value = "t_customer_account_transaction", shard = "modify.customerId")
	public Integer modify(@Param("modify") DCustomerAccountTransaction modify);

}
