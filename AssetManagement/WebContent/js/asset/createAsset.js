$(document).ready(function() {
	initDataForCreate();
});

function initDataForCreate(){
	$("#div-loader").show();
	getCommonInfoForAsset();
	initOtherPropertiesForAsset();
	$("#div-loader").hide();
}

function setCommonInfoForAsset(data){
	$("#assetId").val(data.assetId);
	var siteMs = initDropDownList($("#selectedSite"), data.siteList, false, "Shanghai", 300);
	var allEntityMs = initDropDownList($("#selectedEntity"), data.allEntity, false, "群硕上海", 300);
	var assetStatusMs = initDropDownList($("#selectedStatus"), data.assetStatus, false, "AVAILABLE", 300);
	var assetTypeMs = initDropDownList($("#assetType"), data.assetType, false, "DEVICE", 300);
	var machineTypeMs = initDropDownList($("#machineType"), data.machineTypes, false, undefined, 300);
	var deviceSubtypeMs = initDropDownList($("#deviceSubtypeSelect"), data.deviceSubtypeList, false, undefined, 300);
	var projectMs = initDropDownMap($("#projectCode"), [], false, "label", "label");
	
	$("#selectedSite").data("selectedSite",siteMs);
	$("#selectedEntity").data("selectedEntity",allEntityMs);
	$("#selectedStatus").data("selectedStatus",assetStatusMs);
	$("#assetType").data("assetType",assetTypeMs);
	$("#machineType").data("machineType",machineTypeMs);
	$("#deviceSubtypeSelect").data("deviceSubtypeSelect",deviceSubtypeMs);
	$("#projectCode").data("projectCode",projectMs);
	
	$("#deviceDetails").show().siblings().hide();
	getLocations("Augmentum Shanghai");
	
	siteBindEvent();
	entityBindEvent();
	statusBindEvent();
	assetTypesBindEvent();
	machineTypeBindEvent();
}

function initOtherPropertiesForAsset(){
	getCustomers();
	getUsers();
}
