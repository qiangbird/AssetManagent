package com.augmentum.ams.web.vo.user;

public class UserCustomColumnVo {

    private String id;
    private Integer sequence;
    private String enName;
    private String zhName;
    private Boolean showDefault;
    private String categoryType;
    private Boolean isMustShow;
    private String sortName;
    private Integer width;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getSequence() {
        return sequence;
    }
    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }
    public String getEnName() {
        return enName;
    }
    public void setEnName(String enName) {
        this.enName = enName;
    }
    public String getZhName() {
        return zhName;
    }
    public void setZhName(String zhName) {
        this.zhName = zhName;
    }
    public Boolean getShowDefault() {
        return showDefault;
    }
    public void setShowDefault(Boolean showDefault) {
        this.showDefault = showDefault;
    }
    public String getCategoryType() {
        return categoryType;
    }
    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }
    public Boolean getIsMustShow() {
        return isMustShow;
    }
    public void setIsMustShow(Boolean isMustShow) {
        this.isMustShow = isMustShow;
    }
    public String getSortName() {
        return sortName;
    }
    public void setSortName(String sortName) {
        this.sortName = sortName;
    }
    public Integer getWidth() {
        return width;
    }
    public void setWidth(Integer width) {
        this.width = width;
    }
    
}
