package com.augmentum.ams.service.asset;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

public interface CustomerAssetService {
    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:03:05 AM
     * @param customerCode
     * @return
     */
    List<Asset> findAssetsByCustomerCode(String customerCode);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 11, 2013 11:03:09 AM
     * @param page
     * @param status
     * @param type
     * @param fromTime
     * @param toTime
     * @param customerId
     * @return
     */
    Page<Asset> findCustomerAssetsBySearchCondition(
            SearchCondition searchCondition, String[] customerIds);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 26, 2013 1:36:20 PM
     * @param userVo
     * @param list
     * @return
     */
    List<Customer> findVisibleCustomerList(UserVo userVo, List<CustomerVo> list);

    /**
     * 
     * @description return assets to project or IT
     * @author Jay.He
     * @time Dec 26, 2013 1:37:22 PM
     * @param status
     * @param ids
     */
    void returnCustomerAsset(User returner, String status, String ids);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 26, 2013 1:57:49 PM
     * @param assetsId
     * @param userCode
     * @param request
     */
    void takeOverCustomerAsset(String assetsId, String userCode,
            HttpServletRequest request);

    /**
     * @description TODO
     * @author Jay.He
     * @time Dec 26, 2013 2:02:20 PM
     * @param customerCode
     * @param ids
     * @param projectCode
     * @param userName
     * @param assetUserCode
     * @param request
     * @throws BusinessException
     */
    void assginCustomerAsset(String customerCode, String ids,
            String projectCode, String userName, String assetUserCode,
            HttpServletRequest request) throws BusinessException;


    /**
     * @author John.li
     * @param request
     * @param page
     * @throws ExcelException
     * @throws SQLException
     */
    String exportAssetsForAll(SearchCondition condition, String[] customerIds,
            HttpServletRequest request) throws ExcelException, SQLException;
}
