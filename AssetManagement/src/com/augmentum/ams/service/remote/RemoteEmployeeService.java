package com.augmentum.ams.service.remote;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.web.vo.user.UserVo;

/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 12:59:47 PM
 */
public interface RemoteEmployeeService {

    /**
     * @description Get all employees info from IAP, then encapsulated them in
     *              json array
     * @author Rudy.Gao
     * @time Sep 12, 2013 12:58:53 PM
     * @param request
     * @return JSONArray
     * @throws DataException
     */
    JSONArray findRemoteEmployees(HttpServletRequest request) throws DataException;
    
    /**
     * @description Get all employees info from IAP, then encapsulated them in
     *              json array
     * @author Rudy.Gao
     * @time Sep 12, 2013 12:58:53 PM
     * @param request
     * @return JSONArray
     * @throws DataException
     */
    Map<String, String> findRemoteEmployeesForCache(HttpServletRequest request) throws DataException;

    /**
     * @author Grylls.Xu
     * @time Oct 14, 2013 3:02:07 PM
     * @description TODO
     * @param request
     * @return
     * @throws DataException
     */
    UserVo getLoginUser(HttpServletRequest request) throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 18, 2013 10:57:24 AM
     * @param userId
     * @param request
     * @return
     * @throws DataException
     */
    UserVo getRemoteUserById(String userId, HttpServletRequest request) throws DataException;

    /**
     * 
     * @description TODO
     * @author John.Li
     * @time Nov 18, 2013 10:57:36 AM
     * @param userName
     * @param request
     * @return
     * @throws DataException
     */
    List<UserVo> getRemoteUserByName(List<String> userNames, HttpServletRequest request) throws DataException;

    /**
     * 
     * @description TODO
     * @author John.li
     * @time Dec 9, 2013 09:37:36 AM
     * @param customerCode
     * @param request
     * @return
     * @throws DataException
     */
    JSONArray findRemoteEmployeeByCustomerCode(String customerCode, HttpServletRequest request)
            throws DataException;
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 18, 2013 2:57:34 PM
     * @param projectCode
     * @param request
     * @return
     * @throws DataException 
     */
    JSONArray findRemoteEmployeeByProjectCode(String projectCode, HttpServletRequest request) throws DataException;

}
