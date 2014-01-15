package com.augmentum.ams.util;


/**
 * Used to format search and filter conditions
 * @author Geoffrey.Zhao
 *
 */
public class SearchFieldHelper {

    public static String[] getAssetFields() {
        String[] fieldNames = new String[18];
        fieldNames[0] = "assetId";
        fieldNames[1] = "assetName";
        fieldNames[2] = "user.userName";
        fieldNames[3] = "project.projectName";
        fieldNames[4] = "customer.customerName";
        fieldNames[5] = "poNo";
        fieldNames[6] = "barCode";
        fieldNames[7] = "entity";
        fieldNames[8] = "keeper";
        fieldNames[9] = "manufacturer";
        fieldNames[10] = "memo";
        fieldNames[11] = "ownerShip";
        fieldNames[12] = "seriesNo";
        fieldNames[13] = "status";
        fieldNames[14] = "type";
        fieldNames[15] = "vendor";
        fieldNames[16] = "location.site";
        fieldNames[17] = "location.room";
        
        return fieldNames;
    }
    
    public static String[] getSentenceFields() {
        String[] sentenceFields = new String[10];
        sentenceFields[0] = "manufacturer";
        sentenceFields[1] = "seriesNo";
        sentenceFields[2] = "user.userName";
        sentenceFields[3] = "project.projectName";
        sentenceFields[4] = "customer.customerName";
        sentenceFields[5] = "ownerShip";
        sentenceFields[6] = "memo";
        sentenceFields[7] = "vendor";
        sentenceFields[8] = "entity";
        sentenceFields[9] = "assetName";
        
        return sentenceFields;
    }
    
    public static String getAssetStatus() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AVAILABLE");
        stringBuilder.append(",");
        stringBuilder.append("IDLE");
        stringBuilder.append(",");
        stringBuilder.append("IN_USE");
        stringBuilder.append(",");
        stringBuilder.append("BORROWED");
        stringBuilder.append(",");
        stringBuilder.append("RETURNED");
        stringBuilder.append(",");
        stringBuilder.append("BROKEN");
        stringBuilder.append(",");
        stringBuilder.append("WRITE_OFF");
        stringBuilder.append(",");
        stringBuilder.append("ASSIGNING");
        stringBuilder.append(",");
        stringBuilder.append("RETURNING_TO_IT");
        stringBuilder.append(",");
        stringBuilder.append("RETURNING_TO_CUSTOMER");
        return stringBuilder.toString();
    }
    
    public static String getAssetType() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DEVICE");
        stringBuilder.append(",");
        stringBuilder.append("MACHINE");
        stringBuilder.append(",");
        stringBuilder.append("SOFTWARE");
        stringBuilder.append(",");
        stringBuilder.append("MONITOR");
        stringBuilder.append(",");
        stringBuilder.append("OTHERASSETS");
        return stringBuilder.toString();
    }

	public static String[] getCustomerGroupFields() {
		String[] sentenceFields = new String[4];
        sentenceFields[0] = "groupName";
        sentenceFields[1] = "description";
        sentenceFields[2] = "processType";
        sentenceFields[3] = "customers";
        
        return sentenceFields;
	}
    
}
