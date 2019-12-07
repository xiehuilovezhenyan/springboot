package com.xiehui.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 课程DAO接口
 * 
 * @author xiehui
 *
 */
@Component
@Mapper
public interface DCourseDAO {

	/**
	 * 获取课程
	 * 
	 * @param id 课程标识
	 * @return 课程
	 */
	public DCourse get(@Param("id") Long id);

	/**
	 * 存在课程
	 * 
	 * @param id 课程标识
	 * @return 是否存在
	 */
	public Boolean exist(@Param("id") Long id);

	/**
	 * 创建课程
	 * 
	 * @param create 课程创建
	 * @return 创建行数
	 */
	public Integer create(@Param("create") DCourse create);

	/**
	 * 修改课程
	 * 
	 * @param modify 课程修改
	 * @return 修改行数
	 */
	public Integer modify(@Param("modify") DCourse modify);

	/**
	 * 查询某个用户的已经购买的课程列表
	 * 
	 * @param customerId
	 * @return
	 */
	public List<DCourse> selectShoppingCourseByCustomerId(@Param("customerId") Long customerId);

	/**
	 * 根据类型查询课程
	 * 
	 * @param type
	 * @return
	 */
	List<DCourse> listHomeCourseByParameter(@Param("type") Short type);

	/**
	 * 查询热推列表
	 * 
	 * @return
	 */
	List<DCourse> listHotCourse();

	List<DCourse> listCourseByPrimaryKeyIds(@Param("ids") List<Long> ids);

	/**
	 * 根据 一组标识 拼接名字<br>
	 * 使用场景: 打包课订单详情
	 * 
	 * @param ids 标识集合
	 * @return 名字拼接
	 */
	String groupConcatCourseNameByIds(@Param("ids") List<Long> ids);

	/**
	 * 查询所有简单系列课，没有引导加群
	 * 
	 * @return
	 */
	List<DCourse> selectAllSimpleCourse();

	List<DCourse> selectVirtualProduct();

	List<Long> listCourseIdByParameter(@Param("type") Short type);

	/**
	 * 根据课程版本昵称查询课程列表
	 * 
	 * @param versionNikeName
	 * @return
	 */
	List<DCourse> selectCourseByVersionNikeName(@Param("versionNikeName") String versionNikeName);

	/**
	 * 根据营期标识集合查询课程集合
	 * 
	 * @param campDateIds
	 * @return
	 */
	List<DCourse> listCourseByCampDateIds(@Param("campDateIds") List<Long> campDateIds);
}
