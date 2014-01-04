package com.augmentum.ams.dao.asset;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.asset.CustomizedColumn;

public interface CustomizedColumnDao extends BaseDao<CustomizedColumn>{

    /**
     * @author Grylls.Xu
     * @time Oct 21, 2013 9:55:20 AM
     * @description Get default columns by category(which table).
     * @param category
     * @return
     */
    List<CustomizedColumn> findDefaultColumns(String category);
    
    /**
     * @author John.Li
     * @time Nov 6, 2013 19:16:20 PM
     * @description Get the value of columns by realName and realTable.
     * @param realName, realTable
     * @return
     */
    List<String> getValueOfColumn(String realName, String realTable);
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    List<CustomizedColumn> findAllColumns();

}
