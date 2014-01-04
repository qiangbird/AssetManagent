package com.augmentum.ams.service.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.DataException;

public interface RemoteEntityService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:05:36 AM
     * @param request
     * @return
     * @throws DataException
     */
    List<String> getAllEntityFromIAP(HttpServletRequest request) throws DataException;
}
