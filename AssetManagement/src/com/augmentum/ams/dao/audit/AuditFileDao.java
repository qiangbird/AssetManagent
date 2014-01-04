package com.augmentum.ams.dao.audit;

import java.util.List;

import com.augmentum.ams.dao.base.BaseDao;
import com.augmentum.ams.model.audit.AuditFile;

public interface AuditFileDao extends BaseDao<AuditFile>{

    /** get audit file name for generate the new file name
     * Get the a AuditFile entity by file name
     * @param auditFileName
     * @return
     */
    AuditFile getAuditFileByFileName(String auditFileName);

    /**
     * @author Geoffrey.Zhao
     * @return
     */
    List<String> getAuditFilesName();
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    List<AuditFile> findProcessingAuditList();
    
    /**
     * @author Geoffrey.Zhao
     * @return
     */
    List<AuditFile> findDoneAuditList();
    
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
     * @author John.li
     * @return
     */
    String getEmployeeNameByFileName(String auditFileName);
}
