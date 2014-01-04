package com.augmentum.ams.dao.customized;

import java.text.ParseException;
import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.customized.CustomizedViewItem;

public interface CustomizedViewItemDao extends BaseDao<CustomizedViewItem> {
    
    /**
     * @description Get all CustomizedViewItem objects by customizedViewId.
     * @author John.li
     * @time Oct 30, 2013 17:18:31 PM
     * @param customizedViewId
     * @return
     * @throws ParseException 
     */
    List<CustomizedViewItem> findByCustomizedViewId(String customizedViewId) throws ParseException;
    
    /**
     * @description Batch save CustomizedViewItem objects.
     * @author John.li
     * @time Nov 17, 2013 17:18:31 PM
     * @param customizedViewItems
     * @return
     */
    void saveCustomizedViewItem(List<CustomizedViewItem> customizedViewItems);

}
