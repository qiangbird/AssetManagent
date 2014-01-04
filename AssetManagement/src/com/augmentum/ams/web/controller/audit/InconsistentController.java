package com.augmentum.ams.web.controller.audit;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.augmentum.ams.service.audit.InconsistentService;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.audit.AuditVo;

@Controller("inconsistentController")
@RequestMapping(value="/inconsistent")
public class InconsistentController extends BaseController{
	
    @Autowired
    private InconsistentService inconsistentService;
    
    @RequestMapping("/getInconsistentAssetsCount")
    @ResponseBody
    public int getInconsistentAssetsCount(AuditVo auditFileVo) throws Exception {
        
        return inconsistentService.getInconsistentAssetsCount(auditFileVo.getAuditFileName());
     }
    
    @RequestMapping("/findInconsistentAssets")
    @ResponseBody
    public JSONObject findInconsistentAssets(AuditVo auditVo,
            HttpServletRequest servletRequest) throws Exception {
        
        JSONObject jsonObject = new JSONObject();
        
        int countIncons=this.getInconsistentAssetsCount(auditVo);
        int  sEcho=Integer.parseInt(servletRequest.getParameter("sEcho"));
        int iDisplayStart=Integer.parseInt(servletRequest.getParameter("iDisplayStart"));
        int iDisplayLength=Integer.parseInt(servletRequest.getParameter("iDisplayLength"));
        
        jsonObject.put("sEcho", sEcho);
        jsonObject.put("iTotalRecords", countIncons);
        jsonObject.put("iTotalDisplayRecords",countIncons);
        
        JSONArray incons= inconsistentService.findInconsistentAssets(auditVo.getAuditFileName(),
                iDisplayStart,iDisplayLength);
        jsonObject.put("aaData", incons);
        
        return jsonObject;
     } 
}
