package com.augmentum.ams.service.common.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.augmentum.ams.excel.ExcelBuilder;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.service.common.CommonService;

public class CommonServiceImpl implements CommonService {

/*    @Autowired
    @Qualifier("commonDao")
    private CommonDao<String> commonDao;

    @Override
    public <T extends BaseEntity> T get(Class<T> entity, String id) {
        return (T) commonDao.get(entity, id);
    }

    @Override
    public <T extends BaseEntity> void create(T entity) {
        commonDao.save(entity);
    }

    @Override
    public <T extends BaseEntity> void update(T entity) {
        commonDao.update(entity);
    }

    @Override
    public <T extends BaseEntity> void saveOrUpdate(T entity) {
        commonDao.saveOrUpdate(entity);
    }

    @Override
    public <T extends BaseEntity> void saveOrUpdate(Collection<T> entities) {
        commonDao.saveAll(entities);
    }

    @Override
    public void flush() {
        commonDao.getHibernateTemplate().flush();
    }

    @Override
    public void clear() {
        commonDao.clear();
    }

    @Override
    public <T extends BaseEntity> void delete(T entity) {
        commonDao.delete(entity);
    }

    @Override
    public <T extends BaseEntity> void delete(Class<T> entity, String id) {
        commonDao.delete(commonDao.get(entity, id));
    }

    @Override
    public <T extends BaseEntity> void delete(Collection<T> entities) {
        commonDao.delete(entities);
    }

    @Override
    public <T extends BaseEntity> void expire(T entity) {
        commonDao.expire(entity);
    }

    @Override
    public <T extends BaseEntity> void expire(Class<T> entity, String id) {
        commonDao.expire(commonDao.get(entity, id));
    }

    @Override
    public <T extends BaseEntity> void expire(Collection<T> entities) {
        commonDao.expire(entities);
    }

    @Override
    public <T extends BaseEntity> void evict(T entity) {
        commonDao.evict(entity);
    }

    @Override
    public <T extends BaseEntity> List<T> listAll(Class<T> entity) {
        return (List<T>) commonDao.listAll(entity);
    }

    @Override
    public <T extends BaseEntity> List<T> listAllValid(Class<T> entity) {
        return (List<T>) commonDao.listAllValid(entity);
    }

    @Override
    public <T extends BaseEntity> List<T> find(Exp exp) {
        return commonDao.find(exp);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends BaseEntity> T findUnique(Exp exp) {
        return (T) commonDao.findUnique(exp);
    }

    @Override
    public <T extends BaseEntity> PagingList find(SearchItem<T> searchItem) {
        return commonDao.find(searchItem);
    }

    @Override
    public <T extends BaseEntity> T findUnique(SearchItem<T> searchItem) {
        return (T) commonDao.findUnique(searchItem);
    }*/

    @Override
    public String export(Class<?> entity, Collection<?> collection) throws ExcelException {
        return ExcelBuilder.getParser(entity).parse(collection);
    }
}
