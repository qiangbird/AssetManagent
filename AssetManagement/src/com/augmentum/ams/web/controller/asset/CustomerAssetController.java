package com.augmentum.ams.web.controller.asset;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BusinessException;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.asset.Customer;
import com.augmentum.ams.model.user.User;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.asset.CustomerAssetService;
import com.augmentum.ams.service.asset.CustomerService;
import com.augmentum.ams.service.asset.TransferLogService;
import com.augmentum.ams.service.remote.RemoteCustomerService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.FileOperateUtil;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.asset.CustomerVo;
import com.augmentum.ams.web.vo.asset.ProjectVo;
import com.augmentum.ams.web.vo.convert.FormatEntityListToEntityVoList;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;
import com.augmentum.ams.web.vo.user.UserVo;

@Controller("customerAssetController")
@RequestMapping(value = "/customerAsset")
public class CustomerAssetController extends BaseController {
    @Autowired
    private CustomerAssetService customerAssetService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserCustomColumnsService userCustomColumnsService;
    @Autowired
    private RemoteCustomerService remoteCustomerService;
    @Autowired
    private TransferLogService transferLogService;
    @Autowired
    private AssetService assetService;

    private Logger logger = Logger.getLogger(CustomerAssetController.class);

    @RequestMapping(value = "searchCustomerAssetsList")
    public ModelAndView listAssetsAsCustomerCode(
            SearchCondition searchCondition, String customerCode,
            HttpSession session) {

        logger.info("listAssetsAsCustomerCode method start!");

        ModelAndView modelAndView = new ModelAndView();
        Customer customer = customerService.getCustomerByCode(customerCode);

        if (null == searchCondition) {
            searchCondition = new SearchCondition();
        }
        
        Page<Asset> assetPage = customerAssetService
                .findCustomerAssetsBySearchCondition(searchCondition,
                        customer.getId());
        String clientTimeOffset = (String) session.getAttribute("timeOffset");
        List<AssetListVo> list = FormatEntityListToEntityVoList
                .formatAssetListToAssetVoList(assetPage.getResult(),
                        clientTimeOffset);
        List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
                .findUserCustomColumns("asset", getUserIdByShiro());
        JSONArray array = SearchCommonUtil.formatAssetVoListTOJSONArray(list,
                userCustomColumnList, null);
        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", assetPage.getRecordCount());
        modelAndView.addObject("totalPage", assetPage.getTotalPage());
        modelAndView.addObject("searchCondition", searchCondition);

        logger.info("listAssetsAsCustomerCode method end!");
        return modelAndView;
    }

    // goCustomerAsset
    @RequestMapping(value = "listCustomerAsset", method = RequestMethod.GET)
    public String listCustomerAsset(String customerCode,
            HttpServletRequest request, String status, String type)
            throws BusinessException {

        logger.info("listCustomerAsset method start!");

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("customerCode", customerCode);
        Customer customer = customerService.getCustomerByCode(customerCode);
        List<ProjectVo> projectList = remoteCustomerService
                .getProjectByCustomerCode(customerCode, request);
        request.setAttribute("customer", customer);
        request.setAttribute("projectList", projectList);
        request.setAttribute("status", status);
        request.setAttribute("type", type);

        logger.info("listCustomerAsset method start!");
        return "asset/customerAssetList";
    }

    @RequestMapping(value = "assginAssets", method = RequestMethod.PUT)
    public ModelAndView assginAssets(String customerCode, String ids,
            String projectCode, String userName, String assetUserCode,
            HttpServletRequest request) throws BusinessException {

        logger.info("assginAssets method start!");
        logger.info(customerCode + "\t" + projectCode + "\t" + userName + "\t"
                + assetUserCode);

        ModelAndView modelAndView = new ModelAndView();
        customerAssetService.assginCustomerAsset(customerCode, ids,
                projectCode, userName, assetUserCode, request);
        transferLogService.saveTransferLog(ids, "Assign");

        modelAndView.addObject("customerCode", customerCode);
        modelAndView.setViewName("redirect:/customerAsset/listCustomerAsset");

        logger.info("assginAssets method end!");
        return modelAndView;
    }

    @RequestMapping(value = "changeStatus/{status}", method = RequestMethod.PUT)
    @ResponseBody
    public String returnToOperation(@PathVariable String status,
            String assetsId, String customerCode, String operation) {

        logger.info("returnToOperation method start!");

        User returner = (User) SecurityUtils.getSubject().getSession()
                .getAttribute("currentUser");
        customerAssetService.returnCustomerAsset(returner, status, assetsId);
        transferLogService.saveTransferLog(assetsId, operation);

        logger.info("returnToOperation method end!");
        return null;
    }

    @RequestMapping(value = "takeOver", method = RequestMethod.PUT)
    @ResponseBody
    public String takeOver(String assetsId, String customerCode,
            String userCode, HttpServletRequest request) {

        logger.info("takeOver method in CustomerAssetController start!");

        customerAssetService.takeOverCustomerAsset(assetsId, userCode, request);
        transferLogService.saveTransferLog(assetsId, "TakeOver");

        logger.info("takeOver method in CustomerAssetController end!");
        return null;
    }

    @RequestMapping(value = "/listAllCustomerAssets")
    public String redirectAllCustomerAssetList(HttpServletRequest request,
            String type, String status) {

        request.setAttribute("type", type);
        request.setAttribute("status", status);
        return "asset/customerAssetList";
    }

    @RequestMapping(value = "/findAllCustomersAssets")
    public ModelAndView findAllCustomersAssets(SearchCondition searchCondition,
            HttpSession session, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView();

        if (null == searchCondition) {
            searchCondition = new SearchCondition();
        }

        // TODO: change to get customer list from session
        UserVo userVo = (UserVo) SecurityUtils.getSubject().getSession()
                .getAttribute("userVo");

        List<CustomerVo> list = null;
        try {
            list = remoteCustomerService.getCustomerByEmployeeId(
                    userVo.getEmployeeId(), request);
        } catch (BusinessException e) {
            logger.error("get customerVo failed from IAP", e);
        }

        List<Customer> customers = customerAssetService
                .findVisibleCustomerList(userVo, list);
        
        Set<Customer> set = new HashSet<Customer>(customers);
        List<Customer> customerList = new ArrayList<Customer>(set);

        Page<Asset> page = customerAssetService
                .findAllCustomerAssetBySearchCondition(searchCondition,
                        customerList);

        String clientTimeOffset = (String) session.getAttribute("timeOffset");
        List<AssetListVo> assetVoList = FormatEntityListToEntityVoList
                .formatAssetListToAssetVoList(page.getResult(),
                        clientTimeOffset);
        List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
                .findUserCustomColumns("asset", getUserIdByShiro());
        JSONArray array = SearchCommonUtil.formatAssetVoListTOJSONArray(
                assetVoList, userCustomColumnList, "");

        modelAndView.addObject("fieldsData", array);
        modelAndView.addObject("count", page.getRecordCount());
        modelAndView.addObject("totalPage", page.getTotalPage());
        modelAndView.addObject("searchCondition", searchCondition);

        return modelAndView;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    public void exportAssets(HttpServletRequest request,
            HttpServletResponse response, String assetIds, 
            String customerCode, SearchCondition condition) {

        String customerId = "";
        List<Customer> customerList = null; 
        
        if (StringUtils.isNotBlank(customerCode)) {
            Customer customer = customerService.getCustomerByCode(customerCode);
            customerId = customer.getId();
        } else {
            
            // TODO: change to get customer list from session
            UserVo userVo = (UserVo) SecurityUtils.getSubject().getSession()
                    .getAttribute("userVo");
            
            List<CustomerVo> list = null;
            try {
                list = remoteCustomerService.getCustomerByEmployeeId(
                        userVo.getEmployeeId(), request);
            } catch (BusinessException e) {
                logger.error("get customerVo failed from IAP", e);
            }
            
            List<Customer> customers = customerAssetService
                    .findVisibleCustomerList(userVo, list);
            
            Set<Customer> set = new HashSet<Customer>(customers);
            customerList = new ArrayList<Customer>(set);
        }
        
        
        if (null == condition) {
            condition = new SearchCondition();
        }
        
        String outPutPath = null;
        try {

            if (null == assetIds || "".equals(assetIds)) {
                condition.setIsGetAllRecords(Boolean.TRUE);
                outPutPath = customerAssetService.exportAssetsForAll(condition,
                        customerList, customerId, request);
            } else {
                outPutPath = assetService.exportAssetsByIds(assetIds, request);
            }
            FileOperateUtil.download(request, response, outPutPath);

        } catch (ExcelException e) {
            logger.error(e.getMessage());
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

}
