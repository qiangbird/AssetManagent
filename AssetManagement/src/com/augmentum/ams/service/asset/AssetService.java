package com.augmentum.ams.service.asset;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.springframework.web.multipart.MultipartFile;

import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.util.ExceptionHelper;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.AssignAssetCondition;
import com.augmentum.ams.web.vo.system.SearchCondition;

public interface AssetService {

    /**
     * 
     * @author Grylls.Xu
     * @time Sep 24, 2013 4:01:14 PM
     * @description TODO
     * @param asset
     */
    void saveAsset(Asset asset);

    /**
     * 
     * @author Grylls.Xu
     * @time Sep 24, 2013 4:01:21 PM
     * @description TODO
     * @param asset
     */
    void updateAsset(Asset asset);

    /**
     * 
     * @author Grylls.Xu
     * @time Sep 24, 2013 4:01:25 PM
     * @description TODO
     */
    Asset getAsset(String id);

    /**
     * 
     * @author Grylls.Xu
     * @time Sep 24, 2013 4:01:34 PM
     * @description TODO
     */
    void deleteAssetById(String id);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 8, 2013 3:35:01 PM
     * @return
     */
    List<Asset> findAllAssets();

    /**
     * @description TODO
     * @author Jay.He
     * @time Nov 22, 2013 9:35:30 AM
     * @return
     */
    int getAllAssetCount();

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 9, 2013 9:57:05 AM
     * @param assetVo
     * @param asset
     * @param operation
     */
    void saveAssetAsType(AssetVo assetVo, Asset asset, String operation);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 9, 2013 9:57:35 AM
     * @return
     */
    String getGenerateAssetId();

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 9, 2013 9:57:38 AM
     * @param asset
     * @param operation
     * @return
     * @throws ParseException
     */
    List<PropertyTemplate> showOrViewSelfDefinedProperties(Asset asset, String operation)
            throws ParseException;

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 9, 2013 9:57:42 AM
     * @param asset
     * @param custCode
     * @param cust
     */
    void setAssetCustomer(Asset asset, String custCode, Customer cust);

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Dec 9, 2013 9:57:44 AM
     * @param assetVo
     * @param asset
     */
    void assetVoToAsset(AssetVo assetVo, Asset asset);

    /**
     * 
     * @author Jay.He
     * @time Dec 11, 2013 11:02:48 AM
     * @param customerId
     * @return
     */
    List<Asset> findAssetsByCustomerId(String customerId);

    /**
     * @author Geoffrey.Zhao
     * @param condition
     * @throws ExceptionHelper
     */
    void itAssignAssets(User assigner, AssignAssetCondition condition, HttpServletRequest request)
            throws ExceptionHelper;

    /**
     * @author Geoffrey.Zhao
     * @param assetIds
     */
    void returnAssetsToCustomer(String assetIds) throws ExceptionHelper;

    /**
     * @author Geoffrey.Zhao
     * @param assetIds
     */
    void changeAssetsToFixed(String assetIds) throws ExceptionHelper;

    /**
     * @author Geoffrey.Zhao
     * @param assetIds
     */
    void changeAssetsToNonFixed(String assetIds) throws ExceptionHelper;

    /**
     * @author Geoffrey.Zhao
     * @param assetIds
     */
    void addAssetsToAuditForSelected(String assetIds) throws ExceptionHelper;

    /**
     * @author Geoffrey.Zhao
     * @param page
     */
    void addAssetsToAuditForSearchResult(SearchCondition condition) throws ExceptionHelper;

    /**
     * @author Jay.He
     * @time Jan 3, 2014 9:54:09 AM
     * @param file
     * @param request
     * @param response
     */
    void uploadAndDisplayImage(MultipartFile file, HttpServletRequest request,
            HttpServletResponse response);

    /**
     * @author John.li
     * @param assetIds
     * @param request
     * @throws ExcelException
     * @throws SQLException
     */
    String exportAssetsByIds(String assetIds, HttpServletRequest request) throws ExcelException,
            SQLException;

    /**
     * @author John.li
     * @param request
     * @param page
     * @throws ExcelException
     * @throws SQLException
     */
    String exportAssetsForAll(SearchCondition condition, HttpServletRequest request)
            throws ExcelException, SQLException;

    /**
     * @author Geoffrey.Zhao
     * @param user
     * @return
     */
    Map<String, Integer> getAssetCountForPanel(User user);

    /**
     * @author Geoffrey.Zhao
     * @param user
     * @return
     */
    Map<String, Integer> getAssetCountForManager(User user, List<Customer> customers);

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    JSONArray findIdleAssetForPanel(List<Customer> customers);

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    JSONArray findWarrantyExpiredAssetForPanel(String clientTimeOffset);

}
