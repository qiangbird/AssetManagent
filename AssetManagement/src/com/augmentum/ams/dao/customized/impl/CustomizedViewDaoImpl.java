package com.augmentum.ams.dao.customized.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.customized.CustomizedViewDao;
import com.augmentum.ams.model.customized.CustomizedView;

@Repository("customizedViewDao")
public class CustomizedViewDaoImpl extends BaseDaoImpl<CustomizedView> implements CustomizedViewDao {

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.customized.CustomizedViewDao#findCustomizedViewsByUser
     * (java.lang.String)
     */
    @Override
    public List<CustomizedView> findCustomizedViewsByUser(String creatorId) {

        DetachedCriteria criteria = DetachedCriteria.forClass(CustomizedView.class);

        criteria.add(Restrictions.eq("creatorId", creatorId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<CustomizedView> customizedViews = findByCriteria(criteria);

        return customizedViews;
    }

    @Override
    public List<CustomizedView> findByUserAndCategoryType(String creatorId, String category) {

        DetachedCriteria criteria = DetachedCriteria.forClass(CustomizedView.class);

        criteria.add(Restrictions.eq("creatorId", creatorId));
        criteria.add(Restrictions.eq("categoryType", category));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<CustomizedView> customizedViews = findByCriteria(criteria);

        return customizedViews;
    }

}
