package com.augmentum.ams.service.remote.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.augmentum.ams.constants.IAPConstans;
import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.service.remote.RemoteSiteService;
import com.augmentum.ams.util.RemoteUtil;
import com.augmentum.ams.util.SqlRestrictionsUtil;
import com.augmentum.ams.web.vo.asset.SiteVo;
import com.augmentum.iaphelper.constans.DataModelAPI;
import com.augmentum.iaphelper.constans.IAPResponseType;
import com.augmentum.iaphelper.model.IAPDataResponseModel;
import com.augmentum.iaphelper.model.IAPDataSearchModel;

@Service("remoteSiteService")
public class RemoteSiteServiceImpl implements RemoteSiteService {

    private static Logger logger = Logger.getLogger(RemoteSiteServiceImpl.class);
    
    @Override
    public List<SiteVo> getSiteFromIAP(HttpServletRequest httpServletRequest) throws BusinessException {
        
        // Prepare the condition for searching.
        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        searchModel.setColumns(new String[] { IAPConstans.SITE_TYPE, IAPConstans.SITE_NAME_CN, 
        		IAPConstans.SITE_NAME_EN, IAPConstans.SITE_NAME_ABBR });
        searchModel.setFilter(SqlRestrictionsUtil.eq(IAPConstans.SITE_TYPE, IAPConstans.AUGMENTUM_SITE));
        
        logger.info("Search columns: " + Arrays.toString(searchModel.getColumns()));
        logger.info("Search filter: " + searchModel.getFilter());

        // Communicate with IAP.
        Request tmpRequest = RemoteUtil.getRequest(httpServletRequest, DataModelAPI.listSite,
        		IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = RemoteUtil.getResponse(searchModel,
                ContentType.APPLICATION_XML, tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

        // Gain the response and encapsulate them.
        responseData = responseModel.getRequestModel().getDataList();
        // change the map list into SiteVo Objects
        List<SiteVo> siteVoList = new ArrayList<SiteVo>();
        
        for (Map<String, Object> mapData : responseData) {
            SiteVo siteVo = new SiteVo();
            siteVo.setSiteNameAbbr((String) mapData.get(IAPConstans.SITE_NAME_ABBR));
            siteVo.setSiteNameEn(((String) mapData.get(IAPConstans.SITE_NAME_EN)));
            siteVoList.add(siteVo);
        }
        return siteVoList;
    }

}
