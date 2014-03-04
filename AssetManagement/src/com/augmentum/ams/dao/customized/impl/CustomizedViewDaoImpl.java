package com.augmentum.ams.dao.customized.impl;

import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.customized.CustomizedViewDao;
import com.augmentum.ams.model.customized.CustomizedView;

@Repository("customizedViewDao")
public class CustomizedViewDaoImpl extends BaseDaoImpl<CustomizedView> implements CustomizedViewDao {

    private Logger logger = Logger.getLogger(CustomizedViewDaoImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.dao.customized.CustomizedViewDao#findCustomizedViewsByUser
     * (java.lang.String)
     */
    @Override
    public List<CustomizedView> findCustomizedViewsByUser(String creatorId) throws ParseException {

        DetachedCriteria criteria = DetachedCriteria.forClass(CustomizedView.class);

        criteria.add(Restrictions.eq("creatorId", creatorId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<CustomizedView> customizedViews = findByCriteria(criteria);

        return customizedViews;
    }

    @Override
    public List<CustomizedView> findByUserAndCategory(String creatorId, String category) {

        DetachedCriteria criteria = DetachedCriteria.forClass(CustomizedView.class);

        criteria.add(Restrictions.eq("creatorId", creatorId));
        criteria.add(Restrictions.eq("creatorId", category));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<CustomizedView> customizedViews = findByCriteria(criteria);

        return customizedViews;
    }

}
