$(document).ready(function() {
	initDataForEdit();
	checkFixed();
	
	$("#editSubmitForm").click(function() {
		doEditSubmit();
	});
});

function initDataForEdit(){
	$("#div-loader").show();
	getCommonInfoForAsset();
	getUsers();
	$("#div-loader").hide();
}

function doEditSubmit(){
	if(!doEditValidation()){
		return;
	}
	updateSelfDefinedProperties();
	var assetVo = setAssetVoForEdit();
	
	$.ajax({
		type : 'post',
		url : 'asset/update',
		data : assetVo,
		success : function(data) {
			if (data.error != undefined) {
				var errorCode = data.error.toString();
				showMessageBarForMessage(errorCode);
				return false;
			} else {
				window.location.href = "asset/allAssets";
			}
		}
	});
	return true;
}

function doEditValidation(){
	assetNameValidation();
	assetStatusValidation();
	checkInAndOutValidation();
	entityValidation();
	siteValidation();
	roomValidation();
	userValidation();
	
	if(assetNameValidation() && assetStatusValidation() && checkInAndOutValidation() &&
			entityValidation() && siteValidation() && roomValidation() && userValidation()){
		return true;
	}else{
		return false;
	}
}

function setAssetVoForEdit(){
	var assetVo = {};
	
	assetVo.id = $("#id").val();
	assetVo.assetId = $("#assetId").val();
	assetVo.assetName = $("#assetName").val();
	assetVo.type = $("#assetType").val();
	assetVo.barCode = $("#barCode").val();
	assetVo.seriesNo = $("#seriesNo").val();
	assetVo.poNo = $("#poNo").val();
	assetVo.ownerShip = $("#ownership").val();
	assetVo.customerCode = $("#customerCode").val();
	assetVo.customerName = $("#customerName").val();
	assetVo.projectCode = $("#projectCode").val();
	assetVo.projectName = $("#projectName").val();
	assetVo.status = $("#selectedStatus").data("selectedStatus").getValue().join();
	assetVo.checkedInTime = $("#checkedInTime").val();
	assetVo.checkedOutTime = $("#checkedOutTime").val();
	assetVo.keeper = $("#keeperSelect").val();
	assetVo.fixed = $("#fixed").val();
	assetVo.photoPath = $("#photoPath").val();
	assetVo.entity = $("#selectedEntity").data("selectedEntity").getValue().join();
	assetVo.site = $("#selectedSite").data("selectedSite").getValue().join();
	assetVo.location = $("#selectedLocation").data("selectedLocation").getValue().join();
	assetVo.userId = $("#userId").data("userId").getValue().join();
	assetVo.userName = $("#userName").val();
	assetVo.manufacturer = $("#manufacturer").val();
	assetVo.warrantyTime = $("#assetWarranty").val();
	assetVo.vendor = $("#monitorVendor").val();
	assetVo.memo = $("#memo").val();
	assetVo.machineId = $("#machineId").val();
	assetVo.machineSubtype = $("#machineType").data("machineType").getValue().join();
	assetVo.machineSpecification = $("#specification").val();
	assetVo.machineConfiguration = $("#machineConfiguration").val();
	assetVo.machineAddress = $("#machineAddress").val();
	assetVo.monitorId = $("#monitorId").val();
	assetVo.monitorSize = $("#size").val();
	assetVo.monitorDetail = $("#details").val();
	assetVo.deviceId = $("#deviceId").val();
	assetVo.deviceSubtypeName = $("#deviceSubtypeSelect").data("deviceSubtypeSelect").getValue().join();
	assetVo.deviceConfiguration = $("#configuration").val();
	assetVo.softwarecId = $("#softwareId").val();
	assetVo.softwareVersion = $("#version").val();
	assetVo.softwareAdditionalInfo = $("#additionalInfo").val();
	assetVo.softwareLicenseKey = $("#licenseKey").val();
	assetVo.softwareManagerVisible = $("#visible").val();
	assetVo.otherAssetsId = $("#otherAssetsId").val();
	assetVo.otherAssetsDetail = $("#otherAssetsDetails").val();
	
	return assetVo;
}

function updateSelfDefinedProperties(){
	var selfDefinedNames = new Array();
	var selfDefinedIds = new Array();
	var selfDefinedValues = new Array();
    var names="";
	var ids="";
	var values="";
	
	//get self-defined names
	$.each($(".showAsSelfDefine div .selfPropertyName"), function(i, item){
		selfDefinedNames[i] = $(this).text();
		names+=$(this).text()+",";
	});
	$.each($(".showAsSelfDefine div .selfId"), function(i, item){
  	     selfDefinedIds[i] = $(this).val();
  	  ids+=$(this).val()+",";
	});
	//get self-defined values
	$.each($(".showAsSelfDefine div .selfPropertyVlaue"), function(i, item){
		if($(this).val()!=""){
  	   selfDefinedValues[i] = $(this).val();
  	 values+=$(this).val()+",";
		};
	});
	
	if("" == ids){
		return;
	}else{
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			url : 'customizedPropery/updateSelfDefinedProperties',
			dataType : 'json',
			data:{
				'assetId':$("#id").val(),
				'selfDefinedNames' : names,
				'selfDefinedIds' : ids,
				'selfDefinedValues' : values
			},
			success : function(data) {
				console.log(data);
			}
		});
	}
}

function setCommonInfoForAsset(data){
	showSpecifyTypeView();
	var siteMs = initDropDownList($("#selectedSite"), data.siteList, false, $("#selectedSite").val(), 300);
	var allEntityMs = initDropDownList($("#selectedEntity"), data.allEntity, false, $("#selectedEntity").val(), 300);
	var assetStatusMs = initDropDownList($("#selectedStatus"), data.assetStatus, false, $("#selectedStatus").val(), 300);
	var machineTypeMs = initDropDownList($("#machineType"), data.machineTypes, false, $("#machineType").val(), 300);
	var deviceSubtypeMs = initDropDownList($("#deviceSubtypeSelect"), data.deviceSubtypeList, false, $("#deviceSubtypeSelect").val(), 300);
	
	$("#selectedSite").data("selectedSite",siteMs);
	$("#selectedEntity").data("selectedEntity",allEntityMs);
	$("#selectedStatus").data("selectedStatus",assetStatusMs);
	$("#machineType").data("machineType",machineTypeMs);
	$("#deviceSubtypeSelect").data("deviceSubtypeSelect",deviceSubtypeMs);
	
	getLocations("Augmentum " + $("#selectedSite").data("selectedSite").getValue().join());
	
	siteBindEvent();
	entityBindEvent();
	statusBindEvent();
}
