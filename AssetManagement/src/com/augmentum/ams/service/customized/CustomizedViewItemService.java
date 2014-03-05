package com.augmentum.ams.service.customized;

import java.util.List;

import org.apache.lucene.search.BooleanQuery;
import org.hibernate.search.query.dsl.QueryBuilder;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedViewItem;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

public interface CustomizedViewItemService {
 
    /**
     * @description Save the CustomizedViewItem object.
     * @author John.li
     * @time Oct 30, 2013 16:48:34 PM
     * @param customizedViewVo
     * @return
     */
    void saveCustomizedViewItem(CustomizedViewVo customizedViewVo);
    
    /**
     * @description Delete the CustomizedViewItem object.
     * @author John.li
     * @time Oct 30, 2013 16:48:50 PM
     * @param customizedViewVo
     * @return
     */
    void deleteCustomizedViewItem(CustomizedViewVo customizedViewVo);
    
    /**
     * @description Update the CustomizedViewItem object.
     * @author John.li
     * @time Oct 30, 2013 16:49:30 PM
     * @param customizedViewVo
     * @return
     * @throws BaseException 
     */
    void updateCustomizedViewItem(CustomizedViewVo customizedViewVo) throws BaseException;
    
    /**
     * @description Get a single CustomizedViewItem object.
     * @author John.li
     * @time Oct 30, 2013 16:56:31 PM
     * @param customizedViewItemId
     * @return
     */
    CustomizedViewItem getCustomizedViewItemById(String customizedViewItemId);
    
    /**
     * @description Get all CustomizedViewItem objects by customizedViewId.
     * @author John.li
     * @time Oct 30, 2013 16:56:31 PM
     * @param customizedViewId
     * @return
     */
    List<CustomizedViewItem> findByCustomizedViewId(String customizedViewId);
    
    /**
     * @description Get all complicate search condition from customizeView.
     * @author John.li
     * @time Dec 12, 2013 14:56:31 PM
     * @param customizedViewId
     * @return
     * @throws BaseException 
     */
    BooleanQuery getCustomizedViewItemQuery(QueryBuilder qb, String customizedViewId) throws BaseException;

    
}
