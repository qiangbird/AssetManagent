package com.augmentum.ams.service.remote;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.web.vo.asset.SiteVo;

/**
 * 
 * @author Jay.He
 * 
 */
public interface RemoteSiteService {

    /**
     * get Site info from IAP
     * 
     * @description TODO
     * @author Jay.He
     * @time Oct 23, 2013 2:05:35 PM
     * @param httpServletRequest
     * @return
     * @throws DataException
     */
    List<SiteVo> getSiteFromIAP(HttpServletRequest httpServletRequest) throws DataException;

}
