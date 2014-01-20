package com.augmentum.ams.web.controller.audit;

import java.io.File;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.service.audit.AuditFileService;
import com.augmentum.ams.util.Constant;
import com.augmentum.ams.util.FileOperateUtil;
import com.augmentum.ams.util.UTCTimeUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.audit.AuditVo;

@Controller("auditFileController")
@RequestMapping(value = "/auditFile")
public class AuditFileController extends BaseController {

    @Autowired
    private AuditFileService auditFileService;

    @RequestMapping(value = "/inventoryList")
    public String redirectAuditList() {
        return "audit/inventoryList";
    }

    @RequestMapping(value = "/getProcessingAuditList")
    public ModelAndView getProcessingAuditList() {
        
        ModelAndView modelAndView = new ModelAndView();
        Map<AuditFile, Integer> auditFiles = auditFileService.getProcessingAuditList();
        
        JSONArray jsonArray = new JSONArray();
        
        for (Map.Entry<AuditFile, Integer> entry : auditFiles.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileName", entry.getKey().getFileName());
            jsonObject.put("percentage", entry.getValue());
            
            jsonArray.add(jsonObject);
        }

        modelAndView.addObject("processAudits", jsonArray);
        return modelAndView;
    }

    @RequestMapping(value = "/getDoneAuditList")
    public ModelAndView getDoneAuditList(HttpSession session) {
        
        ModelAndView modelAndView = new ModelAndView();
        
        String timeOffset = (String) session.getAttribute("timeOffset");
        Map<AuditFile, Integer> auditFiles = auditFileService.getDoneAuditList();
        
        JSONArray jsonArray = new JSONArray();
        
        for (Map.Entry<AuditFile, Integer> entry : auditFiles.entrySet()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileName", entry.getKey().getFileName());
            jsonObject.put("operationTime", UTCTimeUtil.formatDateToString(UTCTimeUtil
                    .utcToLocalTime(entry.getKey().getOperationTime(), timeOffset),
                    Constant.TIME_MINUTE_PATTERN));
            jsonObject.put("operator", entry.getKey().getOperator().getUserName());
            jsonObject.put("percentage", entry.getValue());
            
            jsonArray.add(jsonObject);
        }

        modelAndView.addObject("doneAudits", jsonArray);
        return modelAndView;
    }
    
    @RequestMapping(value="/getAuditFilesCount")
    public ModelAndView getAuditFilesCount() {
        
        ModelAndView modelAndView = new ModelAndView();
        int processCount = auditFileService.getProcessingAuditFilesCount();
        int doneCount = auditFileService.getDoneAuditFilesCount();
        
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("processCount", processCount);
        jsonObject.put("doneCount", doneCount);
        modelAndView.addObject("auditFilesCount", jsonObject);
        return modelAndView;
    }
    
    /**
     * check inventory
     * @return
     * @throws Exception
     */
    @RequestMapping("/checkInventory")
    @ResponseBody
    public String checkInventory(@RequestParam(value = "file", required = false) MultipartFile file,
            AuditVo auditFileVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        File targetFile = FileOperateUtil.upload(request, response, file);
        int percentage = auditFileService.checkInventory(getUserVoByShiro(), targetFile, 
                auditFileVo.getAuditFileName());
        
        return String.valueOf(percentage);
    }
    
    @RequestMapping("/updateToDone")
    @ResponseBody
    public String updateToDone(AuditVo audiVo) throws Exception {
        
        auditFileService.updateAuditFile(audiVo.getAuditFileName(), getUserVoByShiro());
        
        return null;
    }
    
    @RequestMapping("/deleteAuditFile")
    @ResponseBody
    public String deleteAuditFile(AuditVo audiVo) throws Exception {
        
        auditFileService.deleteFile(audiVo.getAuditFileName(), getUserVoByShiro());
        
        return null;
    }
}
