$(document).ready(function(){
//about datepicker
   $("#checkedInTime, #checkedOutTime, #assetWarranty, .l-date").datepicker({
	      changeMonth : true,
	      changeYear : true,
	      dateFormat : "yy-mm-dd"
	});
   
   $(".showAsSelfDefine").find(".asset-input-panel").each(function(){
	  $(this).find(".selfPropertyName").each(function(){
		  var length;
		  if ($(this).html().charCodeAt(0) > 255) {
			  length = 10;
		  } else {
			  length = 20;
		  }
		  if ($(this).html().length > length) {
			  $(this).poshytip({
				  className: 'tip-green',
				  allowTipHover: true,
				  content: $(this).html()
			  });
		  }
	   });
   });
   
   $(".fixedCheckBox").click(function(){
   	if("radioCheckOff" == $(this).find("a").attr("class")){
   		$(this).parents(".radioBoxes").find(".radioCheckOn").attr("class","radioCheckOff");
   		$(this).find("a").attr("class","radioCheckOn");
   		$("#fixed").val($(this).find("a").attr("id"));
   	}
   });
	   
   //upload image
   $("#file").change(
	   function() {
	   $("#uploadForm").ajaxSubmit(
	   {
	       type : 'post',
	       url : 'upload/image',
	       data : $("#uploadForm")
	               .formSerialize(),
	       success : function(data) {
	           console.log(data);
	           $("#aphoto").attr("src", data);
	           $("#photoPath").val(data);
	           return false;
	       },
	       error : function(data,
	           XmlHttpRequest, textStatus,
	           errorThrown) {
	           console.log(data);
	           return false;
	               }
	       });
	   });
   
  //Batch create
   $(".batchCheckBoxOff").click(function(){
	   if("batchCheckBoxOff" == $(this).attr("class")){
			$(this).removeClass("batchCheckBoxOff");
			$(this).addClass("batchCheckBoxOn");
		}else{
			$(this).removeClass("batchCheckBoxOn");
			$(this).addClass("batchCheckBoxOff");
		}
	   $("#showBatch").removeClass().addClass("showBatchHovered");
	   if ("batchCheckBoxOn" == $("#batchCreate").attr("class")) {
		   $("#showBatch").removeClass().addClass("showBatchNormal");
		   $("#isBatchCreate").val(true);
		   $("#showBatch").show();
	   } else {
	       $("#showBatch").removeClass().addClass("showBatchNormal");
	       $("#showBatch").val("1").hide();
	   }
   });
   $(".visibleCheckBoxOff").click(function(){
	   if("visibleCheckBoxOff" == $(this).attr("class")){
			$(this).removeClass("visibleCheckBoxOff");
			$(this).addClass("visibleCheckBoxOn");
			
			$("#visible").attr("value",true);
		}else{
			$(this).removeClass("visibleCheckBoxOn");
			$(this).addClass("visibleCheckBoxOff");
			
			$("#visible").attr("value",false);
		}
   });
	$("#showBatch").blur(function() {
	    if ($(this).val() == ""|| !numberCheck($(this).val().trim())) {
	$("#showBatch").removeClass("showBatchNormal showBatchHovered").addClass("showBatchError");
	} else {
	    $("#showBatch").removeClass().addClass("showBatchNormal");
	}
	});
	
	$("#assetName").blur(function(){
		assetNameValidation();
	});
	$("#main").delegate("#assetType input:first", "blur", function(){
		assetTypeValidation();
	});
	$("#main").delegate("#ownership input:first", "blur", function(){
		owershipValidation();
	});
	$("#main").delegate("#customerCode input:first", "blur", function(){
		usedByValidation();
	});
	$("#main").delegate("#selectedStatus input:first", "blur", function(){
		assetStatusValidation();
	});
	//Compare check-in and check-out time
	$("#checkedOutTime").change(function() {
		checkInAndOutValidation();
	});
	$("#main").delegate("#selectedEntity input:first", "blur", function(){
		entityValidation();
	});
	$("#main").delegate("#selectedSite input:first", "blur", function(){
		siteValidation();
	});
	$("#main").delegate("#selectedLocation input:first", "blur", function(){
		roomValidation();
	});
	$("#main").delegate("#userId input:first", "blur", function(){
		userValidation();
	});
	$("#main").delegate("#machineType input:first", "blur", function(){
		machineTypeValidation();
	});
	
	$("#submitForm").click(function() {
		if(!successValidation()){
			return;
		}
		var assetVo = setAssetVo();
		
		  $.ajax({
				   type : 'POST',
				   url : 'asset/saveAsset',
				   data : assetVo,
				   success : function(data) {
				    	if(data.error != undefined){
					    	var errorCode = data.error.toString();
					        showMessageBarForMessage(errorCode);
					        return false;
				    	}else{
				    		window.location.href = "asset/allAssets";
				    	}
				    }
			});
	});
});

//show as type
function assetTypesBindEvent(){
	var assetTypeMs  = $("#assetType").data("assetType");
	$(assetTypeMs).on('selectionchange', function(event, combo, selection){
        var assetType = assetTypeMs.getValue().join();
        showSpecifyTypeView(assetType);
    });
}

function showSpecifyTypeView(type){
	var assetType;
	if(undefined == type){
		assetType = $("#assetType").val();
	}else{
		assetType = type;
	}
	
	if (assetType.trim() == 'MACHINE') {
        $("#machineDetails").show().siblings().hide();
     } else if (assetType.trim() == 'MONITOR') {
        $("#monitorDetails").show().siblings().hide();
     } else if (assetType.trim() == 'DEVICE') {
        $("#deviceDetails").show().siblings().hide();
     } else if (assetType.trim() == 'SOFTWARE') {
        $("#softwareDetails").show().siblings().hide();
     } else if (assetType.trim() == "OTHERASSETS") {
        $("#otherAssetsDetails").show().siblings().hide();
     }
}

function assetNameValidation(){
	var assetName = $("#assetName").val();
	return validateValueIsEmpty($("#assetName"), assetName, "AssetName is null!");
}

function assetTypeValidation(){
	var assetType = $("#assetType").data("assetType").getValue().join();	
	return validateValueIsEmpty($("#assetType"), assetType, "Asset type is null or not a right owership!");
}

function owershipValidation(){
	var ownership = $("#ownership").data("ownership").getValue().join();	
	return validateValueIsEmpty($("#ownership"), ownership, "Ownership is null or not a right owership!");
}

function usedByValidation(){
	var customerCode = $("#customerCode").data("customerCode").getValue().join();	
	return validateValueIsEmpty($("#customerCode"), customerCode, "Customer is null or not a right customer!");
}

function assetStatusValidation(){
	var status = $("#selectedStatus").data("selectedStatus").getValue().join();	
	
	if (validateValueIsEmpty($("#selectedStatus"), status, "Status is null or not a right status!")) {
		if(status=="AVAILABLE"){
			$("#userId").data("userId").clear();
		}
		return true;
	}else{
		return false;
	}
}

function checkInAndOutValidation(){
	var checkIn = $("#checkedInTime").val();
	var checkOut = $("#checkedOutTime").val();
	
	if (checkIn != "") {
		if (!dateCompare(checkIn, checkOut)) {
			addErrorStyle($("#checkedOutTime"), "The checkOutTime must be greater than the checkInTime");
			return false;
		} else {
			removeErrorStyle($("#checkedOutTime"));
			return true;
		}
	}else{
		return true;
	}
}

function entityValidation(){
	var selectedEntity = $("#selectedEntity").data("selectedEntity").getValue().join();	
	return validateValueIsEmpty($("#selectedEntity"), selectedEntity, "Entity is null or not a right entity!");
}

function siteValidation(){
	var selectedSite = $("#selectedSite").data("selectedSite").getValue().join();	
	return validateValueIsEmpty($("#selectedSite"), selectedSite, "Entity is null or not a right entity!");
}

function roomValidation(){
	var selectedLocation = $("#selectedLocation").data("selectedLocation").getValue().join();	
	return validateValueIsEmpty($("#selectedLocation"), selectedLocation, "Room is null or not a right room!");
}

function userValidation(){
	var userId = $("#userId").data("userId").getValue().join();	
	var selectedStatus = $("#selectedStatus").data("selectedStatus").getValue().join();	
	
	if("" != userId){
		var arrayValue = new Array();
		arrayValue.push("IN_USE");
		
		$("#selectedStatus").data("selectedStatus").clear();
		$("#selectedStatus").data("selectedStatus").setValue(arrayValue);
		return true;
	} else if("IN_USE" == selectedStatus){
		return validateValueIsEmpty($("#userId"), userId, "User is null or not exist!");
	} else {
		return true;
	}
}

function machineTypeValidation(){
	var assetType = $("#assetType").data("assetType").getValue().join();	
	var machineType = $("#machineType").data("machineType").getValue().join();	
	
	if (assetType == "MACHINE" && "" == machineType) {
		addErrorStyle($("#machineType"), "Machine subtype is null or not a right type!");
		return false;
	}else{
		removeErrorStyle($("#machineType"));
		return true;
	}
}

function successValidation(){
	assetNameValidation();
	assetTypeValidation();
	machineTypeValidation();
	owershipValidation();
	usedByValidation();
	assetStatusValidation();
	checkInAndOutValidation();
	entityValidation();
	siteValidation();
	roomValidation();
	userValidation();
	machineTypeValidation();
	
	if(assetNameValidation() && assetTypeValidation() && machineTypeValidation() && owershipValidation() &&
			usedByValidation() && assetStatusValidation() && checkInAndOutValidation() && entityValidation() &&
			siteValidation() && roomValidation() && userValidation() && machineTypeValidation()){
		return true;
	}else{
		return false;
	}
}

function setAssetVo(){
	var assetVo = {};
	
	assetVo.batchCreate = $("#isBatchCreate").val();
	assetVo.batchCount = $("#showBatch").val();
	assetVo.assetId = $("#assetId").val();
	assetVo.assetName = $("#assetName").val();
	assetVo.type = $("#assetType").data("assetType").getValue().join();
	assetVo.barCode = $("#barCode").val();
	assetVo.seriesNo = $("#seriesNo").val();
	assetVo.poNo = $("#poNo").val();
	assetVo.ownerShip = $("#ownership").data("ownership").getValue().join();
	assetVo.customerCode = $("#customerCode").data("customerCode").getValue().join();
	assetVo.customerName = $("#customerName").val();
	assetVo.projectCode = $("#projectCode").data("projectCode").getValue().join();
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

function getCommonInfoForAsset(){
	$.ajax({
		    type : 'GET',
		    contentType : 'application/json',
		    url : 'asset/createAsset',
		    dataType : 'json',
		    success : function(data) {
		    	setCommonInfoForAsset(data);
		    }
	});
}

function siteBindEvent(){
	var siteMs = $("#selectedSite").data("selectedSite");
	$(siteMs).on('selectionchange', function(event, combo, selection) {
		siteValidation();
		
		var site = siteMs.getValue().join();
		var currentSite = "Augmentum " + site;
		getLocations(currentSite);
	});
}

function getLocations(currentSite){
 	 $.ajax({
 		    type : 'GET',
 		    contentType : 'application/json',
 		    url : 'location/getLocationRoom?currentSite='+currentSite,
 		    dataType : 'json',
 		    success : function(data) {
 		    	var rooms = [];
  		        length = data.locationRoomList.length;
  		        
  		        for ( var i = 0; i < length; i++) {
  		        	rooms[i] = data.locationRoomList[i];
   	        }
 		    	var locationMs = initDropDownList($("#selectedLocation"), rooms, false, $("#room").val(), 300);
 		    	$("#selectedLocation").data("selectedLocation",locationMs);
 		    	
 		    	roomBindEvent();
 		    }
 });
}

function roomBindEvent(){
	var selectedLocationMs = $("#selectedLocation").data("selectedLocation");
	$(selectedLocationMs).on('selectionchange', function(event, combo, selection) {
		roomValidation();
	});
}

function entityBindEvent(){
	var allEntityMs = $("#selectedEntity").data("selectedEntity");
	$(allEntityMs).on('selectionchange', function(event, combo, selection) {
		entityValidation();
	});
}

function statusBindEvent(){
	var assetStatusMs = $("#selectedStatus").data("selectedStatus");
	$(assetStatusMs).on('selectionchange', function(event, combo, selection) {
		assetStatusValidation();
	});
}

function machineTypeBindEvent(){
	var machineTypeMs = $("#machineType").data("machineType");
	$(machineTypeMs).on('selectionchange', function(event, combo, selection) {
		machineTypeValidation();
	});
}

function getUsers(){
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'user/getEmployeeDataSource',
        dataType : 'json',
        success : function(data) {
        	var employeeMs = initDropDownMap($("#userId"), data.employeeLabel, false, "label", "value", $("#userIdDefaultValue").val());
    		$("#userId").data("userId",employeeMs);
    		
    		employeeBindEvent();
        },
        });
}

function employeeBindEvent(){
	var employeeMs = $("#userId").data("userId");
	$(employeeMs).on('selectionchange', function(event, combo, selection){
		var selectedItems = $("#userId").data("userId").getSelectedItems();
		
		if(0 < selectedItems.length){
			var userName = selectedItems[0].label;
			$("#userName").val(userName);
		}else{
			$("#userName").val("");
		}
		
		userValidation();
    });
}

function getCustomers(){
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'customer/getCustomerInfo',
        dataType : 'json',
        success : function(data) {
        	var ownerShipMS = initDropDownMap($("#ownership"), data.customerList, false, "label", "label", $("#ownership").val());
        	$("#ownership").data("ownership",ownerShipMS);
        	
            var customerMs = initDropDownMap($("#customerCode"), data.customerList, false, "label", "value", $("#customerCodeDefaultValue").val());
            $("#customerCode").data("customerCode",customerMs);
            
            ownerShipBindEvent();
            customerBindEvent();
         }
      });
}

function ownerShipBindEvent(){
	var ownerShipMS = $("#ownership").data("ownership");
	$(ownerShipMS).on('selectionchange', function(event, combo, selection){
		owershipValidation();
    });
}

function customerBindEvent(){
	var customerMs = $("#customerCode").data("customerCode");
	$(customerMs).on('selectionchange', function(event, combo, selection){
		var selectedItems = $("#customerCode").data("customerCode").getSelectedItems();
		
		if(0 < selectedItems.length){
			var customerName = selectedItems[0].label;
			$("#customerName").val(customerName);
		}else{
			$("#customerName").val("");
		}
		
		usedByValidation();
		
		var customerCode = $("#customerCode").data("customerCode").getValue().join();
		
		if("" == customerCode){
			return;
		}else{
			getProjectsByCustomer(customerCode);
		}
    });
}

function getProjectsByCustomer(customerCode){
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'project/getProjectByCustomerCode',
        data : {
        	customerCode:customerCode
        },
        dataType : 'json',
        success : function(data) {
        	var projectMs = initDropDownMap($("#projectCode"), data.projectList, false, "label", "value");
        	$("#projectCode").data("projectCode",projectMs);
        	
        	var projectManagerNames = [];
        	projectManagerNames = data.projectManager.del();
        	setValueOfKeeper(projectManagerNames.join("; "));
        	
        	projectBindEvent(data);
        },
        error : function() {
        	showMessageBarForOperationResultMessage("error");
        }
    });
}

function projectBindEvent(data){
	var projectMs = $("#projectCode").data("projectCode");
	$(projectMs).on('selectionchange', function(event, combo, selection){
        var projectCode = projectMs.getValue().join();
        var selectedItems = $("#projectCode").data("projectCode").getSelectedItems();
        
        if(0 < selectedItems.length){
        	var projectName = selectedItems[0].label;
    		$("#projectName").val(projectName);
        }else{
        	$("#projectName").val("");
        }
        
        
        var projectCodes = [];
        
        for(var i = 0; i < data.projectList.length; i++){
        	projectCodes.push(data.projectList[i]);
        }
        
        var index = getIndexInArr(projectCodes,projectCode);
        var projectManager = data.projectManager[index];
        
        setValueOfKeeper(projectManager);
    });
}

function setValueOfKeeper(projectManagerNames){
	// 00900 means Augmentum
	var customerCode = $("#customerCode").data("customerCode").getValue().join();
	if (customerCode == "00900") {
        $("#keeperSelect").val("Ping Zhou");
    } else {
        $("#keeperSelect").val(projectManagerNames);
    }
}

function checkFixed(){
	if("true" == $("#fixed").val()){
		$("#false").attr("class","radioCheckOff");
		$("#true").attr("class","radioCheckOn");
	}else{
		$("#false").attr("class","radioCheckOn");
		$("#true").attr("class","radioCheckOff");
	}
}
