package com.augmentum.ams.service.remote.impl;

import java.util.ArrayList;
import com.augmentum.ams.common.constants.IAPConstans;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.Project;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.util.RemoteUtil;
import com.augmentum.ams.util.RoleLevelUtil;
import com.augmentum.ams.util.SqlRestrictionsUtil;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ProjectVo;
import com.augmentum.ams.web.vo.user.UserVo;
import com.augmentum.iaphelper.constans.DataModelAPI;
import com.augmentum.iaphelper.constans.IAPResponseType;
import com.augmentum.iaphelper.model.IAPDataResponseModel;
import com.augmentum.iaphelper.model.IAPDataSearchModel;

@Service("remoteCustomerService")
public class RemoteCustomerServiceImpl implements RemoteCustomerService {

	private static Logger logger = Logger
			.getLogger(RemoteCustomerServiceImpl.class);
	@Autowired
	private CustomerService customerService;

	@Autowired
	private RemoteEmployeeService remoteEmployeeService;

	@Override
	public List<Customer> getCustomerCodesByEmployeeId(String employeeId,
			HttpServletRequest httpServletRequest) throws DataException {

		if (StringUtils.isEmpty(employeeId)) {
			String message = "employeeId is null or ''";
			logger.warn(message);
			return null;
		}
		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.CUSTOMER_NAME,
				IAPConstans.CUSTOMER_CODE });
		searchModel.setFilter(SqlRestrictionsUtil.or(SqlRestrictionsUtil.eq(
				IAPConstans.MANAGER_EMPLOYEEID, employeeId),
				SqlRestrictionsUtil.eq(IAPConstans.DIRECTOR_EMPLOYEEID,
						employeeId)));

		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}
		Set<String> customerCodes = new HashSet<String>();
		List<Customer> customers = new ArrayList<Customer>();
		for (Map<String, Object> mapData : responseData) {
			String customerCode = (String) mapData
					.get(IAPConstans.CUSTOMER_CODE);
			if (!customerCodes.contains(customerCode)) {
				String customerName = (String) mapData
						.get(IAPConstans.CUSTOMER_NAME);
				Customer customer = new Customer();
				customer.setCustomerName(customerName);
				customer.setCustomerCode(customerCode);
				customerCodes.add(customerCode);
				customers.add(customer);
			}
		}
		customerService.saveCustomer(customers);
		return customers;
	}

	@Override
	public String[] getProjectManagerIdByCustomerCodes(String customerCode,
			HttpServletRequest httpServletRequest) throws DataException {

		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.PROJECT_CODE,
				IAPConstans.MANAGER_MANAGER_ID });
		searchModel
				.setFilter(SqlRestrictionsUtil.and(SqlRestrictionsUtil.eq(
						IAPConstans.CUSTOMER_CODE, customerCode),
						SqlRestrictionsUtil.eq(IAPConstans.PROJECTSTATUS,
								IAPConstans.INACTIVE)));

		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}

		List<Project> projectList = new ArrayList<Project>();
		StringBuffer pmids = new StringBuffer();
		for (Map<String, Object> mapData : responseData) {
			String projectCode = (String) mapData.get(IAPConstans.PROJECT_CODE);
			String managerId = (String) mapData
					.get(IAPConstans.MANAGER_MANAGER_ID);
			Project project = new Project();
			project.setProjectCode(projectCode);
			pmids.append(managerId).append(",");
			projectList.add(project);
		}
		String ids[] = pmids.toString().split(" ,");
		return ids;
	}

	@Override
	public String[] getProjectManagerIdByCustomerName(String customerName,
			HttpServletRequest httpServletRequest) throws DataException {
		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.PROJECT_CODE,
				IAPConstans.MANAGER_MANAGER_ID });
		searchModel
				.setFilter(SqlRestrictionsUtil.and(SqlRestrictionsUtil.eq(
						IAPConstans.CUSTOMER_NAME, customerName),
						SqlRestrictionsUtil.eq(IAPConstans.PROJECTSTATUS,
								IAPConstans.INACTIVE)));
		
		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}

		List<Project> projectList = new ArrayList<Project>();

		StringBuffer pmids = new StringBuffer();
		for (Map<String, Object> mapData : responseData) {
			String projectCode = (String) mapData.get(IAPConstans.PROJECT_CODE);
			String managerId = (String) mapData
					.get(IAPConstans.MANAGER_MANAGER_ID);
			Project project = new Project();
			project.setProjectCode(projectCode);
			pmids.append(managerId).append(",");
			projectList.add(project);
		}
		String ids[] = pmids.toString().split(" ,");
		return ids;
	}

	@Override
	public List<CustomerVo> getAllCustomerFromIAP(
			HttpServletRequest httpServletRequest) throws DataException {

		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.CUSTOMER_CODE,
				IAPConstans.CUSTOMER_NAME });
		searchModel.setFilter(SqlRestrictionsUtil.ne(IAPConstans.PROJECTSTATUS,
				IAPConstans.INACTIVE));

		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}

		List<CustomerVo> customerList = new ArrayList<CustomerVo>();
		Set<CustomerVo> customerSet = new HashSet<CustomerVo>();
		for (Map<String, Object> mapData : responseData) {
			String custCode = (String) mapData.get(IAPConstans.CUSTOMER_CODE);
			String custName = (String) mapData.get(IAPConstans.CUSTOMER_NAME);
			CustomerVo cust = new CustomerVo();
			cust.setCustomerCode(custCode);
			cust.setCustomerName(custName);
			customerSet.add(cust);
		}
		for (CustomerVo cust : customerSet) {
			customerList.add(cust);
		}
		return customerList;
	}

	@Override
	public Customer getCustomerByNamefromIAP(
			HttpServletRequest httpServletRequest, String customerName)
			throws DataException {
		Customer cust = new Customer();
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.ID,
				IAPConstans.CUSTOMER_NAME, IAPConstans.CUSTOMER_CODE });
		searchModel
				.setFilter(SqlRestrictionsUtil.and(SqlRestrictionsUtil.ne(
						"customerStatus", IAPConstans.INACTIVE),
						SqlRestrictionsUtil.eq(IAPConstans.CUSTOMER_NAME,
								customerName)));
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listCustomer, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}
		for (Map<String, Object> mapData : responseData) {
			cust.setId((String) mapData.get(IAPConstans.ID));
			cust.setCustomerName((String) mapData
					.get(IAPConstans.CUSTOMER_NAME));
			cust.setCustomerCode((String) mapData
					.get(IAPConstans.CUSTOMER_CODE));
		}
		return cust;
	}

	@Override
	public String getCustomerIdByNamefromIAP(
			HttpServletRequest httpServletRequest, String customerName)
			throws DataException {
		String customerId = "";
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.ID });
		searchModel.setFilter(SqlRestrictionsUtil.ne(
				IAPConstans.CUSTOMER_STATUS, IAPConstans.INACTIVE));
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listCustomer, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}
		for (Map<String, Object> mapData : responseData) {
			customerId = (String) mapData.get(IAPConstans.ID);
		}
		return customerId;
	}

	@Override
	public Customer getCustomerByCodefromIAP(
			HttpServletRequest httpServletRequest, String customerCode)
			throws DataException {
		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.CUSTOMER_CODE,
				IAPConstans.CUSTOMER_NAME });
		searchModel.setFilter(SqlRestrictionsUtil.eq(IAPConstans.CUSTOMER_CODE,
				customerCode));

		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}
		Customer newCustomer = new Customer();
		for (Map<String, Object> mapData : responseData) {
			String custCode = (String) mapData.get(IAPConstans.CUSTOMER_CODE);
			String custName = (String) mapData.get(IAPConstans.CUSTOMER_NAME);
			newCustomer.setCustomerCode(custCode);
			newCustomer.setCustomerName(custName);
		}
		return newCustomer;
	}

	@Override
	public List<ProjectVo> getProjectByCustomerCode(String customerCode,
			HttpServletRequest httpServletRequest) throws DataException {

		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.PROJECT_CODE,
				IAPConstans.PROJECT_NAME, IAPConstans.PROJECT_MANAGER_ID,
				IAPConstans.MANAGER_EMPLOYEE_ID });
		searchModel
				.setFilter(SqlRestrictionsUtil.and(SqlRestrictionsUtil.eq(
						IAPConstans.CUSTOMER_CODE, customerCode),
						SqlRestrictionsUtil.ne(IAPConstans.PROJECTSTATUS,
								IAPConstans.INACTIVE)));

		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}

		List<ProjectVo> projectList = new ArrayList<ProjectVo>();

		for (Map<String, Object> mapData : responseData) {
			String projectCode = (String) mapData.get(IAPConstans.PROJECT_CODE);
			String projectName = (String) mapData.get(IAPConstans.PROJECT_NAME);
			// String projectManagerId=(String)mapData.get(PROJECT_MANAGER_ID);
			String managerEmployeeId = (String) mapData
					.get(IAPConstans.MANAGER_EMPLOYEE_ID);
			String projectInfo = projectName + "#" + projectCode;
			UserVo uv = remoteEmployeeService.getRemoteUserById(
					managerEmployeeId, httpServletRequest);
			String projectManagerName = uv.getEmployeeName();

			ProjectVo project = new ProjectVo();
			project.setProjectCode(projectCode);
			project.setProjectName(projectName);
			project.setProjectManagerEmployeeId(managerEmployeeId);
			project.setProjectManagerName(projectManagerName);
			project.setProjectInfo(projectInfo);

			projectList.add(project);
		}
		return projectList;
	}

	@Override
	public List<CustomerVo> getCustomerForEmployeeByEmployeeId(String userId,
			HttpServletRequest request) throws DataException {

		IAPDataSearchModel model = new IAPDataSearchModel();
		model.setColumns(new String[] { IAPConstans.CUSTOMER_CODE,
				IAPConstans.CUSTOMER_NAME });
//		model.setFilter(SqlRestrictionsUtil.eq(
//				IAPConstans.EMPLOYEE_EMPLOYEE_ID, userId));
		model.setFilter(SqlRestrictionsUtil.and(SqlRestrictionsUtil.eq(
              IAPConstans.EMPLOYEE_EMPLOYEE_ID, userId),
                SqlRestrictionsUtil.ne(IAPConstans.CUSTOMER_STATUS,
                        IAPConstans.INACTIVE)));
		Request clientRequest = RemoteUtil.getRequest(request,
				"dataModel_listEmployeeProject", IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);

		IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
				ContentType.APPLICATION_XML, clientRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}
		List<CustomerVo> customerList = new ArrayList<CustomerVo>();
		Set<CustomerVo> customerSet = new HashSet<CustomerVo>();
		for (Map<String, Object> mapData : responseData) {
			String custCode = (String) mapData.get(IAPConstans.CUSTOMER_CODE);
			String custName = (String) mapData.get(IAPConstans.CUSTOMER_NAME);
			CustomerVo cust = new CustomerVo();
			cust.setCustomerCode(custCode);
			cust.setCustomerName(custName);
			customerSet.add(cust);
		}
		for (CustomerVo cust : customerSet) {
			customerList.add(cust);
		}
		return customerList;

	}

	@Override
	public List<CustomerVo> getCustomerByEmployeeId(String userId,
			HttpServletRequest request) throws DataException {

		List<CustomerVo> customerList = new ArrayList<CustomerVo>();
		Set<CustomerVo> customerSet = new HashSet<CustomerVo>();

		UserVo userVo = remoteEmployeeService
				.getRemoteUserById(userId, request);

		List<CustomerVo> custgomerVoList = getCustomerForEmployeeByEmployeeId(
				userId, request);
		customerSet.addAll(custgomerVoList);

		IAPDataSearchModel model = new IAPDataSearchModel();
		model.setColumns(new String[] { IAPConstans.CUSTOMER_CODE,
				IAPConstans.CUSTOMER_NAME });
		model.setFilter(SqlRestrictionsUtil.and(SqlRestrictionsUtil.eq(
				IAPConstans.PROJECT_DIRECTOR_ID, userVo.getId()),
				SqlRestrictionsUtil.ne(IAPConstans.CUSTOMER_STATUS,
						IAPConstans.INACTIVE)));
		Request clientRequest = RemoteUtil.getRequest(request,
				DataModelAPI.listProject, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);

		IAPDataResponseModel responseModel = RemoteUtil.getResponse(model,
				ContentType.APPLICATION_XML, clientRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}
		for (Map<String, Object> mapData : responseData) {
			String custCode = (String) mapData.get(IAPConstans.CUSTOMER_CODE);
			String custName = (String) mapData.get(IAPConstans.CUSTOMER_NAME);
			CustomerVo cust = new CustomerVo();
			cust.setCustomerCode(custCode);
			cust.setCustomerName(custName);
			customerSet.add(cust);
		}
		for (CustomerVo cust : customerSet) {
			customerList.add(cust);
		}
		return customerList;
	}

	@Override
	public List<CustomerVo> getCustomersFromIAP(
			HttpServletRequest httpServletRequest) throws DataException {

		// Prepare the condition for searching.
		IAPDataSearchModel searchModel = new IAPDataSearchModel();
		searchModel.setColumns(new String[] { IAPConstans.CUSTOMER_CODE,
				IAPConstans.CUSTOMER_NAME });
		searchModel.setFilter(SqlRestrictionsUtil.ne(
				IAPConstans.CUSTOMER_STATUS, IAPConstans.INACTIVE));

		// Communicate with IAP.
		Request tmpRequest = RemoteUtil.getRequest(httpServletRequest,
				DataModelAPI.listCustomer, IAPConstans.ADMIN_ROLE,
				IAPResponseType.APPLICATION_XML);
		IAPDataResponseModel responseModel = RemoteUtil.getResponse(
				searchModel, ContentType.APPLICATION_XML, tmpRequest);
		List<Map<String, Object>> responseData = new ArrayList<Map<String, Object>>();

		// Gain the response and encapsulate them.
		if (responseModel.getStatus().getStatusCode() == HttpStatus.SC_OK) {
			responseData = responseModel.getRequestModel().getDataList();
		} else {
			logger.info("Cannot get responseData from IAP");
		}

		List<CustomerVo> customerList = new ArrayList<CustomerVo>();
		Set<CustomerVo> customerSet = new HashSet<CustomerVo>();
		for (Map<String, Object> mapData : responseData) {
			String custCode = (String) mapData.get(IAPConstans.CUSTOMER_CODE);
			String custName = (String) mapData.get(IAPConstans.CUSTOMER_NAME);
			CustomerVo cust = new CustomerVo();
			cust.setCustomerCode(custCode);
			cust.setCustomerName(custName);
			customerSet.add(cust);
		}
		for (CustomerVo cust : customerSet) {
			customerList.add(cust);
		}
		return customerList;
	}

}