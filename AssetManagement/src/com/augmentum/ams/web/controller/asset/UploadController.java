package com.augmentum.ams.web.controller.asset;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.augmentum.ams.aop.OperationLogAnnotation;
import com.augmentum.ams.exception.ExcelException;
import com.augmentum.ams.service.asset.AssetImportParserService;
import com.augmentum.ams.service.asset.AssetService;
import com.augmentum.ams.service.audit.AuditFileService;
import com.augmentum.ams.util.FileOperateUtil;
import com.augmentum.ams.web.controller.base.BaseController;
import com.augmentum.ams.web.vo.asset.AssetVo;
import com.augmentum.ams.web.vo.asset.ImportVo;
import com.augmentum.ams.web.vo.audit.AuditVo;

@Controller("uploadController")
@RequestMapping(value = "/upload")
public class UploadController extends BaseController {
    
    @Autowired
    private AssetImportParserService assetImportParserService;
    @Autowired
    private AuditFileService auditFileService;
    @Autowired
    private AssetService assetService;
    
    @RequestMapping(value = "/excel")
    @ResponseBody
    public JSONObject  uploadAssetsExcelFile(MultipartFile file, String flag, HttpServletRequest request,
            HttpServletResponse response) {
        
        File targetFile = FileOperateUtil.upload(request, response, file);
        ImportVo importVo = null;
        JSONObject jsonObject = new JSONObject();
        try {
            importVo = assetImportParserService.importAsset(targetFile, request, flag);
        } catch (ExcelException e) {
            jsonObject.put("error", e.getErrorCode());
            return jsonObject;
        }
        
        jsonObject.put("all", importVo.getAllImportRecords());
        jsonObject.put("success", importVo.getSuccessRecords());
        jsonObject.put("failure", importVo.getFailureRecords());
        jsonObject.put("failureFileName", importVo.getFailureFileName());
        
        return jsonObject;
    }
    
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public void uploadFile(
            @RequestParam(value = "file", required = false) MultipartFile file,
            AssetVo assetVo, HttpServletRequest request,
            HttpServletResponse response) {

        assetService.uploadAndDisplayImage(file, request, response);
    }
    
    @RequestMapping("/cvs")
    @ResponseBody
    public String checkInventory(@RequestParam(value = "file", required = false) MultipartFile file,
            AuditVo auditFileVo, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        File targetFile = FileOperateUtil.upload(request, response, file);
        int percentage = auditFileService.checkInventory(getUserVoByShiro(), targetFile, 
                auditFileVo.getAuditFileName());
        
        return String.valueOf(percentage);
    }
}
