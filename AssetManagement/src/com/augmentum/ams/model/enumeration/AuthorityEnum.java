package com.augmentum.ams.model.enumeration;


/**
 * @author Rudy.Gao
 * @time Sep 12, 2013 9:41:40 AM
 */
public enum AuthorityEnum {

    ASSET_VIEW_OWN("Asset:view_own"),
    ASSET_EXPORT("Asset:export"),
    ASSET_APPLY_ASSET("Asset:apply_asset"),

    ASSET_VIEW_PROJECT("Asset:view_project"),
    ASSET_ASSIGN_ASSET("Asset:assign_asset"),

    ASSET_CHECK_IN_ASSET("Asset:check_in_asset"),
    ASSET_CHECK_OUT_ASSET("Asset:check_out_asset"),

    ASSET_CREATE("Asset:create"),
    ASSET_CHECK_INVENTORY("Asset:check_inventory"),
    ASSET_IMPORT_ASSET("Asset:import_asset"),
    TRANSFER_LOG("TransferLog:*"),

    OPERATION_LOG("OperationLog:*"),
    USER("User:*");

    private String authority;

    private AuthorityEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return authority;
    }

    public static AuthorityEnum getAuthorityEnum(String authority) {
        AuthorityEnum authorityEnum = null;
        for (AuthorityEnum ae : AuthorityEnum.values()) {
            if (ae.getAuthority().equalsIgnoreCase(authority)) {
                authorityEnum = ae;
                break;
            }
        }
        return authorityEnum;
    }


}
