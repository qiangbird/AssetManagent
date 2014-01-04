package com.augmentum.ams.dao.search.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.search.UserPageSizeDao;
import com.augmentum.ams.model.base.PageSize;

@Repository("userPageSizeDao")
public class UserPageSizeDaoImpl extends BaseDaoImpl<PageSize> implements UserPageSizeDao {

    @Override
    public List<PageSize> findPageSizes() {
        
        String hql = "FROM PageSize WHERE isExpired = ? AND isDefaultValue = ?";
        return super.find(hql, Boolean.FALSE, Boolean.TRUE);
    }

    @Override
    public PageSize getPageSize(int categoryFlag, int pageSizeValue) {
        
        String hql = "FROM PageSize WHERE isExpired = ? AND categoryFlag = ? AND pageSizeValue = ?";
        return super.getUnique(hql, Boolean.FALSE, categoryFlag, pageSizeValue);
    }

}
