package com.augmentum.ams.web.vo.system;

import org.hibernate.criterion.DetachedCriteria;


/**
 * Used to do pagination in Service level.
 * 
 */
public final  class PaginationUtil {

    
    private PaginationUtil() {
        
    }
   
    /**
     * Used to do pagination, using the relevant DAO to gain result from database.
     * Then encapsulates the relevant information into the instance of Page class.
     * CAUTION: use this new function to substitute the old one.
     * 
     * @param "pageableDao" is the DAO level that implements the pageable interface,
     * "page" contains the information that are required when paginating.
     * "criteria" is the instance of DetachedCriteria for querying.
     * 
     * @return Contains the information relate with pagination after searching from the database.
     */
    public static Page<?> gainPage(Page<?> page, DetachedCriteria criteria){
        
        //Validate the attribute in the page instance.
        validatePage(page);
        
        //Gain the position of the first record to be showed.
        gainStartRecord(page);
        
        //validate the data in the instance of Page after querying.
        validateAfterQuery(page);
        
        //Gain the amount of pages.
        gainTotalPage(page);
        
        //Gain the number of previous page, judge whether it exists.
        gainPrePage(page);
        
      //Gain the number of next page, judge whether it exists.
        gainNextPage(page);
        
        return page;
    }
    

    /**
     * Used to gain the amount of pages, then store it in the
     * instance of Page class.
     * 
     * @param page is the instance of page which contains the
     * basic information to calculate the amount of pages.
     */
    private static void gainTotalPage(Page<?> page) {
        int amount = page.getRecordCount() / page.getPageSize();
        
        //Judge whether every page is full of records
        if(page.getRecordCount() % page.getPageSize() == 0) {
            
            //If every page is full
            page.setTotalPage(amount);
        } else {
            
            //If there is one page which is not full of records
            page.setTotalPage(amount + 1);
        }
    }
    
    //The default prePage when the prePage doesn't exist
    private static final int DEFAULT_PRE_PAGE = 1;
    
    /**
     * Used to gain the number of the previous page
     * according to the current page, then store it in the
     * instance of Page class.
     * 
     * @param page is the instance of page which contains the
     * basic information needed.
     */
    private static void gainPrePage(Page<?> page) {
        
        //Judge whether the current page is the first page
        if(1 == page.getCurrentPage()) {
            
            //There isn't previous page before first page 
            page.setHasPrePage(false);
            page.setPrePage(DEFAULT_PRE_PAGE);
        } else {
            
            //The previous page of other page
            page.setHasPrePage(true);
            page.setPrePage(page.getCurrentPage() - 1);
        }
    }
    
    /**
     * Used to gain the number of the next page
     * according to the current page, then store it in the
     * instance of Page class.
     * 
     * @param page is the instance of page which contains the
     * basic information needed.
     */
    private static void gainNextPage(Page<?> page) {
        
        //Judge whether the current page is the last page
        if(page.getTotalPage() == page.getCurrentPage()) {
            
            //There isn't next page after the last page 
            page.setHasNextPage(false);
            page.setNextPage(page.getTotalPage());
        } else {
            
            //The next page of other page
            page.setHasNextPage(true);
            page.setNextPage(page.getCurrentPage() + 1);
        }
    }
    
    /**
     * Used to gain the position of the first record
     * to be showed on the page, then store it in the
     * instance of Page class.
     * 
     * @param page is the instance of page which contains the
     * basic information needed.
     */
    private static void gainStartRecord(Page<?> page) {
        int startRecord = (page.getCurrentPage() - 1) * page.getPageSize();
        page.setStartRecord(startRecord);
    }
    
    /**
     * Used to validate the information which stored in
     * the instance of Page class before querying.
     * 
     * @param page is the instance of page which contains the
     * basic information needed.
     * @throws IllegalArgumentException 
     */
    @SuppressWarnings("static-access")
    private static void validatePage(Page<?> page){
        if(page.getPageSize() <= 0) {
        	throw new RuntimeException();
        }
        if(page.getCurrentPage() < 1) {
        	throw new RuntimeException();
        }
    }
    
    private static void validateAfterQuery(Page<?> page) {
        if(page.getStartRecord() > page.getRecordCount()) {
        	throw new RuntimeException();
        }
    }
}
