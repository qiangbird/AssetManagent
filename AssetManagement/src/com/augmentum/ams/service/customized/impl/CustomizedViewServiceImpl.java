package com.augmentum.ams.service.customized.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.dao.customized.CustomizedViewDao;
import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedView;
import com.augmentum.ams.service.customized.CustomizedViewService;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

@Service("customizedViewService")
public class CustomizedViewServiceImpl implements CustomizedViewService {

    @Autowired
    private CustomizedViewDao customizedViewDao;

    @Override
    public CustomizedView saveCustomizedView(CustomizedViewVo customizedViewVo) {

        CustomizedView customizedView = new CustomizedView();

        customizedView.setCreatorId(customizedViewVo.getCreatorId());
        customizedView.setCreatorName(customizedViewVo.getCreatorName());
        customizedView.setOperators(customizedViewVo.getOperator());
        customizedView.setViewName(customizedViewVo.getViewName());
        customizedView.setCategoryType(customizedViewVo.getCategoryType());

        customizedViewDao.save(customizedView);

        return customizedView;
    }

    @Override
    public void deleteCustomizedView(CustomizedViewVo customizedViewVo) throws BaseException {

        CustomizedView customizedView = this.getCustomizedViewById(customizedViewVo
                .getCustomizedViewId());
        customizedViewDao.delete(customizedView);
    }

    @Override
    public CustomizedView updateCustomizedView(CustomizedViewVo customizedViewVo)
            throws BaseException {

        CustomizedView customizedView = this.getCustomizedViewById(customizedViewVo
                .getCustomizedViewId());

        customizedView.setOperators(customizedViewVo.getOperator());
        customizedView.setViewName(customizedViewVo.getViewName());

        customizedViewDao.update(customizedView);

        return customizedView;
    }

    @Override
    public CustomizedView getCustomizedViewById(String customizedViewId) throws BaseException {

        CustomizedView customizedView = customizedViewDao.get(CustomizedView.class,
                customizedViewId);

        return customizedView;
    }

    @Override
    public List<CustomizedView> findCustomizedViewByUser(String creatorId) {

        return customizedViewDao.findCustomizedViewsByUser(creatorId);
    }

    @Override
    public JSONArray changeCustomizedViewToJson(List<CustomizedView> customizedViews) {

        JSONArray array = new JSONArray();

        for (int i = 0; i < customizedViews.size(); i++) {
            JSONObject object = new JSONObject();
            object.put("customizedViewId", customizedViews.get(i).getId());
            object.put("viewName", customizedViews.get(i).getViewName());
            array.add(object);
        }
        return array;
    }

    @Override
    public List<CustomizedView> findByUserAndCategoryType(String creatorId, String categoryType) {

        return customizedViewDao.findByUserAndCategoryType(creatorId, categoryType);
    }

}
