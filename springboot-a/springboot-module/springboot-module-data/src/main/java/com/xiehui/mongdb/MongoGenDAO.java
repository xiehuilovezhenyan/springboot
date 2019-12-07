package com.xiehui.mongdb;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.Assert;

/**
 * mongdb抽象类
 * 
 * @author xiehui
 *
 * @param <T>
 */
public abstract class MongoGenDAO<T> {

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 获取 value 对象Class类型
     * 
     * @return Class类型
     */
    protected abstract Class<T> getValueClazz();

    /**
     * 保存一个对象(保存或者更新)
     * 
     * @param t
     */
    public void save(T t) {
        this.mongoTemplate.save(t);
    }

    /**
     * 批量保存一批对象(不具备更新功能)
     * 
     * @param tList
     */
    public void batchSave(List<T> tList) {
        this.mongoTemplate.insertAll(tList);
    }

    /**
     * 保存或者更新一个对象
     * 
     * @param User
     */
    public void saveOrUpdate(T t) {
        this.mongoTemplate.save(t);
    }

    /**
     * 删除一个对象
     * 
     * @param id
     */
    public void remove(Long id) {
        T t = findById(id);
        this.mongoTemplate.remove(t);
    }

    /**
     * 查找一个对象
     * 
     * @param id
     * @return
     */
    public T findById(Long id) {
        // 获取value类型 并 assert
        Class<T> valueClazz = buildValueClazz();
        return this.mongoTemplate.findById(id, valueClazz);
    }

    /**
     * 检查存在
     * 
     * @param query
     * @return
     */
    public Boolean exist(Query query) {
        // 获取value类型 并 assert
        Class<T> valueClazz = buildValueClazz();
        return this.mongoTemplate.exists(query, valueClazz);
    }

    /**
     * 统计数量
     * 
     * @param query
     * @return
     */
    public Long count(Query query) {
        // 获取value类型 并 assert
        Class<T> valueClazz = buildValueClazz();
        return this.mongoTemplate.count(query, valueClazz);
    }

    /**
     * 查询所有的T
     * 
     * @return
     */
    public List<T> findAll() {
        // 获取value类型 并 assert
        Class<T> valueClazz = buildValueClazz();
        return this.mongoTemplate.findAll(valueClazz);
    }

    /**
     * 本类中获取 value对象Class类型
     *
     * @return 类型
     */
    private Class<T> buildValueClazz() {
        Class<T> valueClazz = getValueClazz();
        Assert.isTrue(Objects.nonNull(valueClazz), "buildValueClazz() must not be null all.");
        return valueClazz;
    }
}
