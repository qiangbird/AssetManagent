package com.augmentum.ams.web.controller.asset;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.exception.DataException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.asset.DeviceSubtype;
import com.augmentum.ams.model.customized.PropertyTemplate;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.DeviceSubtypeService;
import com.augmentum.ams.service.asset.LocationService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.remote.RemoteEmployeeService;
import com.augmentum.ams.service.remote.RemoteEntityService;
import com.augmentum.ams.service.remote.RemoteSiteService;
import com.augmentum.ams.service.search.SearchAssetService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.service.user.UserService;
import com.augmentum.ams.util.AssetUtil;
import com.augmentum.ams.util.ExceptionHelper;
import com.augmentum.ams.util.FormatEntityListToEntityVoList;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.AssignAssetCondition;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.SiteVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("assetController")
@RequestMapping(value = "/asset")
public class AssetController extends BaseController {

    @Autowired
    private AssetService assetService;
    @Autowired
    private RemoteSiteService remoteSiteService;
    @Autowired
    private LocationService locationService;
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    @Autowired
    private UserService userService;
    @Autowired
    private DeviceSubtypeService deviceSubtypeService;
    @Autowired
    private RemoteEmployeeService remoteEmployeeService;
    @Autowired
    private RemoteEntityService remoteEntityService;
    @Autowired
    private UserCustomColumnsService userCustomColumnsService;
    @Autowired
    private SearchAssetService searchAssetService;

    private Page<Asset> pageForAudit;

    private Logger logger = Logger.getLogger(AssetController.class);

    @RequestMapping("/software")
    public String softwareList() {
        return "asset/assetList";
    }

    @RequestMapping("/software/detail")
    public String softwareDetail() {
        return "asset/assetDetail";
    }

    /**
     * @author Jay.He
     * @time Nov 15, 2013 10:59:52 AM
     * @param request
     * @param assetVo
     * @return
     * @throws DataException
     * @throws ParseException
     */
    @RequestMapping("/createAsset")
    public ModelAndView createAsset(HttpServletRequest request, AssetVo assetVo)
            throws DataException, ParseException {

        logger.info("createAsset method start!");

        ModelAndView modelAndView = getCommonInfoForAsset(request);
        assetVo.setAssetId(assetService.getGenerateAssetId());
        modelAndView.setViewName("asset/createAsset");

        logger.info("createAsset method end!");
        return modelAndView;

    }

    private ModelAndView getCommonInfoForAsset(HttpServletRequest request) throws DataException,
            ParseException {

        logger.info("getCommonInfoForAsset method start!");

        ModelAndView modelAndView = new ModelAndView();
        List<SiteVo> siteList = remoteSiteService.getSiteFromIAP(request);
        List<String> locationList = new ArrayList<String>();
        for (SiteVo siteVo : siteList) {
            locationList.addAll(AssetUtil.getSiteLocation(locationService.getAllLocation(siteVo
                    .getSiteNameAbbr())));
        }
        List<String> list = remoteEntityService.getAllEntityFromIAP(request);
        List<DeviceSubtype> deviceSubtypeList = deviceSubtypeService.getAllDeviceSubtype();

        modelAndView.addObject("siteList", locationList);
        modelAndView.addObject("allEntity", list);
        modelAndView.addObject("assetStatus", AssetUtil.getAssetStatus());
        modelAndView.addObject("assetType", AssetUtil.getAssetTypes());
        modelAndView.addObject("machineTypes", AssetUtil.getMachineTypes());
        modelAndView.addObject("deviceSubtypeList", deviceSubtypeList);

        logger.info("getCommonInfoForAsset method end!");
        return modelAndView;
    }

    /**
     * 
     * @author Jay.He
     * @time Nov 15, 2013 10:59:47 AM
     * @param request
     * @param assetVo
     * @param batchCreate
     * @param batchCount
     * @return
     * @throws DataException
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/saveAsset", method = RequestMethod.POST)
    public ModelAndView saveAsset(HttpServletRequest request, AssetVo assetVo, String batchCreate,
            String batchCount) throws DataException, UnsupportedEncodingException {

        logger.info("saveAsset method start!");

        logger.info(assetVo.toString());
        ModelAndView modelAndView = new ModelAndView();
        Asset asset = new Asset();

        voToAsset(request, assetVo, asset);
        AssetUtil.setKeeperForAssetVo(assetVo, asset);

        if (null == batchCreate) {
            asset.setAssetId(assetVo.getAssetId());
            try {
                assetService.saveAssetAsType(assetVo, asset, "save");
            } catch (Exception e) {
                logger.error("Save asset error", e);
            }
        } else {
            try {
                // TODO
                int batchNum = Integer.parseInt(batchCount);

                List<String> batchIdList = AssetUtil.getBatchId(assetVo.getAssetId(), batchNum);
                for (int i = 0; i < batchNum; i++) {
                    asset.setAssetId(batchIdList.get(i));
                    assetService.saveAssetAsType(assetVo, asset, "save");
                }
            } catch (NumberFormatException e) {
                // TODO
                logger.error("Number format error!", e);
            }
        }
        modelAndView.setViewName("redirect:/asset/allAssets");

        logger.info("saveAsset method end!");
        return modelAndView;
    }

    /**
     * 
     * @description copy assetVo elements to asset
     * @author Jay.He
     * @time Nov 11, 2013 3:59:30 PM
     * @param assetVo
     * @param asset
     * @throws UnsupportedEncodingException
     * @throws DataException
     */
    private void voToAsset(HttpServletRequest request, AssetVo assetVo, Asset asset)
            throws UnsupportedEncodingException {
        // get Location local with location sent from front page
        // TODO vo --> User -> username,userId
        if (!assetVo.getUser().getUserName().equals("")) {
            User user = userService.getUserByName(assetVo.getUser().getUserName());
            if (null == user) {
                UserVo userVo = null;
                try {
                    userVo = remoteEmployeeService.getRemoteUserByName(assetVo.getUser()
                            .getUserName(), request);
                } catch (DataException e) {
                    logger.error("Get user error from IAP", e);
                }
                userService.saveUserAsUserVo(userVo);
            }
        } else {
            assetVo.setUser(null);
        }
        setCustomerToAsset(request, assetVo, asset);
        assetService.assetVoToAsset(assetVo, asset);

    }

    /**
     * 
     * @description TODO
     * @author Jay.He
     * @time Nov 22, 2013 10:00:15 AM
     * @param request
     * @param assetVo
     * @param asset
     */
    private void setCustomerToAsset(HttpServletRequest request, AssetVo assetVo, Asset asset) {
        String custCode = assetVo.getCustomer().getCustomerCode();
        Customer cust = null;
        try {
            cust = remoteCustomerService.getCustomerByCodefromIAP(request, custCode);
        } catch (DataException e1) {
            e1.printStackTrace();
        }
        assetService.setAssetCustomer(asset, custCode, cust);
    }

    @RequestMapping(value = "/allAssets")
    public String redirectPage() {

        logger.info("redirectPage method start!");
        logger.info("redirectPage method end!");
        return "asset/allAssetsList";
    }

    /**
     * 
     * @description Upload and show the file
     * @author Jay.He
     * @time Nov 21, 2013 4:15:42 PM
     * @param file
     * @param assetVo
     * @param request
     * @param response
     */
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public void uploadFile(@RequestParam(value = "file", required = false) MultipartFile file,
            AssetVo assetVo, HttpServletRequest request, HttpServletResponse response) {

        logger.info("uploadFile method start!");

        assetService.uploadAndDisplayImage(file, request, response);

        logger.info("uploadFile method end!");
    }

//    @RequestMapping("/getCustomerInfo")
//    public ModelAndView getCustomerInfo(HttpServletRequest request) throws DataException {
//        ModelAndView modelAndView = new ModelAndView();
//        List<CustomerVo> customerList = remoteCustomerService.getAllCustomerFromIAP(request);
//        modelAndView.addObject("customerList", customerList);
//        return modelAndView;
//    }

    // @RequestMapping("/listAsset")
    // public ModelAndView listAsset() throws ParseException {
    // ModelAndView modelAndView = new ModelAndView();
    // List<Asset> allAssets = assetService.findAllAssets();
    // modelAndView.addObject("assetList", allAssets);
    // modelAndView.setViewName("asset/listAsset");
    // return modelAndView;
    // }

    @RequestMapping(value = "/edit/{id}")
    public ModelAndView editAssetById(@PathVariable String id, HttpServletRequest request)
            throws Exception {

        logger.info("editAssetById method start!");

        ModelAndView modelAndView = getCommonInfoForAsset(request);
        Asset asset = assetService.getAsset(id);
        AssetVo assetVo = new AssetVo();
        assetVo.setId(id);
        String timeOffset = (String) request.getSession().getAttribute("timeOffset");
        AssetUtil.assetToAssetVo(asset, assetVo, timeOffset);
        // get self-defined properties
        getSelfDefinedProperties(asset, modelAndView, "show");
        modelAndView.addObject("asset", assetVo);
        modelAndView.setViewName("asset/editAsset");

        logger.info("editAssetById method end!");
        return modelAndView;
    }

    @RequestMapping(value = "/view/{id}")
    public ModelAndView viewAssetById(@PathVariable String id, HttpServletRequest request)
            throws Exception {

        logger.info("viewAssetById method start!");

        ModelAndView modelAndView = getCommonInfoForAsset(request);
        Asset asset = assetService.getAsset(id);
        AssetVo assetVo = new AssetVo();
        assetVo.setId(id);
        String timeOffset = (String) request.getSession().getAttribute("timeOffset");
        AssetUtil.assetToAssetVo(asset, assetVo, timeOffset);
        // get self-defined properties
        getSelfDefinedProperties(asset, modelAndView, "view");
        modelAndView.addObject("asset", assetVo);
        modelAndView.setViewName("asset/assetDetail");

        logger.info("viewAssetById method end!");
        return modelAndView;
    }

    private void getSelfDefinedProperties(Asset asset, ModelAndView modelAndView, String operation)
            throws Exception {
        List<PropertyTemplate> propertyTemplatesList = assetService
                .showOrViewSelfDefinedProperties(asset, operation);
        modelAndView.addObject("selfPropertyCount", propertyTemplatesList.size());
        modelAndView.addObject("showSelfProperties", propertyTemplatesList);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView updateAsset(AssetVo assetVo, HttpServletRequest request)
            throws UnsupportedEncodingException {

        logger.info("updateAsset method start!");

        ModelAndView modelAndView = new ModelAndView();
        Asset asset = assetService.getAsset(assetVo.getId().trim());
        voToAsset(request, assetVo, asset);
        AssetUtil.setKeeperForAssetVo(assetVo, asset);
        try {
            assetService.saveAssetAsType(assetVo, asset, "update");
        } catch (Exception e) {
            logger.error("Update asset error!", e);
        }
        modelAndView.setViewName("redirect:/asset/allAssets");

        logger.info("updateAsset method end!");
        return modelAndView;
    }

    @RequestMapping(value = "/copy/{id}")
    public ModelAndView copyAssetById(@PathVariable String id, HttpServletRequest request)
            throws DataException, ParseException {

        logger.info("copyAssetById method start!");

        ModelAndView modelAndView = getCommonInfoForAsset(request);
        Asset asset = assetService.getAsset(id);
        asset.setAssetId(assetService.getGenerateAssetId());
        AssetVo assetVo = new AssetVo();
        String timeOffset = (String) request.getSession().getAttribute("timeOffset");
        AssetUtil.assetToAssetVo(asset, assetVo, timeOffset);
        modelAndView.addObject("assetVo", assetVo);
        modelAndView.setViewName("asset/copyAsset");

        logger.info("copyAssetById method end!");
        return modelAndView;
    }

    @RequestMapping("/delete/{id}")
    public ModelAndView deleteAssetById(@PathVariable String id) throws DataException,
            ParseException {

        logger.info("deleteAssetById method satrt!");

        ModelAndView modelAndView = new ModelAndView();
        assetService.deleteAssetById(id);
        modelAndView.setViewName("redirect:/asset/allAssets");

        logger.info("deleteAssetById method end!");
        return modelAndView;
    }

    @RequestMapping(value = "/itAssignAssets")
    @ResponseBody
    public String itAssignAssets(AssignAssetCondition condition, HttpServletRequest request) {

        try {
            assetService.itAssignAssets(condition, request);
        } catch (ExceptionHelper e) {
            logger.info(e);
        }
        return null;
    }

    @RequestMapping(value = "/returnAssetsToCustomer")
    @ResponseBody
    public String returnAssetsToCustomer(String assetIds) {

        try {
            assetService.returnAssetsToCustomer(assetIds);
        } catch (ExceptionHelper e) {
            logger.info(e);
        }
        return null;
    }

    @RequestMapping(value = "/changeAssetsToFixed")
    @ResponseBody
    public String changeAssetsToFixed(String assetIds) {

        try {
            assetService.changeAssetsToFixed(assetIds);
        } catch (ExceptionHelper e) {
            logger.info(e);
        }
        return null;
    }

    @RequestMapping(value = "/changeAssetsToNonFixed")
    @ResponseBody
    public String changeAssetsToNonFixed(String assetIds) {

        try {
            assetService.changeAssetsToNonFixed(assetIds);
        } catch (ExceptionHelper e) {
            logger.info(e);
        }
        return null;
    }

    @RequestMapping(value = "/addAssetsToAuditForSearchResult")
    public String addAssetsToAuditForSearchResult() {

        assetService.addAssetsToAuditForSearchResult(pageForAudit);
        return "redirect:/auditFile/inventoryList";
    }

    @RequestMapping(value = "/addAssetsToAuditForSelected")
    public String addAssetsToAuditForSelected(String assetIds) {

        assetService.addAssetsToAuditForSelected(assetIds);
        return "redirect:/auditFile/inventoryList";
    }

    @RequestMapping(value = "/listMyAssets")
    public String redirectToMyassetsPage(HttpSession session, HttpServletRequest request) {

        logger.info("redirectToMyassetsPage method satrt!");

        UserVo userVo = (UserVo) session.getAttribute("userVo");
        User user = userService.getUserByUserId(userVo.getEmployeeId());
        request.setAttribute("userUuid", user.getId());

        logger.info("redirectToMyassetsPage method end!");
        return "asset/allAssetsList";
    }

    // TODO get asset data list
    @RequestMapping(value = "/allAssetsList", method = RequestMethod.GET)
    public ModelAndView findMyAssetsBySearchCondition(SearchCondition searchCondition, String uuid,
            HttpSession session) throws BaseException {

        logger.info("findMyAssetsBySearchCondition method end!");

        if (null == searchCondition) {
            searchCondition = new SearchCondition();
        }
        if (null != uuid && !uuid.equals("")) {
            searchCondition.setUserUuid(uuid);
        }
        Page<Asset> page = searchAssetService.findAllAssetsBySearchCondition(searchCondition);
        pageForAudit = page;
        String clientTimeOffset = (String) session.getAttribute("timeOffset");
        List<AssetListVo> list = FormatEntityListToEntityVoList.formatAssetListToAssetVoList(
                page.getResult(), clientTimeOffset);
        List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
                .findUserCustomColumns("asset", getUserIdByShiro());
        JSONArray array = SearchCommonUtil.formatAssetVoListTOJSONArray(list, userCustomColumnList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", page.getRecordCount());
        modelAndView.addObject("totalPage", page.getTotalPage());

        logger.info("findMyAssetsBySearchCondition method end!");
        return modelAndView;
    }

}
