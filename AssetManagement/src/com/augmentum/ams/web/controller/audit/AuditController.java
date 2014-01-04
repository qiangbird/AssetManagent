package com.augmentum.ams.web.controller.audit;


import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.service.audit.AuditFileService;
import com.augmentum.ams.service.audit.AuditService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.audit.AuditVo;

@Controller("auditController")
@RequestMapping(value="/audit")
public class AuditController extends BaseController{
	
    @Autowired
    private AuditService auditService;
    
    @Autowired
    private AuditFileService auditFileService;
    
    @RequestMapping("/showAuditDetails")
    public ModelAndView showAuditDetails(AuditVo auditVo) throws Exception{
        
        ModelAndView modelAndView = new ModelAndView("audit/inventoryDetails");
        int percentNumTemp = auditService.getAuditPercentage(auditVo.getAuditFileName());
        String percentNum = String.valueOf(percentNumTemp);
        AuditFile auditFile = auditFileService.getByFileName(auditVo.getAuditFileName());
        
        if(null == auditFile || null == auditFile.getOperator()){
            modelAndView.addObject("operator", null);
            modelAndView.addObject("operationTime", null);
        }else{
            modelAndView.addObject("operator", 
                    auditFileService.getEmployeeNameByFileName(auditVo.getAuditFileName()));
            modelAndView.addObject("operationTime", auditFile.getOperationTime());
        }
        modelAndView.addObject("fileName", auditVo.getAuditFileName());
        modelAndView.addObject("percentNum", percentNum);
        
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
        int  sEcho=Integer.parseInt(servletRequest.getParameter("sEcho"));
        int iDisplayStart=Integer.parseInt(servletRequest.getParameter("iDisplayStart"));
        int iDisplayLength=Integer.parseInt(servletRequest.getParameter("iDisplayLength"));
        
        jsonObject.put("sEcho", sEcho);
        jsonObject.put("iTotalRecords", countAudit);
        jsonObject.put("iTotalDisplayRecords",countAudit);
        
        JSONArray audits= auditService.findAudited(auditVo.getAuditFileName(),
                iDisplayStart, iDisplayLength);
        jsonObject.put("aaData", audits);
        
        return jsonObject;
    }
    
    @RequestMapping("/findUnAuditedAssets")
    @ResponseBody
    public JSONObject findUnAuditedAssets(AuditVo auditVo,
            HttpServletRequest servletRequest) throws Exception {
        
        JSONObject jsonObject = new JSONObject();
        int countUnAudit=this.getUnAuditedAssetsCount(auditVo);
        int  sEcho=Integer.parseInt(servletRequest.getParameter("sEcho"));
        int iDisplayStart=Integer.parseInt(servletRequest.getParameter("iDisplayStart"));
        int iDisplayLength=Integer.parseInt(servletRequest.getParameter("iDisplayLength"));
        
        jsonObject.put("sEcho", sEcho);
        jsonObject.put("iTotalRecords", countUnAudit);
        jsonObject.put("iTotalDisplayRecords",countUnAudit );
        
        JSONArray unAudits= auditService.findUnAudited(auditVo.getAuditFileName(),
                iDisplayStart, iDisplayLength);
        jsonObject.put("aaData", unAudits);
        
        return jsonObject;
     }
}
