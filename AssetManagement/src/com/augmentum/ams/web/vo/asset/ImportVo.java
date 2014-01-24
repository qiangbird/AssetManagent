package com.augmentum.ams.web.vo.asset;

import com.augmentum.ams.model.asset.Asset;

public class ImportVo {

    private int allImportRecords;
    
    private int successRecords;
    
    private int failureRecords;
    
    private String failureFileName;
    
    private boolean isErrorRecorde;
    
    private Asset asset;

    public int getAllImportRecords() {
        return allImportRecords;
    }

    public void setAllImportRecords(int allImportRecords) {
        this.allImportRecords = allImportRecords;
    }

    public int getSuccessRecords() {
        return successRecords;
    }

    public void setSuccessRecords(int successRecords) {
        this.successRecords = successRecords;
    }

    public int getFailureRecords() {
        return failureRecords;
    }

    public void setFailureRecords(int failureRecords) {
        this.failureRecords = failureRecords;
    }

    public String getFailureFileName() {
        return failureFileName;
    }

    public void setFailureFileName(String failureFileName) {
        this.failureFileName = failureFileName;
    }

    public boolean isErrorRecorde() {
        return isErrorRecorde;
    }

    public void setErrorRecorde(boolean isErrorRecorde) {
        this.isErrorRecorde = isErrorRecorde;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }
    
}
