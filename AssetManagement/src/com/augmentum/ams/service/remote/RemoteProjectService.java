package com.augmentum.ams.service.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.web.vo.asset.ProjectVo;

public interface RemoteProjectService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 9, 2013 5:08:43 PM
     * @param projectCode
     * @param httpServletRequest
     * @return
     * @throws DataException
     */
    public String getManagerIdByProjectCode(String projectCode,
            HttpServletRequest httpServletRequest) throws DataException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:05:25 AM
     * @param employeeId
     * @param request
     * @return
     * @throws DataException
     */
    public List<ProjectVo> getProjectByEmployeeId(String employeeId, HttpServletRequest request)
            throws DataException;
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 25, 2013 10:11:15 AM
     * @param projectCode
     * @param request
     * @return
     * @throws DataException
     */
    public ProjectVo getProjectByProjectCode(String projectCode, HttpServletRequest request) throws DataException;
}
