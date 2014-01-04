package com.augmentum.ams.service.audit;

import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augmentum.ams.model.audit.AuditFile;
import com.augmentum.ams.service.BaseCaseTest;

public class AuditFileServiceTest extends BaseCaseTest {

    @Autowired
    private AuditFileService auditFileService;

    @Test
    public void getProcessingAuditList() {
        Map<AuditFile, Integer> map = auditFileService.getProcessingAuditList();

        for (Map.Entry<AuditFile, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey().getFileName() + "==" + entry.getValue());
        }
    }

    @Test
    public void getDoneAuditList() {
        Map<AuditFile, Integer> map = auditFileService.getDoneAuditList();

        for (Map.Entry<AuditFile, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey().getFileName() + "==" + entry.getValue());
        }
    }
}
