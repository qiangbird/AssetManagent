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
import com.augmentum.ams.service.remote.RemoteEntityService;
import com.augmentum.ams.util.RemoteUtil;
import com.augmentum.iaphelper.constans.DataModelAPI;
import com.augmentum.iaphelper.constans.IAPResponseType;
import com.augmentum.iaphelper.model.IAPDataResponseModel;
import com.augmentum.iaphelper.model.IAPDataSearchModel;

@Service("remoteEntityService")
public class RemoteEntityServiceImpl implements RemoteEntityService {
    private static Logger logger = Logger.getLogger(RemoteEntityServiceImpl.class);

    @Override
    public List<String> getAllEntityFromIAP(HttpServletRequest request) {
        
        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        searchModel.setColumns(new String[] { IAPConstans.COMPANY_ENTITY_SHORT_NAME_CN, IAPConstans.COMPANY_ENTITY_CODE });
        
        logger.info("Search columns: " + Arrays.toString(searchModel.getColumns()));
        
        Request tmpRequest = RemoteUtil.getRequest(request, DataModelAPI.listCompanyEntity,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = null;
        responseModel = RemoteUtil.getResponse(searchModel, ContentType.APPLICATION_XML,
                    tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
        responseData = responseModel.getRequestModel().getDataList();
        List<String> list = new ArrayList<String>();
        
        for (Map<String, Object> mapData : responseData) {
            list.add((String) mapData.get(IAPConstans.COMPANY_ENTITY_SHORT_NAME_CN));
        }

        return list;
    }

}
