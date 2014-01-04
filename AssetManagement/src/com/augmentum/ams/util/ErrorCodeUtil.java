package com.augmentum.ams.util;

/**
 * @author Rudy.Gao
 * @time Sep 27, 2013 3:11:50 PM
 */
public final class ErrorCodeUtil {
	
	/**
     * Authority delegates it is used to throw AuthorityException, USER
     * delegates it is used user entity
     */
    public static final String AUTHORITY_USER_0102001 = "0102001";

    /**
     * DATA delegates it is used to throw DataException, ASSET
     * delegates it is used asset entity
     */
    public static final String DATA_ASSET_SAVE_FAILED = "0201001";
    public static final String DATA_ASSET_UPDATE_FAILED = "0201002";
    public static final String DATA_ASSET_DELETE_FAILED = "0201003";
    public static final String DATA_ASSET_QUERY_NORECOED = "0201004";
    public static final String DATA_ASSET_NOT_EXIST = "0201005";
    public static final String DATA_ASSET_EXISTED = "0201006";

    /**
     * Asset status operation
     */
    public static final String ASSET_ASSIGN_FAILED = "0201007";
    public static final String ASSET_ASSIGN_STATUS_INVALID = "0201008";
    
    public static final String ASSET_RETURNING_TO_CUSTOMER_FAILED = "0201009";
    public static final String ASSET_RETURNING_TO_CUSTOMER_STATUS_INVALID = "0201010";
    public static final String ASSET_RETURNING_TO_CUSTOMER_OWNERSHIP_INVALID = "0201011";
    
    public static final String ASSET_CHANGE_TO_FIXED_FAILED = "0201012";
    public static final String ASSET_CHANGE_TO_NOT_FIXED_FAILED = "0201013";
    
    /**
     * DATA delegates it is used to throw DataException, USER
     * delegates it is used user entity
     */
    public static final String DATA_USER_SAVE_FAILED = "0202001";
    public static final String DATA_USER_UPDATE_FAILED = "0202002";
    public static final String DATA_USER_DELETE_FAILED = "0202003";
    public static final String DATA_USER_QUERY_NORECORD = "0202004";
    public static final String DATA_USER_NOT_EXIST = "0202005";
    public static final String DATA_USER_EXISTED = "0202006";

    /**
     * DATA delegates it is used to throw DataException, ROLE
     * delegates it is used role entity
     */
    public static final String DATA_ROLE_0203001 = "0203001";
    
    /**
     * DATA delegates it is used to throw DataException, IAP
     * delegates it is used interactive with IAP
     */
    public static final String DATA_IAP_0204001 = "0204001";
    
    /**
     * DATA delegates it is used to throw DataException, IAP
     * delegates it is used interactive with IAP
     */
    public static final String DATA_IAP_0204002 = "0204002";
    
    /**
     * DATA delegates it is used to throw DataException, IAP
     * delegates it is used interactive with IAP
     */
    public static final String DATA_IAP_0204003 = "0204003";
    
    /**
     * PARAMETER delegates it is used to throw ParameterException, ASSET
     * delegates it is used asset entity
     */
    public static final String PARAMETER_ASSET_0201001 = "0301001";

    /**
     * PARAMETER delegates it is used to throw ParameterException, USER
     * delegates it is used user entity
     */
    public static final String PARAMETER_USER_0202001 = "0302001";
    
    /**
     * PARAMETER delegates it is used unknown exception
     */
    public static final String UNKNOWN_EXCEPTION_0401001 = "0401001";

    private ErrorCodeUtil() {
    	
    }

}
