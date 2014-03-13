$(document).ready(function() {
	initDataForCopy();
	checkFixed();
	
	$("#cancelCopy").click(function(){
		window.history.back();
	}); 
});

function initDataForCopy(){
	$("#div-loader").show();
	getCommonInfoForAsset();
	initOtherPropertiesForAsset();
	$("#div-loader").hide();
}

function setCommonInfoForAsset(data){
	$("#assetId").val(data.assetId);
	showSpecifyTypeView();
	var siteMs = initDropDownList($("#selectedSite"), data.siteList, false, $("#selectedSite").val(), 300);
	var allEntityMs = initDropDownList($("#selectedEntity"), data.allEntity, false, $("#selectedEntity").val(), 300);
	var assetStatusMs = initDropDownList($("#selectedStatus"), data.assetStatus, false, $("#selectedStatus").val(), 300);
	var assetTypeMs = initDropDownList($("#assetType"), data.assetType, false, $("#assetType").val(), 300);
	var machineTypeMs = initDropDownList($("#machineType"), data.machineTypes, false, $("#machineType").val(), 300);
	var deviceSubtypeMs = initDropDownList($("#deviceSubtypeSelect"), data.deviceSubtypeList, false, $("#deviceSubtypeSelect").val(), 300);
	var projectMs = initDropDownList($("#projectCode"), [], false, $("#projectCodeDefaultValue").val(), 300);
	
	$("#selectedSite").data("selectedSite",siteMs);
	$("#selectedEntity").data("selectedEntity",allEntityMs);
	$("#selectedStatus").data("selectedStatus",assetStatusMs);
	$("#assetType").data("assetType",assetTypeMs);
	$("#machineType").data("machineType",machineTypeMs);
	$("#deviceSubtypeSelect").data("deviceSubtypeSelect",deviceSubtypeMs);
	$("#projectCode").data("projectCode",projectMs);
	
	getLocations("Augmentum " + $("#selectedSite").data("selectedSite").getValue().join());
	
	siteBindEvent();
	entityBindEvent();
	statusBindEvent();
}

function initOtherPropertiesForAsset(){
	getCustomers();
	getUsers();
}

