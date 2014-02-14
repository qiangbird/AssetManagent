function validateAssetForm(){
    
    var validations = new Array();
    var validation = "success";

    var assetName = $("#assetName").val();
    var ownership = $("#ownership").val();
    var customerName = $("#customerName").val();
    var maxUseNum = $("#maxUseNum").val();
    var checkedInTime = $("#checkedInTime").val();
    var checkedOutTime = $("#checkedOutTime").val();
    var assetStatus = $("#selectedStatusList").find("span:first").text();
    var assetUser = $("#assetUser").val();
    if(entityType = "Asset"){
    	validations.push($("#assetName").validateNull(assetName,"AssetName should not be null !"));
    	validations.push($("#ownership").validateNull(ownership,"Ownership should not be null and must be a customer !"));
    	validations.push($("#customerName").validateNull(customerName,"CustomerName should not be null and must be a customer!"));
    	validations.push($("#maxUseNum").validateNum(maxUseNum,"Max User Num must be a number and cannot be null !"));
    	
    	if("" != checkedInTime && "" != checkedOutTime){
    	validations.push($("#checkedOutTime").validateDate(checkedOutTime,checkedInTime,"Check out time should larger than check in time !"));
    	}
    	if("IN_USE" == assetStatus){
    	if("" == assetUser || $("#assetUser").hasClass("input-text-error")){
    		$("#assetUser").push($("#assetUser").validateNull(assetUser,"Asset user cannot be null and must be a employee name !"));
    	}
    	}else{
    		if($("#assetUser").hasClass("input-text-error")){
        		$("#assetUser").push($("#assetUser").validateNull(assetUser,"Asset must be a employee name !"));
        	}
    	}
    	validations.push("failed");
    } 
    
    validation = recordFailInfo(validations);

    return validation;
}

function clearValidationMessage(){
	$(".validation_fail").removeClass("validation_fail").poshytip('destroy');
}

