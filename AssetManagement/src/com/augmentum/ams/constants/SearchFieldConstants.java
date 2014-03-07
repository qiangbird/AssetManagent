package com.augmentum.ams.constants;

import org.apache.commons.lang.ArrayUtils;

public class SearchFieldConstants {

    /*
     * all asset fields
     */
    public static final String[] ALL_ASSET_FIELDS = { "assetId", "assetName",
            "user.userName", "project.projectName", "customer.customerName",
            "poNo", "barCode", "entity", "keeper", "manufacturer", "memo",
            "ownerShip", "seriesNo", "status", "type", "vendor",
            "location.site", "location.room", "user.userName_forSort" };

    public static final String[] ASSET_SENTENCE_FIELDS = { "manufacturer",
            "seriesNo", "user.userName", "project.projectName",
            "customer.customerName", "ownerShip", "memo", "vendor", "entity",
            "assetName" };

    /*
     * customer group fields
     */
    public static final String[] CUSTOMER_GROUP_FIELDS = { "groupName",
            "description", "processType", "customers.customerName" };

    /*
     * transfer log fields
     */
    public static final String[] TRANSFER_LOG_FIELDS = { "asset.assetId",
            "asset.assetName", "user.userName", "action", "asset.manufacturer",
            "asset.barCode", "asset.seriesNo", "asset.poNo" };

    /*
     * operation log fields
     */
    public static final String[] OPERATION_LOG_FIELDS = { "operatorName",
            "operatorID", "operationObject", "operation", "operationObjectID" };

    /*
     * location fields
     */
    public static final String[] LOCATION_FIELDS = { "site", "room" };

    /*
     * todo fields
     */
    public static final String[] TODO_FIELDS = { "asset.assetId",
            "asset.assetName", "asset.customer.customerName",
            "asset.project.projectName", "asset.user.userName", "asset.type",
            "asset.status", "assigner.userName", "assigner.userName_forSort",
            "returner.userName", "returner.userName_forSort" };

    /*
     * inconsistent fileds
     */
    public static final String[] INCONSISTENT_FIELDS = { "barcode",
            "asset.assetId", "asset.assetName", "asset.user.userName",
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

    /*
     * userRole fields
     */
    public static final String[] USER_ROLE_FIELDS = { "userName",
            "userName_forSort", "userId" };

    /*
     * machine fields
     */
    public static final String[] SOLE_MACHINE_FIELDS = { "machine.address",
            "machine.configuration", "machine.specification", "machine.subtype" };

    public static final String[] MACHINE_FIELDS = (String[]) ArrayUtils.addAll(
            ALL_ASSET_FIELDS, SOLE_MACHINE_FIELDS);

    /*
     * monitor fields
     */
    public static final String[] SOLE_MONITOR_FIELDS = { "monitor.size",
            "monitor.detail" };

    public static final String[] MONITOR_FIELDS = (String[]) ArrayUtils.addAll(
            ALL_ASSET_FIELDS, SOLE_MONITOR_FIELDS);

    /*
     * device fields
     */
    public static final String[] SOLE_DEVICE_FIELDS = { "device.configuration",
            "device.deviceSubtype.subtypeName" };

    public static final String[] DEVICE_FIELDS = (String[]) ArrayUtils.addAll(
            ALL_ASSET_FIELDS, SOLE_DEVICE_FIELDS);

    /*
     * software fields
     */
    public static final String[] SOLE_SOFTWARE_FIELDS = { "software.version",
            "software.licenseKey", "software.additionalInfo" };

    public static final String[] SOFTWARE_FIELDS = (String[]) ArrayUtils
            .addAll(ALL_ASSET_FIELDS, SOLE_SOFTWARE_FIELDS);

    /*
     * otherAssets fields
     */
    public static final String[] SOLE_OTHERASSETS_FIELDS = { "otherAssets.detail" };

    public static final String[] OTHERASSETS_FIELDS = (String[]) ArrayUtils
            .addAll(ALL_ASSET_FIELDS, SOLE_OTHERASSETS_FIELDS);

}
