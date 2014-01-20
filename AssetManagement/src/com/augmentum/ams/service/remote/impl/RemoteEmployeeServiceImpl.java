package com.augmentum.ams.service.remote.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.augmentum.ams.common.constants.IAPConstans;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.util.RemoteUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.SqlRestrictionsUtil;
import com.augmentum.ams.web.vo.user.UserVo;
import com.augmentum.iaphelper.constans.DataModelAPI;
import com.augmentum.iaphelper.constans.IAPResponseType;
import com.augmentum.iaphelper.model.IAPDataResponseModel;
import com.augmentum.iaphelper.model.IAPDataSearchModel;
import com.augmentum.iaphelper.model.Sorter;
import com.augmentum.iaphelper.model.Sorter.SorterType;
import com.augmentum.iaphelper.util.IAPDataModelResponseUtil;
import com.augmentum.iaphelper.util.RequestHelper;

@Service("remoteEmployeeService")
public class RemoteEmployeeServiceImpl implements RemoteEmployeeService {

    private static Logger logger = Logger.getLogger(RemoteEmployeeServiceImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.remote.RemoteEmployeeService#getRemoteEmployees
     * (javax.servlet.http.HttpServletRequest)
     */
    @Override
    public JSONArray findRemoteEmployees(HttpServletRequest request) throws DataException {

        Request clientRequest = RemoteUtil.getRequest(request, DataModelAPI.listEmployee,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_JSON);

        IAPDataSearchModel model = new IAPDataSearchModel();

        model.setColumns(new String[] { IAPConstans.EMPLOYEE_NAME,
                IAPConstans.EMPLOYEE_EMPLOYEE_ID, IAPConstans.POSITION_LEVEL,
                IAPConstans.EMPLOYEE_POSITION_CODE });
        List<Sorter> sorters = new ArrayList<Sorter>();
        // Sort employee by employeeName
        Sorter sorter = new Sorter(IAPConstans.EMPLOYEE_NAME, SorterType.ASC);
        sorters.add(sorter);
        model.setSorters(sorters);

        IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
                ContentType.APPLICATION_XML, clientRequest);
        logger.info("get employeeName and employeeEmployeeId from IAP");

        List<Map<String, Object>> employeeeList = responseModel.getRequestModel().getDataList();
        JSONArray array = new JSONArray();

        // Encapsulated employee info in json array
        for (int i = 0; i < employeeeList.size(); i++) {
            JSONObject map = new JSONObject();
            String employeeName = (String) employeeeList.get(i).get(IAPConstans.EMPLOYEE_NAME);
            String employeeEmployeeId = (String) employeeeList.get(i).get(
                    IAPConstans.EMPLOYEE_EMPLOYEE_ID);
            String positionLevel = employeeeList.get(i).get(IAPConstans.POSITION_LEVEL) + "";
            String employeePositionCode = (String) employeeeList.get(i).get(
                    IAPConstans.EMPLOYEE_POSITION_CODE);
            map.put("label", employeeName);
            map.put("value", employeeName + "#" + employeeEmployeeId);
            map.put("positionLevel", positionLevel);
            map.put("employeePositionCode", employeePositionCode);
            array.add(map);
        }

        return array;
    }
    
    @Override
    public Map<String, String> findRemoteEmployeesForCache(HttpServletRequest request) throws DataException {

        Request clientRequest = RemoteUtil.getRequest(request, DataModelAPI.listEmployee,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_JSON);

        IAPDataSearchModel model = new IAPDataSearchModel();

        model.setColumns(new String[] { IAPConstans.EMPLOYEE_NAME,
                IAPConstans.EMPLOYEE_EMPLOYEE_ID, IAPConstans.POSITION_LEVEL,
                IAPConstans.EMPLOYEE_POSITION_CODE });
        List<Sorter> sorters = new ArrayList<Sorter>();
        // Sort employee by employeeName
        Sorter sorter = new Sorter(IAPConstans.EMPLOYEE_NAME, SorterType.ASC);
        sorters.add(sorter);
        model.setSorters(sorters);

        IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
                ContentType.APPLICATION_XML, clientRequest);
        logger.info("get employeeName and employeeEmployeeId from IAP");

        List<Map<String, Object>> employeeeList = responseModel.getRequestModel().getDataList();
        Map<String, String> employeees = new HashMap<String, String>();

        // Encapsulated employee info in json array
        for (int i = 0; i < employeeeList.size(); i++) {
            String employeeName = (String) employeeeList.get(i).get(IAPConstans.EMPLOYEE_NAME);
            String employeeEmployeeId = (String) employeeeList.get(i).get(
                    IAPConstans.EMPLOYEE_EMPLOYEE_ID);
            employeees.put(employeeName, employeeEmployeeId);
        }

        return employeees;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.augmentum.ams.service.remote.RemoteEmployeeService#getLoginUser(javax
     * .servlet.http.HttpServletRequest)
     */
    @Override
    public UserVo getLoginUser(HttpServletRequest request) throws DataException {

        String responseBody = null;
        String ticketURL = RequestHelper.getTicketURLByKey(request, DataModelAPI.getEmployeeInfo);

        Request clientRequest = null;
        try {
            clientRequest = RequestHelper.getRequest(ticketURL, DataModelAPI.getEmployeeInfo,
                    IAPConstans.ADMIN_ROLE, null);
            responseBody = RequestHelper.sendRequest(clientRequest, null, null);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<Map<String, Object>> users = IAPDataModelResponseUtil.getResponseModel(responseBody)
                .getRequestModel().getDataList();

        UserVo userVo = new UserVo();
        if (users.size() > 0) {
            Map<String, Object> userMap = users.get(0);
            userVo.setEmployeeId((String) userMap.get(IAPConstans.EMPLOYEE_EMPLOYEE_ID));
            userVo.setEmployeeName((String) userMap.get(IAPConstans.EMPLOYEE_NAME));
            userVo.setEmployeeLevel((String) userMap.get(IAPConstans.EMPLOYEE_LEVEL));
            // change employee role after login
           
            if (RoleLevelUtil.getRoleByUserVo(userVo).toString().equalsIgnoreCase("manager")) {
                userVo.setEmployeeLevel("Manager");
            } else {
                userVo.setEmployeeLevel("Employee");
            }
             

        }
        return userVo;
    }

    @Override
    public UserVo getRemoteUserById(String userId, HttpServletRequest request) throws DataException {

        IAPDataSearchModel model = new IAPDataSearchModel();
        model.setColumns(new String[] { IAPConstans.EMPLOYEE_NAME, IAPConstans.POSITION_NAME_EN,
                IAPConstans.POSITION_NAME_CN, IAPConstans.DEPARTMENT_NAME_EN,
                IAPConstans.DEPARTMENT_NAME_CN, IAPConstans.MANAGER_NAME,
                IAPConstans.EMPLOYEE_LEVEL });
        model.setFilter(SqlRestrictionsUtil.eq(IAPConstans.EMPLOYEE_EMPLOYEE_ID, userId));
        Request clientRequest = RemoteUtil.getRequest(request, DataModelAPI.listEmployee,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);

        IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
                ContentType.APPLICATION_XML, clientRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
        if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            responseData = responseModel.getRequestModel().getDataList();
        } else {
            logger.info(responseModel.getStatus().getMessage());
        }
        UserVo user = new UserVo();
        for (Map<String, Object> mapData : responseData) {
            user.setEmployeeId(userId);
            user.setEmployeeName((String) mapData.get(IAPConstans.EMPLOYEE_NAME));
            user.setPositionNameEn((String) mapData.get(IAPConstans.POSITION_NAME_EN));
            user.setPositionNameCn((String) mapData.get(IAPConstans.POSITION_NAME_CN));
            user.setDepartmentNameEn((String) mapData.get(IAPConstans.DEPARTMENT_NAME_EN));
            user.setDepartmentNameCn((String) mapData.get(IAPConstans.DEPARTMENT_NAME_CN));
            user.setManagerName((String) mapData.get(IAPConstans.MANAGER_NAME));
            user.setEmployeeLevel((String) mapData.get(IAPConstans.EMPLOYEE_LEVEL));

        }
        return user;
    }

    @Override
    public UserVo getRemoteUserByName(String userName, HttpServletRequest request)
            throws DataException {

        IAPDataSearchModel searchModel = new IAPDataSearchModel();
        searchModel.setColumns(new String[] { IAPConstans.EMPLOYEE_NAME,
                IAPConstans.EMPLOYEE_EMPLOYEE_ID, IAPConstans.POSITION_NAME_EN,
                IAPConstans.POSITION_NAME_CN, IAPConstans.DEPARTMENT_NAME_EN,
                IAPConstans.DEPARTMENT_NAME_CN, IAPConstans.MANAGER_NAME });
        searchModel.setFilter(SqlRestrictionsUtil.eq(IAPConstans.EMPLOYEE_NAME, userName));
        Request tmpRequest = RemoteUtil.getRequest(request, DataModelAPI.listEmployee,
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_XML);
        IAPDataResponseModel responseModel = RemoteUtil.getResponse(searchModel,
                ContentType.APPLICATION_XML, tmpRequest);
        List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
        if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
            responseData = responseModel.getRequestModel().getDataList();
        } else {
            logger.info(responseModel.getStatus().getMessage());
        }
        UserVo user = new UserVo();
        for (Map<String, Object> mapData : responseData) {
            user.setEmployeeId((String) mapData.get(IAPConstans.EMPLOYEE_EMPLOYEE_ID));
            user.setEmployeeName((String) mapData.get(IAPConstans.EMPLOYEE_NAME));
            user.setPositionNameEn((String) mapData.get(IAPConstans.POSITION_NAME_EN));
            user.setPositionNameCn((String) mapData.get(IAPConstans.POSITION_NAME_CN));
            user.setDepartmentNameEn((String) mapData.get(IAPConstans.DEPARTMENT_NAME_EN));
            user.setDepartmentNameCn((String) mapData.get(IAPConstans.DEPARTMENT_NAME_CN));
            user.setManagerName((String) mapData.get(IAPConstans.MANAGER_NAME));
        }
        return user;
    }

    @Override
    public JSONArray findRemoteEmployeeByCustomerCode(String customerCode,
            HttpServletRequest request) throws DataException {
        Request clientRequest = RemoteUtil.getRequest(request, "dataModel_listEmployeeProject",
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_JSON);

        IAPDataSearchModel model = new IAPDataSearchModel();

        // model.setColumns(new String[] { "employeeName", "employeeEmployeeId"
        // });
        model
                .setColumns(new String[] { IAPConstans.EMPLOYEE_NAME,
                        IAPConstans.EMPLOYEE_EMPLOYEE_ID });
        model.setFilter(SqlRestrictionsUtil.eq(IAPConstans.PROJECT_CUSTOMER_CODE, customerCode));
        List<Sorter> sorters = new ArrayList<Sorter>();

        // Sort employee by employeeName
        Sorter sorter = new Sorter(IAPConstans.EMPLOYEE_NAME, SorterType.ASC);
        sorters.add(sorter);
        model.setSorters(sorters);

        IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
                ContentType.APPLICATION_XML, clientRequest);
        logger.info("get employeeName and employeeEmployeeId from IAP");

        List<Map<String, Object>> employeeeList = responseModel.getRequestModel().getDataList();
        JSONArray array = new JSONArray();

        // Encapsulated employee info in json array
        for (int i = 0; i < employeeeList.size(); i++) {
            JSONObject map = new JSONObject();
            String employeeName = (String) employeeeList.get(i).get(IAPConstans.EMPLOYEE_NAME);
            String employeeEmployeeId = (String) employeeeList.get(i).get(
                    IAPConstans.EMPLOYEE_EMPLOYEE_ID);
            map.put("label", employeeName);
            map.put("value", employeeName + "#" + employeeEmployeeId);
            map.put("employeeCode", employeeEmployeeId);
            array.add(map);
        }
        return array;
    }

    @Override
    public JSONArray findRemoteEmployeeByProjectCode(String projectCode, HttpServletRequest request)
            throws DataException {
        Request clientRequest = RemoteUtil.getRequest(request, "dataModel_listEmployeeProject",
                IAPConstans.ADMIN_ROLE, IAPResponseType.APPLICATION_JSON);

        IAPDataSearchModel model = new IAPDataSearchModel();

        // model.setColumns(new String[] { "employeeName", "employeeEmployeeId"
        // });
        model.setColumns(new String[] { IAPConstans.EMPLOYEE_NAME, IAPConstans.EMPLOYEE_EMPLOYEE_ID });
        model.setFilter(SqlRestrictionsUtil.eq(IAPConstans.PROJECT_CODE, projectCode));
        List<Sorter> sorters = new ArrayList<Sorter>();

        // Sort employee by employeeName
        Sorter sorter = new Sorter(IAPConstans.EMPLOYEE_NAME, SorterType.ASC);
        sorters.add(sorter);
        model.setSorters(sorters);

        IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
                ContentType.APPLICATION_XML, clientRequest);
        logger.info("get employeeName and employeeEmployeeId from IAP");

        List<Map<String, Object>> employeeeList = responseModel.getRequestModel().getDataList();
        JSONArray array = new JSONArray();

        // Encapsulated employee info in json array
        for (int i = 0; i < employeeeList.size(); i++) {
            JSONObject map = new JSONObject();
            String employeeName = (String) employeeeList.get(i).get(IAPConstans.EMPLOYEE_NAME);
            String employeeEmployeeId = (String) employeeeList.get(i).get(
                    IAPConstans.EMPLOYEE_EMPLOYEE_ID);
            map.put("label", employeeName);
            map.put("value", employeeName + "#" + employeeEmployeeId);
            array.add(map);
        }
        return array;
    }

}
