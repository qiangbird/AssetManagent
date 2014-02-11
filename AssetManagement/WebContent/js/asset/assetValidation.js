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
    	}
    	validations.push(validateFromProjectEqToProject("create"));
    } /*else if (rotationType == "Assignment"){
    	validations.push($("#toCustomer").validateNull($("#toCustomer").data("toCustomer").getValue().join()));
    	validations.push($("#toProject").validateNull($("#toProject").data("toProject").getValue().join()));
    	validations.push($("#toBubbleType").validateNull($("#toBubbleType").data("toBubbleType").getValue().join()));
    	validations.push($(".employee_list").validateNull(employees));
    	validations.push($("#planTime").validateNull(planTime));
    	validations.push($("#reason").validateLength(reason, 100));
    } else {
    	validations.push($("#fromCustomer").validateNull($("#fromCustomer").data("fromCustomer").getValue().join()));
    	validations.push($("#fromProject").validateNull($("#fromProject").data("fromProject").getValue().join()));
    	validations.push($(".employee_list").validateNull(employees));
    	validations.push($("#planTime").validateNull(planTime));
    	validations.push($("#reason").validateLength(reason, 100));
    }*/
    
    validation = recordFailInfo(validations);

    return validation;
}

function clearValidationMessage(){
	$(".validation_fail").removeClass("validation_fail").poshytip('destroy');
}
//Edit rotation.
function validateEditRotation(){
    var validations = new Array();
    var validation = "success";
    
    if($("#rotationType").val() != "ROTATION_OUT"){
    	validations.push($("#toCustomerText").validateNull($("#toCustomerText").data("toCustomer").getValue().join()));
		validations.push($("#toProjectText").validateNull($("#toProjectText").data("toProject").getValue().join()));
		validations.push($("#toBubbleType").validateNull($("#toBubbleTypeText").data("toBubbleType").getValue().join()));
		
		if($("#rotationType").text() == "ROTATION"){
		    validations.push(validateFromProjectEqToProject("edit"));
		}
    }
    if($("#rotationStatus").text() == "REQUEST"){
    	validations.push($("#planTimeText").validateNull($("#planTimeText").val()));
    }
    validations.push($("#reason").validateLength($("#reason").val(), 100));
    validation = recordFailInfo(validations);

    return validation;
}

function validateFromProjectEqToProject(flag){
	return "failed";
}

