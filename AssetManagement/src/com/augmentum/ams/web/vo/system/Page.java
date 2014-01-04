package com.augmentum.ams.web.vo.system;

import java.io.Serializable;
import java.util.List;

import com.augmentum.ams.model.base.BaseModel;

/**
 * This is a VO which used to deal with the pagination.
 * 
 * @param <T> It can be used by any entity which needs to be paginated.
 */
public class Page<T extends BaseModel> implements Serializable {

    private static final long serialVersionUID = -6042742275916863789L;
    
    public static final String ASC = "asc";
    
    public static final String DESC = "desc";
    
    /** Indicate the default size of the page. */
    private static final int DEFAULT_PAGE_SIZE = 10;
    
    private static final int DEFAULT_CURRENT_PAGE = 1;
    
    /** Indicate the default column to be sorted. */
    private static final String DEFAULT_SORT_COLUMN = "updatedTime";
    
    private static final int DEFAULT_RECORD_COUNT = 0;
    
    private static final int DEFAULT_TOTAL_PAGE = 1;
    
    private static final int DEFAULT_PRE_PAGE = 1;
    
    private static final int DEFAULT_NEXT_PAGE = 1;
    
    private static final boolean DEFAULT_HAS_PRE_PAGE = false;
    
    private static final boolean DEFAULT_HAS_NEXT_PAGE = false;
    
    /** Indicate the default position of the result in all records. */
    private static final int DEFAULT_START_RECORD = 0;
    
    private int pageSize = DEFAULT_PAGE_SIZE;
    
    private int recordCount = DEFAULT_RECORD_COUNT;
    
    private int totalPage = DEFAULT_TOTAL_PAGE;

    private int currentPage = DEFAULT_CURRENT_PAGE;
    
    private int prePage = DEFAULT_PRE_PAGE;

    private int nextPage = DEFAULT_NEXT_PAGE;

    private boolean hasPrePage = DEFAULT_HAS_PRE_PAGE;

    private boolean hasNextPage = DEFAULT_HAS_NEXT_PAGE;

    private String sortOrder = DESC;

    private String sortColumn = DEFAULT_SORT_COLUMN;
    
    private int startRecord = DEFAULT_START_RECORD;

    private List<T> result;
    
    private String keyword;
    
    private String searchConditions;
    
    private List<T> allRecords;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(int recordCount) {
        this.recordCount = recordCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public int getStartRecord() {
        return startRecord;
    }

    public void setStartRecord(int startRecord) {
        this.startRecord = startRecord;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSearchConditions() {
        return searchConditions;
    }

    public void setSearchConditions(String searchConditions) {
        this.searchConditions = searchConditions;
    }

    public List<T> getAllRecords() {
        return allRecords;
    }

    public void setAllRecords(List<T> allRecords) {
        this.allRecords = allRecords;
    }
    
}
