package com.augmentum.ams.web.controller.audit;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.exception.BaseException;
import com.augmentum.ams.model.asset.Asset;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.model.user.UserCustomColumn;
import com.augmentum.ams.service.audit.AuditFileService;
import com.augmentum.ams.service.audit.AuditService;
import com.augmentum.ams.service.search.UserCustomColumnsService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FormatEntityListToEntityVoList;
import com.augmentum.ams.util.SearchCommonUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetListVo;
import com.augmentum.ams.web.vo.audit.AuditVo;
import com.augmentum.ams.web.vo.system.Page;
import com.augmentum.ams.web.vo.system.SearchCondition;

@Controller("auditController")
@RequestMapping(value="/audit")
public class AuditController extends BaseController{
	
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private AuditFileService auditFileService;
    
    @Autowired
	private UserCustomColumnsService userCustomColumnsService;
    
    @RequestMapping("/showAuditDetails")
    public ModelAndView showAuditDetails(AuditVo auditVo) throws Exception{
        
        ModelAndView modelAndView = new ModelAndView("audit/inventoryDetail");
        int percentNumTemp = auditService.getAuditPercentage(auditVo.getAuditFileName());
        String percentNum = String.valueOf(percentNumTemp);
        AuditFile auditFile = auditFileService.getByFileName(auditVo.getAuditFileName());
        
        if(null == auditFile || null == auditFile.getOperator()){
            modelAndView.addObject(Constant.OPERATOR, null);
            modelAndView.addObject(Constant.OPERATION_TIME, null);
        }else{
            modelAndView.addObject(Constant.OPERATOR, 
                    auditFileService.getEmployeeNameByFileName(auditVo.getAuditFileName()));
            modelAndView.addObject(Constant.OPERATION_TIME, auditFile.getOperationTime());
        }
        modelAndView.addObject(Constant.FILE_NAME, auditVo.getAuditFileName());
        modelAndView.addObject(Constant.PERCENT_NUMBER, percentNum);
        
        return modelAndView;
    }
    
    @RequestMapping("/getUnAuditedAssetsCount")
    @ResponseBody
    public int getUnAuditedAssetsCount(AuditVo auditVo) throws Exception {

        return auditService.getUnAuditedCount(auditVo.getAuditFileName());
     }
    
    @RequestMapping("/getAuditedAssetsCount")
    @ResponseBody
    public int getAuditedAssetsCount(AuditVo auditFilevo) throws Exception {

        return auditService.getAuditedCount(auditFilevo.getAuditFileName());
    }
    
    @RequestMapping("/findAuditedAssets")
    @ResponseBody
    public JSONObject findAuditedAssets(AuditVo auditVo,
            HttpServletRequest servletRequest) throws Exception {
        
        JSONObject jsonObject = new JSONObject();

        int countAudit=this.getAuditedAssetsCount(auditVo);
        int  sEcho=Integer.parseInt(servletRequest.getParameter(Constant.S_ECHO));
        int iDisplayStart=Integer.parseInt(servletRequest.getParameter(Constant.I_DISPLAY_START));
        int iDisplayLength=Integer.parseInt(servletRequest.getParameter(Constant.I_DISPLAY_LENGTH));
        
        jsonObject.put(Constant.S_ECHO, sEcho);
        jsonObject.put(Constant.I_TOTAL_RECORDS, countAudit);
        jsonObject.put(Constant.I_TOTAL_DISPLAY_RECORDS,countAudit);
        
        JSONArray audits= auditService.findAudited(auditVo.getAuditFileName(),
                iDisplayStart, iDisplayLength);
        jsonObject.put(Constant.AA_DATA, audits);
        
        return jsonObject;
    }
    
    @RequestMapping("/findUnAuditedAssets")
    @ResponseBody
    public JSONObject findUnAuditedAssets(AuditVo auditVo,
            HttpServletRequest servletRequest) throws Exception {
        
        JSONObject jsonObject = new JSONObject();
        int countUnAudit=this.getUnAuditedAssetsCount(auditVo);
        int  sEcho=Integer.parseInt(servletRequest.getParameter(Constant.S_ECHO));
        int iDisplayStart=Integer.parseInt(servletRequest.getParameter(Constant.I_DISPLAY_START));
        int iDisplayLength=Integer.parseInt(servletRequest.getParameter(Constant.I_DISPLAY_LENGTH));
        
        jsonObject.put(Constant.S_ECHO, sEcho);
        jsonObject.put(Constant.I_TOTAL_RECORDS, countUnAudit);
        jsonObject.put(Constant.I_TOTAL_DISPLAY_RECORDS,countUnAudit );
        
        JSONArray unAudits= auditService.findUnAudited(auditVo.getAuditFileName(),
                iDisplayStart, iDisplayLength);
        jsonObject.put(Constant.AA_DATA, unAudits);
        
        return jsonObject;
     }
    
/*    @RequestMapping("/viewInventory")
    @ResponseBody
    public String viewInventory(AuditVo auditVo) throws Exception {
        User loginUser = (User) session.get("user");
        String showField = null;
        String flag = auditVo.getFlag();
        if(flag.equals("audit")||flag.equals("unAudit")){
            showField= Format.showFieldsToString(userService.getCommonColumnByUser(loginUser, CommonTableName.audit.toString()));
        }
        else if(flag.equals("incons")){
            showField= Format.showFieldsToString(userService.getCommonColumnByUser(loginUser, CommonTableName.inconsistent.toString()));
        }
        request.put("showFields", showField);
        System.out.println("flag"+flag);
        return SUCCESS;
    }*/
    
    @RequestMapping(value = "/inventoryAsset", method = RequestMethod.GET)
    public ModelAndView redirectInventoryAsset(String flag, String auditFileName) {
    	
    	ModelAndView modelAndView = new ModelAndView("audit/inventoryAssetList");
    	
    	modelAndView.addObject("flag", flag);
    	modelAndView.addObject("auditFileName", auditFileName);
    	
    	return modelAndView;
    }
    
    @RequestMapping(value = "/viewInventoryAsset", method = RequestMethod.GET)
	public ModelAndView findAllAssetsBySearchCondition(
			SearchCondition searchCondition, HttpSession session)
			throws BaseException {

		if (null == searchCondition) {
			searchCondition = new SearchCondition();
		}
		
//		searchCondition.setIsAudited(true);
//		searchCondition.setAuditFileName("2014-01-08_01");
		
		Page<Asset> page = auditService.findAssetForInventory(searchCondition);
		String clientTimeOffset = (String) session.getAttribute("timeOffset");
		List<AssetListVo> list = FormatEntityListToEntityVoList
				.formatAssetListToAssetVoList(page.getResult(),
						clientTimeOffset);
		List<UserCustomColumn> userCustomColumnList = userCustomColumnsService
				.findUserCustomColumns("asset", getUserIdByShiro());
		JSONArray array = SearchCommonUtil.formatAssetVoListTOJSONArray(list,
				userCustomColumnList,null);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("fieldsData", array);
		modelAndView.addObject("count", page.getRecordCount());
		modelAndView.addObject("totalPage", page.getTotalPage());
		return modelAndView;
	}
}
