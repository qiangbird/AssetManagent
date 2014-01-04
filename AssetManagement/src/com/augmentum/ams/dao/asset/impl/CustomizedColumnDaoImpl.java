package com.augmentum.ams.dao.asset.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.asset.CustomizedColumnDao;
import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.model.asset.CustomizedColumn;

@Repository("customizedColumnDao")
public class CustomizedColumnDaoImpl extends BaseDaoImpl<CustomizedColumn> implements
        CustomizedColumnDao {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.asset.CustomizedColumnDao#getDefaultColumns(java
     * .lang.String)
     */
    @Override
    public List<CustomizedColumn> findDefaultColumns(String category) {
        
        String hql = "FROM CustomizedColumn WHERE categoryType = ? AND isExpired = ? ORDER BY sequence asc";
        return super.find(hql, category, Boolean.FALSE);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.asset.CustomizedColumnDao#getValueOfColumn(java
     * .lang.String, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<String> getValueOfColumn(String realName, String realTable) {

        StringBuffer hql = new StringBuffer("SELECT ");
        hql.append(realName);
        hql.append(" FROM ");
        hql.append(realTable);
        hql.append(" WHERE isExpired = ?");
        List<String> values = super.hibernateTemplate.find(hql.toString(), Boolean.FALSE);

        return values;
    }

    @Override
    public List<CustomizedColumn> findAllColumns() {
        
        String hql = "FROM CustomizedColumn WHERE isExpired = ? ORDER BY sequence asc";
        return super.find(hql, Boolean.FALSE);
    }

}
