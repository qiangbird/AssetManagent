package com.augmentum.ams.dao.customized;

import java.text.ParseException;
import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.customized.CustomizedView;

public interface CustomizedViewDao extends BaseDao<CustomizedView>{
    
    /**
     * @description Get all CustomizedView objects by user.
     * @author John.li
     * @time Oct 30, 2013 17:18:31 PM
     * @param userId
     * @return
     * @throws ParseException 
     */
    List<CustomizedView> findCustomizedViewsByUser(String userId) throws ParseException;

}
