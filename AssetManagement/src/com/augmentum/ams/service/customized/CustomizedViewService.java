package com.augmentum.ams.service.customized;

import java.util.List;

import net.sf.json.JSONArray;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.customized.CustomizedView;
import com.augmentum.ams.web.vo.customized.CustomizedViewVo;

public interface CustomizedViewService {

    /**
     * @description Save the CustomizedView object.
     * @author John.li
     * @time Oct 30, 2013 16:48:34 PM
     * @param customizedViewVo
     * @return
     */
    CustomizedView saveCustomizedView(CustomizedViewVo customizedViewVo);

    /**
     * @description Delete the CustomizedView object.
     * @author John.li
     * @time Oct 30, 2013 16:48:50 PM
     * @param customizedViewVo
     * @return
     * @throws BaseException
     */
    void deleteCustomizedView(CustomizedViewVo customizedViewVo) throws BaseException;

    /**
     * @description Update the CustomizedView object.
     * @author John.li
     * @time Oct 30, 2013 16:49:30 PM
     * @param customizedViewVo
     * @return CustomizedView
     * @throws BaseException
     */
    CustomizedView updateCustomizedView(CustomizedViewVo customizedViewVo) throws BaseException;

    /**
     * @description Get a single CustomizedView object.
     * @author John.li
     * @time Oct 30, 2013 16:56:31 PM
     * @param customizedViewId
     * @return
     * @throws BaseException
     */
    CustomizedView getCustomizedViewById(String customizedViewId) throws BaseException;

    /**
     * @description Get all CustomizedView objects by user.
     * @author John.li
     * @time Oct 30, 2013 16:56:31 PM
     * @param creatorId
     * @return
     */
    List<CustomizedView> findCustomizedViewByUser(String creatorId);

    /**
     * @description Change customizedView to JsonArray.
     * @author John.li
     * @time Dec 13, 2013 18:16:31 PM
     * @param customizedViews
     * @return
     */
    JSONArray changeCustomizedViewToJson(List<CustomizedView> customizedViews);

    List<CustomizedView> findByUserAndCategoryType(String creatorId, String categoryType);

}
