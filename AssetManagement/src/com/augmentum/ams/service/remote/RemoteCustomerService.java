package com.augmentum.ams.service.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ProjectVo;

public interface RemoteCustomerService {

    /**
     * Get customer codes by employeeId, the employeeId uses in
     * managerEmployeeId or directorEmployeeId
     * 
     * @param employeeId
     * @param httpServletRequest
     * @return List<Customer>
     * @throws BusinessException
     */
    List<Customer> getCustomerCodesByEmployeeId(String employeeId,
            HttpServletRequest httpServletRequest) throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:08 AM
     * @param customerCodes
     * @param httpServletRequest
     * @return
     * @throws BusinessException
     */
    String[] getProjectManagerIdByCustomerCodes(String customerCodes,
            HttpServletRequest httpServletRequest) throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:11 AM
     * @param customerName
     * @param httpServletRequest
     * @return
     * @throws BusinessException
     */
    String[] getProjectManagerIdByCustomerName(String customerName,
            HttpServletRequest httpServletRequest) throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:15 AM
     * @param httpServletRequest
     * @return
     * @throws BusinessException
     */
    List<CustomerVo> getAllCustomerFromIAP(HttpServletRequest httpServletRequest)
            throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:18 AM
     * @param httpServletRequest
     * @param customerName
     * @return
     * @throws BusinessException
     */
    Customer getCustomerByNamefromIAP(HttpServletRequest httpServletRequest, String customerName)
            throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:22 AM
     * @param httpServletRequest
     * @param customerName
     * @return
     * @throws BusinessException
     */
    Customer getCustomerByCodefromIAP(HttpServletRequest httpServletRequest, String customerCode)
            throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:25 AM
     * @param httpServletRequest
     * @param customerName
     * @return
     * @throws BusinessException
     */
    String getCustomerIdByNamefromIAP(HttpServletRequest httpServletRequest, String customerName)
            throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:30 AM
     * @param customerCode
     * @param httpServletRequest
     * @return
     * @throws BusinessException
     */
    List<ProjectVo> getProjectByCustomerCode(String customerCode,
            HttpServletRequest httpServletRequest) throws BusinessException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:35 AM
     * @param userId
     * @param request
     * @return
     * @throws BusinessException
     */
    List<CustomerVo> getCustomerByEmployeeId(String userId, HttpServletRequest request)
            throws BusinessException;
    
    List<CustomerVo> getCustomerForEmployeeByEmployeeId(String userId, HttpServletRequest request)
            throws BusinessException;
    
    List<CustomerVo> getCustomersFromIAP(HttpServletRequest httpServletRequest)
            throws BusinessException;
}
