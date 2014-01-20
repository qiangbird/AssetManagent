package com.augmentum.ams.service.common;

import java.util.Collection;
import java.util.List;

import com.augmentum.ams.exception.ExcelException;

public interface CommonService {

/*    public <T extends BaseEntity> T get(Class<T> entity, String id);

    public <T extends BaseEntity> void create(T entity);

    public <T extends BaseEntity> void update(T entity);

    public <T extends BaseEntity> void delete(T entity);

    public <T extends BaseEntity> void delete(Class<T> entity, String id);

    public <T extends BaseEntity> void delete(Collection<T> entities);

    public <T extends BaseEntity> void expire(T entity);

    public <T extends BaseEntity> void expire(Class<T> entity, String id);

    public <T extends BaseEntity> void expire(Collection<T> entities);

    public <T extends BaseEntity> void evict(T entity);

    public <T extends BaseEntity> void saveOrUpdate(T entity);

    public <T extends BaseEntity> void saveOrUpdate(Collection<T> entities);

    public <T extends BaseEntity> List<T> listAll(Class<T> entity);

    public <T extends BaseEntity> List<T> listAllValid(Class<T> entity);

    public <T extends BaseEntity> List<T> find(Exp exp);

    public <T extends BaseEntity> T findUnique(Exp exp);

    public <T extends BaseEntity> PagingList find(SearchItem<T> searchItem);

    public <T extends BaseEntity> T findUnique(SearchItem<T> searchItem);

    public void clear();

    public void flush();*/

    public String export(Class<?> entity, Collection<?> collection) throws ExcelException;
}
