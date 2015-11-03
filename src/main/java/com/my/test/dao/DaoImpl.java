package com.my.test.dao;

import java.math.BigInteger;
import java.util.*;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.my.test.util.PageInfo;

@Repository
public class DaoImpl<T> implements Dao<T>{

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	public Object save(T entity) {
		return getSession().save(entity);
	}

	public List<String> saveBatch(List<T> batList) {
		List<String> resList = new ArrayList<String>();
        String res;
        for (T t : batList) {
        	res = (String) getSession().save(t);
            resList.add(res);
        }
        return resList;
	}

	public void saveOrUpdateBatch(List<T> batList) {
		for (T t : batList) {
            getSession().saveOrUpdate(t);
        }
	}

	public void update(T entity) {
		getSession().update(entity);
	}

	/**
     * 执行更新hql <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     */
    public int update(final String hqlStr, final Map<String, Object> params) {
        
        Session session = getSession();
        
        Query query = session.createQuery(hqlStr);
        query.setProperties(params);
        return query.executeUpdate();
    }

    /**
     * 批量更新实体
     *
     * @param batList
     */
    public void updateBatch(List<T> batList) {
        for (Iterator<T> iterator = batList.iterator(); iterator.hasNext();) {
            T tempObj = iterator.next();
            getSession().update(tempObj);
        }
    }

    /**
     * 保存或更新实体 <br/>
     *
     * @param entity
     */
    public void saveOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    /**
     * 保存或更新实体 <br/>
     *
     * @param entity
     * @return
     */
    public T merage(T entity) {
        return (T) getSession().merge(entity);
    }

    /**
     * 删除实体 <br/>
     *
     * @param entity
     */
    public void delete(T entity) {
        getSession().delete(entity);
    }

    /**
     * 通过ID删除实体 <br/>
     *
     * @param entityClass
     * @param id
     */
    public void deleteById(Class<T> entityClass, String id) {
        getSession().delete(
                getSession().get(entityClass, id));
    }

    /**
     * 通过指定属性删除实体 <br/>
     *
     * @param entity
     * @param propertyName
     * @param value
     * @return
     */
    public int deleteByProperty(Class<T> entity, String propertyName, final Object value) {

        final String queryString = "delete from " + entity.getName()
                + " as model where model." + propertyName + "= ?";

        Session session = getSession();
        return session.createQuery(queryString).setParameter(0, value).executeUpdate();
    }

    /**
     * 批量删除对象 <br/>
     *
     * @param batList
     * @return
     */
    public void deleteBatch(List<T> batList) {
        for (Iterator<T> iterator = batList.iterator(); iterator.hasNext();) {
            T tempObj = iterator.next();
            getSession().delete(tempObj);
        }
    }

    /**
     * 查找指定的所有实体 <br/>
     *
     * @param entity
     * @return List<T>
     */
    public List<T> findAll(Class<T> entity) {
        Session session = getSession();
        return (List<T>) session.createQuery("from " + entity.getName()).list();
    }

    /**
     * 通过ID查找实体 <br/>
     *
     * @param entityClass
     * @param id
     * @return
     */
    public T findById(Class<T> entityClass, Integer id) {
        return (T) getSession().get(entityClass, id);
    }
    /**
     * 通过ID查找实体 <br/>
     *
     * @param entityClass
     * @param id
     * @return
     */
    public T findById(Class<T> entityClass, String id) {
        return (T) getSession().get(entityClass, id);
    }

    /**
     * 加载实体 <br/>
     *
     * @param entityClass
     * @param id
     * @param lockMode
     * @return
     */
    @Deprecated
    public T get(Class<T> entityClass, String id, LockMode lockMode) {
        return (T) getSession().get(entityClass, id, lockMode);
    }

    /**
     * 通过某一属性查找对象 <br/>
     *
     * @param entity
     * @param propertyName
     * @param value
     * @return
     */
    public List<T> findByProperty(Class<T> entity, String propertyName, final Object value) {

        final String queryString = "from " + entity.getName()
                + " as model where model." + propertyName + "= ?";

        Session session = getSession();
        return (List<T>) session.createQuery(queryString).setParameter(0, value).list();
    }

    /**
     * 通过hql语句查找实体，且指定分页 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    public List<T> findByPage(String hqlStr,
            Map<String, Object> params, PageInfo pageInfo) {
        
        Session session = getSession();
        String totalCont;
        if (hqlStr.indexOf("order by") != -1) {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"), hqlStr.indexOf("order by"));
        } else {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"));
        }
        Query query = session.createQuery(totalCont);
        query.setProperties(params);
        List resTotal = query.list();
        if (resTotal.size() > 0) {
            pageInfo.setTotalReco(((Long) resTotal.get(0)).intValue());
        }
        query = session.createQuery(hqlStr);
        query.setProperties(params);
        if (pageInfo.getCurrentPage() != 0) {
            pageInfo.setStRec((pageInfo.getCurrentPage() - 1)
                    * pageInfo.getPageSize());
        }
        query.setFirstResult(pageInfo.getStRec());
        query.setMaxResults(pageInfo.getPageSize());
        return (List<T>) query.list();
        
    }
    
    /**
     * {@inheritDoc}
     */
    public List<T> findByPage(String hqlStr,
            Map<String, Object> params, PageInfo pageInfo, int transformerType) {
        Session session = getSession();
        String totalCont;
        if (hqlStr.indexOf("order by") != -1) {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"), hqlStr.indexOf("order by"));
        } else {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"));
        }
        Query query = session.createQuery(totalCont);
        query.setProperties(params);
        List resTotal = query.list();
        if (resTotal.size() > 0) {
            pageInfo.setTotalReco(((Long) resTotal.get(0)).intValue());
        }
        query = session.createQuery(hqlStr);
        query.setProperties(params);
        if (pageInfo.getCurrentPage() != 0) {
            pageInfo.setStRec((pageInfo.getCurrentPage() - 1)
                    * pageInfo.getPageSize());
        }
        query.setFirstResult(pageInfo.getStRec());
        query.setMaxResults(pageInfo.getPageSize());


        if (transformerType == 1) {
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else if (transformerType == 2) {
            query.setResultTransformer(Transformers.TO_LIST);
        } 

        return query.list();
        
    }
    
    public List<T> findByManualPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo, final int transformerType) {
        
        Session session = getSession();
        
        Query query = session.createQuery(hqlStr);
        query.setProperties(params);

        query.setFirstResult(pageInfo.getStRec());
        query.setMaxResults(pageInfo.getPageSize());

        if (transformerType == 1) {
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else if (transformerType == 2) {
            query.setResultTransformer(Transformers.TO_LIST);
        } 

        return query.list();
    }

    /**
     * 通过hql语句查找实体 <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     */
    public List<T> find(final String hqlStr, final Map<String, Object> params) {
        
        Session session = getSession();
        Query query = session.createQuery(hqlStr);
        query.setProperties(params);
        return query.list();
        
    }
    
    public List<T> find(final String hqlStr, final Map<String, Object> params, final int transformerType) {
        Session session = getSession();
        
        Query query = session.createQuery(hqlStr);
        query.setProperties(params);

        if (transformerType == 1) {
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        } else if (transformerType == 2) {
            query.setResultTransformer(Transformers.TO_LIST);
        } 

        return query.list();
    }

    /**
     * 通过hql语句查找单个实体 <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     */
    public T findSingle(final String hqlStr, final Map<String, Object> params) {
        
        Session session = getSession();
        Query query = session.createQuery(hqlStr);
        query.setProperties(params);
        return (T) query.uniqueResult();
    }

    /**
     * 通过原生SQL查找实体 <br/>
     *
     * @param hqlStr
     * @param params
     * @return List
     */
    public List findSQL(final String hqlStr, final Map<String, Object> params) {
        
        Session session = getSession();
        Query query = session.createSQLQuery(hqlStr);
        query.setProperties(params);
        return query.list();
        
    }

    /**
     * 通过原生SQL查找实体 <br/>
     *
     * @param hqlStr
     * @param params
     * @return List
     */
    public List findSQL(final String hqlStr, final Map<String, Object> params, final Class<T> clazz) {
        
        Session session = getSession();
        Query query = session.createSQLQuery(hqlStr).addEntity(clazz);
        query.setProperties(params);
        return query.list();
        
    }

    /**
     * 通过原生SQL查找实体,且指定分页 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    public List findSQLByPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo) {
        
        Session session = getSession();
        
        String totalCont;
        if (hqlStr.indexOf("order by") != -1) {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"), hqlStr.indexOf("order by"));
        } else {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"));
        }
        Query query = session.createSQLQuery(totalCont);
        query.setProperties(params);
        List resTotal = query.list();
        if (resTotal.size() > 0) {
            pageInfo.setTotalReco(((BigInteger) resTotal.get(0)).intValue());
        }
        query = session.createSQLQuery(hqlStr);
        query.setProperties(params);
        if (pageInfo.getCurrentPage() != 0) {
            pageInfo.setStRec((pageInfo.getCurrentPage() - 1)
                    * pageInfo.getPageSize());
        }
        query.setFirstResult(pageInfo.getStRec());
        query.setMaxResults(pageInfo.getPageSize());
        return query.list();
    }

    /**
     * 通过原生SQL查找实体,且指定分页 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    public List<T> findSQLByPage(final String hqlStr,
            final Map<String, Object> params, final PageInfo pageInfo, final Class<T> clazz) {
        
        Session session = getSession();
        String totalCont = "select count(*) ";
        if (hqlStr.indexOf("order by") != -1) {
            totalCont += hqlStr.substring(hqlStr.indexOf("from"), hqlStr.indexOf("order by"));
        } else {
            totalCont += hqlStr.substring(hqlStr.indexOf("from"));
        }
        Query query = session.createSQLQuery(totalCont);
        query.setProperties(params);
        List resTotal = query.list();
        if (resTotal.size() > 0) {
            pageInfo.setTotalReco(((BigInteger) resTotal.get(0)).intValue());
        }
        query = session.createSQLQuery(hqlStr).addEntity(clazz);
        query.setProperties(params);
        if (pageInfo.getCurrentPage() != 0) {
            pageInfo.setStRec((pageInfo.getCurrentPage() - 1)
                    * pageInfo.getPageSize());
        }
        query.setFirstResult(pageInfo.getStRec());
        query.setMaxResults(pageInfo.getPageSize());
        return query.list();
        
    }

    /**
     * 通过原生SQL查找实体 <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     */
    public int executeSQL(final String hqlStr, final Map<String, Object> params) {
        Session session = getSession();
        
        Query query = session.createSQLQuery(hqlStr);
        query.setProperties(params);
        int res = query.executeUpdate();
        return res;
    }

    /**
     * 查找HQL结果集的总数量 <br/>
     *
     * @param hqlStr
     * @param params
     * @return
     *
     * 输入参数： 输出参数
     */
    public int getAllCount(final String hqlStr) {

        Session session = getSession();
        Query query = session.createQuery(hqlStr);
        return ((Long) query.list().get(0)).intValue();
    }

    /**
     * 把hibernate缓存写入库中 <br/>
     *
     */
    public void flush() {
        getSession().flush();
    }

    /**
     * 清理session缓存 <br/>
     *
     */
    public void clear() {
        getSession().clear();
    }

    /**
     * 把实体从session缓存中去除 <br/>
     *
     * @param entity
     */
    public void evict(T entity) {
        getSession().evict(entity);
    }

    /**
     * 加载一个指定id得实体 (返回得是一个代理对象) <br/>
     *
     * @param entityClass
     * @param id
     * @return
     */
    public T load(Class<T> entityClass, String id) {
        return (T) getSession().load(entityClass, id);
    }
    
    /**
     * {@inheritDoc}
     */
    public Long findCounts(final String hql, final Map<String, Object> params) {
    	Session session = getSession();
        Query query = session.createQuery(hql);
        query.setProperties(params);
        List list = query.list();
        return list.isEmpty() ? 0 : (Long) list.get(0);
    }
    
    /**
     * 通过hql语句查找实体，且指定分页 <br/>
     *
     * @param hqlStr
     * @param params
     * @param pageInfo
     * @return
     */
    public List findModelViewByPage(String hqlStr, PageInfo pageInfo) {
        
        Session session = getSession();
        String totalCont;
        if (hqlStr.indexOf("order by") != -1) {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"), hqlStr.indexOf("order by"));
        } else {
            totalCont = "select count(*) "
                    + hqlStr.substring(hqlStr.indexOf("from"));
        }
        Query query = session.createQuery(totalCont);
        List resTotal = query.list();
        if (resTotal.size() > 0) {
            pageInfo.setTotalReco(((Long) resTotal.get(0)).intValue());
        }
        query = session.createQuery(hqlStr);
        if (pageInfo.getCurrentPage() != 0) {
            pageInfo.setStRec((pageInfo.getCurrentPage() - 1)
                    * pageInfo.getPageSize());
        }
        query.setFirstResult(pageInfo.getStRec());
        query.setMaxResults(pageInfo.getPageSize());
        return query.list();
        
//      List<T> list=new ArrayList<T>();
//		list=(List<HouseView>) this.getSession().createQuery(hqlStr).list();
//		return list;
        
    }

}
