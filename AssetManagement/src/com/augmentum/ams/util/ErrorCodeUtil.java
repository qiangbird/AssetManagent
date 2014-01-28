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
    public static final String DATA_SAVE_FAILED = "0201001";
    public static final String DATA_UPDATE_FAILED = "0201002";
    public static final String DATA_DELETE_FAILED = "0201003";
    public static final String DATA_NOT_EXIST = "0201004";
    public static final String DATA_EXISTED = "0201005";

    /**
     * Asset status operation
     */
    public static final String ASSET_STATUS_INVALID = "0201006";
    public static final String ASSET_OWNERSHIP_INVALID = "0201007";
    
    public static final String ASSET_ASSIGN_FAILED = "0201008";
    public static final String ASSET_RETURN_FAILED = "0201009";
    public static final String ASSET_TAKEOVER_FAILED = "0201010";
    public static final String ASSET_CHANGE_TO_FIXED_FAILED = "0201011";
    public static final String ASSET_CHANGE_TO_NONFIXED_FAILED = "0201012";
    public static final String ASSET_ADD_TO_AUDIT_FAILED = "0201013";
    public static final String ASSET_IMPORT_FAILED = "0201014";
    public static final String ASSET_EXPORT_FAILED = "0201015";
    

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
