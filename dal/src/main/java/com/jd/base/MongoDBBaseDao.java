package com.jd.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Collection;
import java.util.List;

/**
 * mongodb数据泛型dao类
 */
public abstract class MongoDBBaseDao<T> {

    @Autowired
    @Qualifier("mongoTemplate")
    protected MongoTemplate mongoTemplate;

    /**
     * 通过条件查询实体(集合)
     */
    public List<T> find(Query query) {
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 通过一定的条件查询一个实体
     */
    public T findOne(Query query) {
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 查询出所有数据
     */
    public List<T> findAll() {
        return this.mongoTemplate.findAll(getEntityClass());
    }

    /**
     * 查询并且修改记录
     */
    public T findAndModify(Query query, Update update) {

        return this.mongoTemplate.findAndModify(query, update, this.getEntityClass());
    }

    /**
     * 按条件查询,并且删除记录
     */
    public T findAndRemove(Query query) {
        return this.mongoTemplate.findAndRemove(query, this.getEntityClass());
    }

    /**
     * 通过条件查询更新数据
     */
    public void updateFirst(Query query, Update update) {
        mongoTemplate.updateFirst(query, update, this.getEntityClass());
    }

    /**
     * 保存一个对象到mongodb
     */
    public T save(T bean) {
        mongoTemplate.save(bean);
        return bean;
    }

    /**
     * 保存集合数据
     * 
     * @param objectsToSave
     */
    public void saveAll(Collection<? extends Object> objectsToSave) {
        mongoTemplate.insertAll(objectsToSave);
    }

    /**
     * 通过ID获取记录
     */
    public T findById(String id) {
        return mongoTemplate.findById(id, this.getEntityClass());
    }

    /**
     * 通过ID获取记录,并且指定了集合名(表的意思)
     */
    public T findById(String id, String collectionName) {
        return mongoTemplate.findById(id, this.getEntityClass(), collectionName);
    }

    /**
     * 获取需要操作的实体类class
     */
    protected abstract Class<T> getEntityClass();

}
