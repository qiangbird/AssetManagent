package com.augmentum.ams.service.search;

import java.util.List;
import java.util.Map;

import com.augmentum.ams.model.user.UserCustomColumn;

public interface UserCustomColumnsService {

	/**
	 * Get current user customize column based on category(table name)
	 * UserCustomColumn includes: CustomizedColumn and PropertyTemplate
	 * 
	 * @author Geoffrey.Zhao
	 * @param category
	 * @param userId
	 * @return
	 */
	List<UserCustomColumn> findUserCustomColumns(String category, String userId);
	
	/**
     * Update current user customized column information
     * 
     * @author Geoffrey.Zhao
     * @param Map<String, Boolean>, key is customizedColumnId, value is if showDefault
     * @param userId
     */
	void updateCustomizedColumns(Map<String, Boolean> customizedColumnIds, String userId);
	
	/**
	 * When user login at first time, according to current category and userId check user_custom_column.
     * if it's null, copy custom_column into user_custom_column.
     * 
	 * @author Geoffrey.Zhao
	 * @param userId
	 */
	void initUserCustomColumn(String userId);
		
}
