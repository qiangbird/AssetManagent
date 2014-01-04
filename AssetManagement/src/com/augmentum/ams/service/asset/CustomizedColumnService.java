package com.augmentum.ams.service.asset;

import java.util.List;

import com.augmentum.ams.model.asset.CustomizedColumn;

public interface CustomizedColumnService {

    /**
     * Get default columns, if userCustomColumns is null, it will be used.
     * @author Geoffrey.Zhao
     * @param category(which table)
     * @return
     */
    List<CustomizedColumn> findDefaultCustomizedColumns(String category);
    
    /**
     * @author John.Li
     * @time Nov 6, 2013 19:16:20 PM
     * @description Get the value of columns by realName and realTable.
     * @param realName, realTable
     * @return
     */
    List<String> getValueOfColumn(String realName, String realTable);
}
