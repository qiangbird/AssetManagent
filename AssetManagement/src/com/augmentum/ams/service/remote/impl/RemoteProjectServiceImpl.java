package com.augmentum.ams.service.remote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.augmentum.ams.common.constants.IAPConstans;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.service.remote.RemoteProjectService;
import com.augmentum.ams.util.RemoteUtil;
import com.augmentum.ams.util.SqlRestrictionsUtil;
import com.augmentum.ams.web.vo.asset.ProjectVo;
import com.augmentum.iaphelper.constans.DataModelAPI;
import com.augmentum.iaphelper.constans.IAPResponseType;
import com.augmentum.iaphelper.model.IAPDataResponseModel;
import com.augmentum.iaphelper.model.IAPDataSearchModel;

@Service("remoteProjectService")
public class RemoteProjectServiceImpl implements RemoteProjectService {
    private static Logger logger = Logger.getLogger(RemoteProjectServiceImpl.class);

    @Override
    public String getManagerIdByProjectCode(String projectCode,
            HttpServletRequest httpServletRequest) throws DataException {
        // Prepare the condition for searching.
        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        searchModel.setColumns(new String[] { IAPConstans.MANAGER_MANAGER_ID });
        searchModel.setFilter(SqlRestrictionsUtil.eq(IAPConstans.PROJECT_CODE, projectCode));

        // Communicate with IAP.
        Request tmpRequest = RemoteUtil.getRequest(httpServletRequest, DataModelAPI.listProject,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = RemoteUtil.getResponse(searchModel,
                ContentType.APPLICATION_XML, tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

        // Gain the response and encapsulate them.
        if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            responseData = responseModel.getRequestModel().getDataList();
        } else {
            logger.info(responseModel.getStatus().getMessage());
        }

        String projectManagerId = null;

        for (Map<String, Object> mapData : responseData) {
            projectManagerId = (String) mapData.get(IAPConstans.MANAGER_MANAGER_ID);
        }
        return projectManagerId;
    }

    @Override
    public List<ProjectVo> getProjectByEmployeeId(String employeeId, HttpServletRequest request)
            throws DataException {
        // Prepare the condition for searching.
        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        // searchModel.setColumns(new String[] {"projectName","projectCode"});
        searchModel.setFilter(SqlRestrictionsUtil.eq(IAPConstans.EMPLOYEE_EMPLOYEE_ID, employeeId));

        // Communicate with IAP.
        Request tmpRequest = RemoteUtil.getRequest(request, "dataModel_listEmployeeProject",
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = RemoteUtil.getResponse(searchModel,
                ContentType.APPLICATION_XML, tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

        // Gain the response and encapsulate them.
        if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            responseData = responseModel.getRequestModel().getDataList();
        } else {
            logger.info(responseModel.getStatus().getMessage());
        }
        List<ProjectVo> projectVoList = new ArrayList<ProjectVo>();

        for (Map<String, Object> mapData : responseData) {

            ProjectVo projectVo = new ProjectVo();
            projectVo.setProjectName((String) mapData.get(IAPConstans.PROJECT_NAME));
            projectVo.setProjectCode((String) mapData.get(IAPConstans.PROJECT_CODE));
            projectVoList.add(projectVo);
        }
        return projectVoList;
    }

    @Override
    public ProjectVo getProjectByProjectCode(String projectCode, HttpServletRequest request)
            throws DataException {
        // Prepare the condition for searching.
        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        searchModel.setColumns(new String[] { IAPConstans.PROJECT_NAME, IAPConstans.PROJECT_CODE });
        searchModel.setFilter(SqlRestrictionsUtil.eq(IAPConstans.PROJECT_CODE, projectCode));

        // Communicate with IAP.
        Request tmpRequest = RemoteUtil.getRequest(request, DataModelAPI.listProject,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = RemoteUtil.getResponse(searchModel,
                ContentType.APPLICATION_XML, tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

        // Gain the response and encapsulate them.
        if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            responseData = responseModel.getRequestModel().getDataList();
        } else {
            logger.info(responseModel.getStatus().getMessage());
        }
        ProjectVo projectVo = new ProjectVo();
        for (Map<String, Object> mapData : responseData) {

            projectVo.setProjectName((String) mapData.get(IAPConstans.PROJECT_NAME));
            projectVo.setProjectCode((String) mapData.get(IAPConstans.PROJECT_CODE));
        }
        return projectVo;
    }
    
    @Override
    public Map<String, String> findAllProjectsFromIAP(HttpServletRequest request) throws DataException{
        
     // Prepare the condition for searching.
        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        searchModel.setColumns(new String[] { IAPConstans.PROJECT_NAME, IAPConstans.PROJECT_CODE });

        // Communicate with IAP.
        Request tmpRequest = RemoteUtil.getRequest(request, DataModelAPI.listProject,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = RemoteUtil.getResponse(searchModel,
                ContentType.APPLICATION_XML, tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

        // Gain the response and encapsulate them.
        if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            responseData = responseModel.getRequestModel().getDataList();
        } else {
            logger.info(responseModel.getStatus().getMessage());
        }
        Map<String, String> projects = new HashMap<String, String>();
        
        for (Map<String, Object> mapData : responseData) {

            projects.put((String) mapData.get(IAPConstans.PROJECT_NAME),
                    (String) mapData.get(IAPConstans.PROJECT_CODE));
        }
        return projects;
    }

}
