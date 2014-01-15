package com.augmentum.ams.service.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.DataException;
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
     * @throws DataException
     */
    List<Customer> getCustomerCodesByEmployeeId(String employeeId,
            HttpServletRequest httpServletRequest) throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:08 AM
     * @param customerCodes
     * @param httpServletRequest
     * @return
     * @throws DataException
     */
    String[] getProjectManagerIdByCustomerCodes(String customerCodes,
            HttpServletRequest httpServletRequest) throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:11 AM
     * @param customerName
     * @param httpServletRequest
     * @return
     * @throws DataException
     */
    String[] getProjectManagerIdByCustomerName(String customerName,
            HttpServletRequest httpServletRequest) throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:15 AM
     * @param httpServletRequest
     * @return
     * @throws DataException
     */
    List<CustomerVo> getAllCustomerFromIAP(HttpServletRequest httpServletRequest)
            throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:18 AM
     * @param httpServletRequest
     * @param customerName
     * @return
     * @throws DataException
     */
    Customer getCustomerByNamefromIAP(HttpServletRequest httpServletRequest, String customerName)
            throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:22 AM
     * @param httpServletRequest
     * @param customerName
     * @return
     * @throws DataException
     */
    Customer getCustomerByCodefromIAP(HttpServletRequest httpServletRequest, String customerCode)
            throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:25 AM
     * @param httpServletRequest
     * @param customerName
     * @return
     * @throws DataException
     */
    String getCustomerIdByNamefromIAP(HttpServletRequest httpServletRequest, String customerName)
            throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:30 AM
     * @param customerCode
     * @param httpServletRequest
     * @return
     * @throws DataException
     */
    List<ProjectVo> getProjectByCustomerCode(String customerCode,
            HttpServletRequest httpServletRequest) throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:06:35 AM
     * @param userId
     * @param request
     * @return
     * @throws DataException
     */
    List<CustomerVo> getCustomerByEmployeeId(String userId, HttpServletRequest request)
            throws DataException;
}
