package com.xiehui.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.xiehui.plugin.shard.ShardTable;

/**
 * 客户点击日志DAO接口
 * 
 * @author xiehui
 *
 */
@Mapper
@Component
public interface DCustomerClickLogDAO {

    /**
     * 获取客户点击日志
     * 
     * @param id 日志标识
     * @return 客户点击日志
     */
	@ShardTable(value = "t_customer_click_log", shard = "id")
    public DCustomerClickLog get(@Param("id") Long id);

    /**
     * 存在客户点击日志
     * 
     * @param id 日志标识
     * @return 是否存在
     */
	@ShardTable(value = "t_customer_click_log", shard = "id")
    public Boolean exist(@Param("id") Long id);

    /**
     * 创建客户点击日志
     * 
     * @param create 客户点击日志创建
     * @return 创建行数
     */
    @ShardTable(value = "t_customer_click_log", shard = "create.id")
    public Integer create(@Param("create") DCustomerClickLog create);

    /**
     * 是否存在 上报记录
     */
    @ShardTable(value = "t_customer_click_log", shard = "customerId")
    boolean existOfPlatFrom(@Param("customerId") Long customerId, @Param("platFrom") String platFrom);

}
