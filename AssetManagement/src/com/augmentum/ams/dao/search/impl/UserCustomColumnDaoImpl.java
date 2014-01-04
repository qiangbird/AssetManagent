package com.augmentum.ams.dao.search.impl;

import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.search.UserCustomColumnDao;
import com.augmentum.ams.model.user.UserCustomColumn;

@Repository("userCustomeColumnsDao")
public class UserCustomColumnDaoImpl extends BaseDaoImpl<UserCustomColumn> implements
        UserCustomColumnDao {

    public List<UserCustomColumn> getUserCustomColumns(String category, String userId) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCustomColumn.class)
                .setFetchMode("customizedColumn", FetchMode.JOIN).setFetchMode("user",
                        FetchMode.JOIN);

        detachedCriteria.createAlias("customizedColumn", "customizedColumn");
        detachedCriteria.createAlias("user", "user");

        detachedCriteria.add(Restrictions.eq("customizedColumn.categoryType", category));
        detachedCriteria.add(Restrictions.eq("user.userId", userId));
        detachedCriteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        detachedCriteria.addOrder(Order.asc("sequence"));

        return super.findByCriteria(detachedCriteria);
    }

    @Override
    public UserCustomColumn getUserCustomColumn(String userCustomColumnId) {

        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserCustomColumn.class);

        detachedCriteria.add(Restrictions.eq("id", userCustomColumnId));
        detachedCriteria.add(Restrictions.eq("isExpired", Boolean.FALSE));

        return super.getUnique(detachedCriteria);
    }
}
