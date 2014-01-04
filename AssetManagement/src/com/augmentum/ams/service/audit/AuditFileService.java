package com.augmentum.ams.service.audit;

import java.io.File;
import java.util.Map;

import com.augmentum.ams.exception.AuditHandleException;
import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.web.vo.user.UserVo;


public interface AuditFileService {

    /**
     * Check the inventory file and return the percentage
     * @param userVoByShiro
     * @param file
     * @param auditFileName
     * @return
     * @throws AuditHandleException 
     */
    int checkInventory(UserVo userVoByShiro, File file,
            String auditFileName) throws AuditHandleException;

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    Map<AuditFile, Integer> getProcessingAuditList();
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    Map<AuditFile, Integer> getDoneAuditList();
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    int getProcessingAuditFilesCount();
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    int getDoneAuditFilesCount();

    /**
     * Update AuditFile by file name and user
     */
    void updateAuditFile(String auditFileName, UserVo userVoByShiro);

    /**
     * Delete AuditFile by file name and user
     */
    void deleteFile(String auditFileName, UserVo userVoByShiro);

    /**
     * Get audit file by file name
     * @param auditFileName
     * @return
     */
    AuditFile getByFileName(String auditFileName);
    
    /**
     * Get employee name by file name
     * @param auditFileName
     * @return
     */
    String getEmployeeNameByFileName(String auditFileName);
}
