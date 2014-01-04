package com.augmentum.ams.dao.customized.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.augmentum.ams.dao.base.impl.BaseDaoImpl;
import com.augmentum.ams.dao.customized.CustomizedViewItemDao;
import com.augmentum.ams.model.customized.CustomizedViewItem;
import com.augmentum.ams.util.LogHelper;

@Repository("customizedViewItemDao")
public class CustomizedViewItemDaoImpl extends BaseDaoImpl<CustomizedViewItem> implements
        CustomizedViewItemDao {

    private Logger logger = Logger.getLogger(CustomizedViewItemDaoImpl.class);

    /* (non-Javadoc)
     * @see com.augmentum.ams.dao.customized.CustomizedViewItemDao#findByCustomizedViewId(java.lang.String)
     */
    @Override
    public List<CustomizedViewItem> findByCustomizedViewId(String customizedViewId)
            throws ParseException {

        logger.info(LogHelper.getLogInfo("Find by customized view id strat", customizedViewId));

        DetachedCriteria criteria = DetachedCriteria.forClass(CustomizedViewItem.class);

        criteria.createAlias("customizedView", "customizedView");
        criteria.add(Restrictions.eq("customizedView.id", customizedViewId));
        criteria.add(Restrictions.eq("isExpired", Boolean.FALSE));
        List<CustomizedViewItem> customizedViewItems = findByCriteria(criteria);

        logger.info(LogHelper.getLogInfo(
                "Find by customized view id end, customized view items size : ",
                customizedViewItems.size()));
        return customizedViewItems;
    }

    /* (non-Javadoc)
     * @see com.augmentum.ams.dao.customized.CustomizedViewItemDao#saveCustomizedViewItem(java.util.List)
     */
    @Override
    public void saveCustomizedViewItem(List<CustomizedViewItem> customizedViewItems) {

        logger.info(LogHelper.getLogInfo(
                "Save customized view item strat, customizedViewItems size",
                customizedViewItems.size()));

        super.hibernateTemplate.saveOrUpdateAll(customizedViewItems);

        logger.info(LogHelper.getLogInfo("Save customized view item end"));
    }

}
