package com.xiehui.mongdb;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;


import lombok.Data;

/**
 * 客户mongdb接口实现类
 * 
 * @author xiehui
 *
 */
@Repository
public class MCustomermDAO extends MongoGenDAO<DCustomer> {

    /**
     * 根据名字查找客户信息
     */
    public DCustomer findByUsername(String username) {
        Query sql = new Query(Criteria.where("username").is(username));
        return super.mongoTemplate.findOne(sql, DCustomer.class);
    }

    /**
     * 统计数量
     * 
     * @param mobile
     * @return
     */
    public Long countByCondition(String mobile) {
        Query query = new Query(Criteria.where("mobile").is(mobile));
        return count(query);
    }

    /**
     * 检查存在
     * 
     * @param mobile
     * @return
     */
    public Boolean existById(Long id) {
        Query query = new Query(Criteria.where("id").is(id));
        return exist(query);
    }

    /**
     * 条件查询
     * 
     * @param dCustomer
     * @return
     */
    public List<DCustomer> queryByCondition(DCustomer dCustomer) {
        Query query = new Query();
        // where (status = 0 and (wechatProvince = "" and wechatCity = "" and wechatCity = ""))
        query.addCriteria(new Criteria().andOperator(Criteria.where("status").is(0),
            new Criteria().andOperator(Criteria.where("wechatCountry").is("中国"),
                Criteria.where("wechatProvince").is("北京"), Criteria.where("wechatCity").is("朝阳"))));

        // 默认拼接的query条件为and形式
        if (Objects.nonNull(dCustomer.getIsDeleted())) {
            query.addCriteria(Criteria.where("isDeleted").is(dCustomer.getIsDeleted()));
        }
        if (Objects.nonNull(dCustomer.getId())) {
            query.addCriteria(Criteria.where("id").is(dCustomer.getId()));
        }
        return this.mongoTemplate.find(query, DCustomer.class);
    }

    /**
     * 分页查询
     * 
     * @param dCustomer
     * @return
     */
    public List<DCustomer> queryByPage(DCustomer dCustomer, Integer pageIndex, Integer pageSize) {
        // 初始化
        Criteria criteria1 = null;
        Criteria criteria2 = null;
        Criteria criteria3 = null;

        List<Criteria> list = new ArrayList<Criteria>();
        if (Objects.nonNull(dCustomer.getStatus())) {
            criteria1 = Criteria.where("status").is(dCustomer.getStatus());
            list.add(criteria1);
        }
        if (StringUtils.isNotBlank(dCustomer.getWechatName())) {
            // 模糊查询
            Pattern pattern = Pattern.compile("^.*" + dCustomer.getWechatName() + ".*$", Pattern.CASE_INSENSITIVE);
            criteria2 = Criteria.where("wechatName").regex(pattern);
            list.add(criteria2);
        }
        if (StringUtils.isNotBlank(dCustomer.getMobile())) {
            criteria3 = Criteria.where("mobile").is(dCustomer.getMobile());
            list.add(criteria3);
        }
        Criteria[] arr = new Criteria[list.size()];
        list.toArray(arr);
        Criteria criteria = new Criteria().andOperator(arr);
        Query query = new Query(criteria);
        query.skip((pageIndex - 1) * pageSize);
        query.limit(pageSize);
        query.with(new Sort(Direction.DESC, "createdTime"));

        // 查询数据
        List<DCustomer> dCustomerList = super.mongoTemplate.find(query, DCustomer.class);
        // 计算总数
        // long total = super.mongoTemplate.count(query, DCustomer.class);

        // 返回
        return dCustomerList;

    }

    /**
     * 测试 Aggregation
     * 
     * db.collection.aggregate(pipeline, options);
     * 
     * pipeline Arra
     * 
     * # 与mysql中的字段对比说明
     * 
     * $project #
     * 返回哪些字段,select,说它像select其实是不太准确的,因为aggregate是一个阶段性管道操作符,$project是取出哪些数据进入下一个阶段管道操作,真正的最终数据返回还是在group等操作中;
     * 
     * $match # 放在group前相当于where使用,放在group后面相当于having使用
     * 
     * 
     * $sort # 排序1升-1降 sort一般放在group后,也就是说得到结果后再排序,如果先排序再分组没什么意义;
     * 
     * 
     * $limit # 相当于limit m,不能设置偏移量
     * 
     * 
     * $skip # 跳过第几个文档
     * 
     * 
     * $unwind # 把文档中的数组元素打开,并形成多个文档,参考Example 一个文档中有数组[A,B,C] 使用unwind可以把一个文档拆分为三个文档A,B,C
     * 
     * $group: { _id: <expression>, <field1>: { <accumulator1> : <expression1> }, ... #
     * 按什么字段分组,注意所有字段名前面都要加$,否则mongodb就为以为不加$的是普通常量,其中accumulator又包括以下几个操作符 ————————————————#
     * $sum,$avg,$first,$last,$max,$min,$push,$addToSet
     * 
     * #如果group by null就是 count(*)的效果
     * 
     * $geoNear # 取某一点的最近或最远,在LBS地理位置中有用
     * 
     * $out # 把结果写进新的集合中。注意1,不能写进一个分片集合中。注意2,不能写进
     * 
     * 
     * @param dCustomer
     * @return
     */
    public List<Test> testAggregationCount(DCustomer dCustomer) {
        Criteria criteria = new Criteria().andOperator(Criteria.where("wechatCountry").is("中国"),
            Criteria.where("wechatProvince").is("北京"), Criteria.where("wechatCity").is("朝阳"));

        // 按照status,mobile 分组统计数量
        Aggregation agg = Aggregation.newAggregation(Aggregation.match(criteria),
            Aggregation.group("status", "mobile").count().as("totalCount"));

        // 返回
        AggregationResults<Test> outputType = super.mongoTemplate.aggregate(agg, DCustomer.class, Test.class);
        List<Test> list = outputType.getMappedResults();
        return list;
    }

    /**
     * 统计数据
     * 
     * @return
     */
    public Integer countByStatus() {
        Criteria criteria = Criteria.where("status").is(0);
        /**
         * criteria.and("AAA").elemMatch(new Criteria().in(c)); //AAA=[1,2,3] c=[1,2,3,4,5] 遍历字段AAA 只要有一个在集合c中就满足条件
         */
        /**
         * criteria.and("BBB").in(c); // 字段BBB在集合c中有数据
         */
        /**
         * criteria = criteria.and("CCC").gte(minWidthCloth).lte(maxWidthCloth); //字段值大于等于minWidthCloth
         * 小于等于maxWidthCloth
         */
        // criteria.and("openIds").elemMatch(new Criteria().andOperator(Criteria.where(""))); ??????????

        // 统计数量
        Aggregation agg = Aggregation.newAggregation(Aggregation.match(criteria), Aggregation.count().as("totalCount"));

        // 返回
        AggregationResults<Test> outputType = super.mongoTemplate.aggregate(agg, DCustomer.class, Test.class);
        List<Test> list = outputType.getMappedResults();
        return list.get(0).getTotalCount();
    }

    @Data
    public static class Test {
        private Integer status;
        private String mobile;
        private Integer totalCount;
    }

    /**
     * 获取class
     */
    @Override
    protected Class<DCustomer> getValueClazz() {
        return DCustomer.class;
    }

}
