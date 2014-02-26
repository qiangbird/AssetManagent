package com.augmentum.ams.constants;

public class SearchFieldConstants {

    public static final String[] ALL_ASSET_FIELDS = { "assetId", "assetName",
            "user.userName", "project.projectName", "customer.customerName",
            "poNo", "barCode", "entity", "keeper", "manufacturer", "memo",
            "ownerShip", "seriesNo", "status", "type", "vendor",
            "location.site", "location.room", "user.userName_forSort" };

    public static final String[] ASSET_SENTENCE_FIELDS = { "manufacturer",
            "seriesNo", "user.userName", "project.projectName",
            "customer.customerName", "ownerShip", "memo", "vendor", "entity",
            "assetName" };

    public static final String ASSET_STATUS = "available,idle,in_use,borrowed,returned,broken,write_off,assigning,returning_to_it";

    public static final String ASSET_TYPE = "device,machine,software,monitor,otherassets";

    public static final String[] CUSTOMER_GROUP_FIELDS = {
            "customerGroup.groupName", "customerGroup.description",
            "customerGroup.processType", "customerName" };

    public static final String[] TRANSFER_LOG_FIELDS = { "asset.assetId",
            "asset.assetName", "user.userName", "action", "asset.manufacturer",
            "asset.barCode", "asset.seriesNo", "asset.poNo" };

    public static final String[] OPERATION_LOG_FIELDS = { "operatorID",
            "operatorID", "operationObject", "operation", "operationObjectID",
            "operationObjectID" };
    
    public static final String[] LOCATION_FIELDS = {"site", "room"};

}
