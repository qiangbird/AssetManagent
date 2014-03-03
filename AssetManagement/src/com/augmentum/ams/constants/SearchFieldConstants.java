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

    public static final String[] CUSTOMER_GROUP_FIELDS = { "groupName",
            "description", "processType", "customers.customerName" };

    public static final String[] TRANSFER_LOG_FIELDS = { "asset.assetId",
            "asset.assetName", "user.userName", "action", "asset.manufacturer",
            "asset.barCode", "asset.seriesNo", "asset.poNo" };

    public static final String[] OPERATION_LOG_FIELDS = { "operatorName",
            "operatorID", "operationObject", "operation", "operationObjectID" };

    public static final String[] LOCATION_FIELDS = { "site", "room" };

    public static final String[] TODO_FIELDS = { "asset.assetId",
            "asset.assetName", "asset.customer.customerName",
            "asset.project.projectName", "asset.user.userName", "asset.type",
            "asset.status", "assigner.userName", "assigner.userName_forSort",
            "returner.userName", "returner.userName_forSort" };

    public static final String[] INCONSISTENT_FIELDS = { "barcode", "asset.assetId",
            "asset.assetName", "asset.user.userName",
            "asset.project.projectName", "asset.customer.customerName",
            "asset.poNo", "asset.barCode", "asset.entity", "asset.keeper",
            "asset.manufacturer", "asset.memo", "asset.ownerShip",
            "asset.seriesNo", "asset.status", "asset.type", "asset.vendor",
            "asset.location.site", "asset.location.room",
            "asset.user.userName_forSort" };

    public static final String[] INCONSISTENT_SENTENCE_FIELDS = {
            "asset.manufacturer", "asset.seriesNo", "asset.user.userName",
            "asset.project.projectName", "asset.customer.customerName",
            "asset.ownerShip", "asset.memo", "asset.vendor", "asset.entity",
            "asset.assetName" };

}
