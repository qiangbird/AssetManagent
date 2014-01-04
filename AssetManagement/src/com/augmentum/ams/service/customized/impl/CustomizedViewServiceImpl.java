package com.augmentum.ams.service.customized.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.CustomizedViewDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedView;
import com.augmentum.ams.service.customized.CustomizedViewService;
import com.augmentum.ams.util.LogHelper;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

@Service("customizedViewService")
public class CustomizedViewServiceImpl implements CustomizedViewService{
	
    private Logger logger = Logger.getLogger(CustomizedViewServiceImpl.class);
    
	@Autowired
	private CustomizedViewDao customizedViewDao;

    @Override
    public CustomizedView saveCustomizedView(CustomizedViewVo customizedViewVo) {

        logger.info(LogHelper.getLogInfo("Save customized view start"));
        
        CustomizedView customizedView = new CustomizedView();
        
        customizedView.setCreatorId(customizedViewVo.getCreatorId());
        customizedView.setCreatorName(customizedViewVo.getCreatorName());
        customizedView.setOperators(customizedViewVo.getOperator());
        customizedView.setViewName(customizedViewVo.getViewName());
        
        customizedViewDao.save(customizedView);
        
        logger.info(LogHelper.getLogInfo("Save customized view end"));
        
        return customizedView;
    }

    @Override
    public void deleteCustomizedView(CustomizedViewVo customizedViewVo) throws BaseException {

        logger.info(LogHelper.getLogInfo("Save customized view start"));
        
        CustomizedView customizedView = this.getCustomizedViewById(customizedViewVo.getCustomizedViewId());
        customizedViewDao.delete(customizedView);
        
        logger.info(LogHelper.getLogInfo("Save customized view end"));
    }

    @Override
    public CustomizedView updateCustomizedView(CustomizedViewVo customizedViewVo) throws BaseException {

        logger.info(LogHelper.getLogInfo("Update customized view start"));
        
        CustomizedView customizedView = this.getCustomizedViewById(customizedViewVo.getCustomizedViewId());
        
        customizedView.setOperators(customizedViewVo.getOperator());
        customizedView.setViewName(customizedViewVo.getViewName());
        
        customizedViewDao.update(customizedView);
        
        logger.info(LogHelper.getLogInfo("Update customized view end"));
        
        return customizedView;
    }

    @Override
    public CustomizedView getCustomizedViewById(String customizedViewId) throws BaseException {

        logger.info(LogHelper.getLogInfo("Get customized view by id start"));
        
        CustomizedView customizedView = customizedViewDao.get(CustomizedView.class, customizedViewId);

        logger.info(LogHelper.getLogInfo("Get customized view by id end"));
        
        return customizedView;
    }

    @Override
    public List<CustomizedView> findCustomizedViewByUser(String creatorId) {

        logger.info(LogHelper.getLogInfo("Find customized view by user start"));
        
        List<CustomizedView> customizedViews = new ArrayList<CustomizedView>();
        try {
			customizedViews = customizedViewDao.findCustomizedViewsByUser(creatorId);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //TODO if customizedViews = null
        logger.info(LogHelper.getLogInfo("Find customized view by user end"));
        return customizedViews;
    }
    
    @Override
    public JSONArray changeCustomizedViewToJson(List<CustomizedView> customizedViews){
        
        JSONArray array = new JSONArray();
        
        for(int i = 0; i < customizedViews.size(); i++){
            JSONObject object = new JSONObject();
            object.put("customizedViewId", customizedViews.get(i).getId());
            object.put("viewName", customizedViews.get(i).getViewName());
            array.add(object);
        }
        
        return array;
    }

}
