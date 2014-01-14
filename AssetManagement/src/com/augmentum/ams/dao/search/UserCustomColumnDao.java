package com.augmentum.ams.dao.search;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.user.UserCustomColumn;

public interface UserCustomColumnDao extends BaseDao<UserCustomColumn>{

	/**Get current user customize column based on category(table name)
	 * UserCustomColumn includes: CustomizedColumn and PropertyTemplate
	 * 
     * @author Geoffrey.Zhao
     * @param category(table name)
     * @param userId
     * @return
     */
    List<UserCustomColumn> getUserCustomColumns(String category, String userId);
    
    /**
     * Get current user someone CustomizedColumn
     * 
     * @author Geoffrey.Zhao
     * @param userCustomColumnId
     * @param userId
     * @return
     */
    UserCustomColumn getUserCustomColumn(String userCustomColumnId);
    
    /**
     * get user customized column count to judge if the user login first time
     * 
     * @param userId
     * @return
     */
    int getUserCustomColumnsCount(String userId);
    
}
