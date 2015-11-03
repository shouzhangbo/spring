package com.my.test.dao;

import java.util.*;

import org.hibernate.LockMode;

import com.my.test.util.PageInfo;

public interface Dao<T> {

	/**
     * 保存一个对象 <br/>
     *
     * @param entity
     * @return
     */
    Object save(T entity);

    /**
     * 批量保存 <br/>
     *
     * @param batList
     * @return
     */
    List<String> saveBatch(List<T> batList);
    
    /**
     * 批量保存或更新 <br/>
     * 
     * @param batList
     * @return 
     */
    void saveOrUpdateBatch(List<T> batList);

    /**
     * 更新一个对象 <br/>
     *
     * @param entity
     */
    void update(T entity);
    
    /**
     * 执行更新hql <br/>
     * 
     * @param hql
     * @param params
     * @return 
     */
    int update(final String hqlStr, final Map<String,Object> params);

    /**
     * 批量更新对象 <br/>
     *
     * @param batList
     */
    void updateBatch(List<T> batList);

    /**
     * 保存或者更新 <br/>
     *
     * @param entity
     */
    void saveOrUpdate(T entity);

    /**
     * 保存或者更新 <br/>
     *
     * @param entity
     */
    T merage(T entity);

    /**
     * 删除一个对象 <br/>
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * 按id删除对象 <br/>
     */
    void deleteById(Class<T> entityClass, String id);

    /**
     * 根据条件批量删除操作 <br/>
     */
    public int deleteByProperty(Class<T> entity, String propertyName, final Object value);
    
    /**
     * 批量删除对象 <br/>
     *
     * @param batList
     */
    void deleteBatch(List<T> batList);

    /**
     * 获取全部对象 <br/>
     */
    List<T> findAll(Class<T> entity);

    /**
     * 按id获取对象 <br/>
     */
    T findById(Class<T> entityClass, String id);
    /**
     * 按id获取对象 <br/>
     */
    T findById(Class<T> entityClass, Integer id);
    
    /**
     * 通过某一属性查找对象 <br/>
     * 
     * @param entity
     * @param propertyName
     * @param value
     * @return 
     */
    List<T> findByProperty(Class<T> entity, String propertyName, final Object value);

    /**
     * 分页查询 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    List<T> findByPage(final String hqlStr, final Map<String, Object> params,
            final PageInfo pageInfo);

    /**
     * 分页查询 <br/>
     * 
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @param transformerType     0 是默认 1 是 ALIAS_TO_ENTITY_MAP 2 是 TO_LIST
     * @return 
     */
    List findByPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo, final int transformerType);
    
    /**
     * 手动分页查询
     * 
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @param transformerType
     * @return 
     */
    List findByManualPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo, final int transformerType);
    
    /**
     * 查询列表 <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     */
    List<T> find(final String hqlStr, final Map<String, Object> params);
    
    /**
     * 查询列表 <br/>
     * 
     * @param hqlStr
     * @param params
     * @param transformerType   0 是默认 1 是 ALIAS_TO_ENTITY_MAP 2 是 TO_LIST
     * @return 
     */
    List find(final String hqlStr, final Map<String, Object> params, final int transformerType);
    
    /**
     * 查询单个对象 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    T findSingle(final String hqlStr, final Map<String, Object> params);

    /**
     * 执行原生的SQL语句 <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     */
    List findSQL(final String hqlStr, final Map<String, Object> params);

    /**
     * 原生的SQL语句分页 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    List findSQLByPage(final String hqlStr, final Map<String, Object> params,
            final PageInfo pageInfo);
    /**
     * 原生的SQL语句分页 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    List findSQLByPage(final String hqlStr, final Map<String, Object> params,
            final PageInfo pageInfo,Class<T> clazz);

    /**
     * 执行原生的SQL语句 <br/>
     *
     * @param string
     * @param params
     * @return
     */
    int executeSQL(final String hqlStr, final Map<String, Object> params);
    
    /**
     * 把hibernate缓存写入库中 <br/>
     * 
     */
    void flush();
    
    /**
     * 加载实体 <br/>
     * 
     * @param entityClass
     * @param id
     * @param lockMode
     * @return 
     */
    T get(Class<T> entityClass, String id, LockMode lockMode);
    
    /**
     * 清理session缓存 <br/>
     * 
     */
    void clear();
    
    /**
     * 把实体从session缓存中去除 <br/>
     * 
     * @param entity 
     */
    void evict(T entity);
    
    /**
     * 加载一个指定id得实体 <br/>
     * 
     * @param entityClass
     * @param id
     * @return 
     */
    T load(Class<T> entityClass, String id);
    
    
     /**
     * 查找HQL结果集的总数量 <br/>
     * 
     * @param hqlStr
     * @param params
     * @return 
     * 
     * 输入参数：
     * 输出参数
     */
    int getAllCount(final String hqlStr);
    
    /**
     * 通过原生SQL查找实体  <br/>
     * 
     * @param hqlStr
     * @param params
     * @return List
     */
    List findSQL(final String hqlStr, final Map<String, Object> params, final Class<T> clazz);
    
    /**
     * 根据条件查询满足条件的数量
     * 
     * @param clazz
     * @param params
     * @return
     */
    Long findCounts(final String hql, final Map<String, Object> params);
    
    List findModelViewByPage(String hqlStr, PageInfo pageInfo);
}
