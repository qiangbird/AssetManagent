$(document).ready(function(){
	
	// get returned asset panel
	function getReturnedAsset() {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			url: "todo/viewReturnedAssetPanel",
			success: function(data){
				var todoList = data.todoList;
				for (var i = 0; i < todoList.length; i++) {
					$(".returnedAssetPanel table").append("<tr><td><input class='returnedAssetId' type='checkbox' value=" 
							+ todoList[i].id + " /></td><td>" + todoList[i].assetName + "</td>" + "<td>" 
							+ todoList[i].customerName + "</td>" + "<td>" + todoList[i].returnedTime 
							+ "</td></tr>");
				}
			}
		});
	}
	
	getReturnedAsset();
	
	$("#returnedAsset").click(function(){
		$.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'todo/confirmReturnedAsset',
            dataType : 'json',
            data: {
                ids: getActivedAssetIds(".returnedAssetId:checked"),
            },
            success : function(){
            	$(".returnedAssetPanel table tr:gt(0)").remove();
            	getReturnedAsset();
            }
        });
	});
	
	$("#viewMore_returnedAsset").click(function(){
		window.location.href = "todo/redirectTodoList?todoFlag=returned";
	});
	
	// get received asset panel
	function getReceivedAsset() {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			url: "todo/viewReceivedAssetPanel",
			success: function(data){
				var todoList = data.todoList;
				for (var i = 0; i < todoList.length; i++) {
					$(".receivedAssetPanel table").append("<tr><td><input class='receivedAssetId' type='checkbox' value=" 
							+ todoList[i].id + " /></td><td>" + todoList[i].assetName + "</td>" + "<td>" 
							+ todoList[i].customerName + "</td>" + "<td>" + todoList[i].receivedTime 
							+ "</td></tr>");
				}
			}
		});
	}
	
	getReceivedAsset();
	
	$("#receivedAsset").click(function(){
		$.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'todo/confirmReceivedAsset',
            dataType : 'json',
            data: {
                ids: getActivedAssetIds(".receivedAssetId:checked"),
            },
            success : function(){
            	$(".receivedAssetPanel table tr:gt(0)").remove();
            	getReceivedAsset();
            }
        });
	});
	
	$("#viewMore_receivedAsset").click(function(){
		window.location.href = "todo/redirectTodoList?todoFlag=received";
	});
	
	// get idle asset panel
	function getIdleAsset() {
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			url: "asset/viewIdleAssetPanel",
			success: function(data){
				
				if (data.idleAssetList == null) {
					return;
				} else {
					
					var assetList = data.idleAssetList;
					for (var i = 0; i < assetList.length; i++) {
						$(".idleAssetPanel table").append("<tr><td><input class='idleAssetId' type='checkbox' value=" 
								+ assetList[i].id + " /></td><td>" + assetList[i].assetName + "</td>" + "<td>" + assetList[i].customerName 
								+ "</td>" + "<td>" + assetList[i].userName + "</td></tr>");
					}
				}
			}
		});
	}
	
	getIdleAsset();
	
	$("#idleAsset").click(function(){
		alert();
	});
	
	$("#viewMore_idleAsset").click(function(){
		window.location.href = "customerAsset/listAllCustomerAssets?status=IDLE";
	});
	
	// get warranty expired asset panel
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		dataType : 'json',
		url: "asset/viewWarrantyExpiredAssetPanel",
		success: function(data){
			var assetList = data.warrantyExpiredAssetList;
			for (var i = 0; i < assetList.length; i++) {
				$(".warrantyExpiredPanel table").append("<tr><td>" + assetList[i].assetId + "</td><td>" 
						+ assetList[i].assetName + "</td><td>" + assetList[i].customerName 
						+ "</td><td>" + assetList[i].warrantyTime + "</td></tr>");
			}
		}
	});
	
	$("#viewMore_warrantyExpired").click(function(){
		window.location.href = "asset/allAssets?isWarrantyExpired=true";
	});
	
	// get software license expired asset panel
	$.ajax({
		type : 'GET',
		contentType : 'application/json',
		dataType : 'json',
		url: "asset/viewLicenseExpiredAssetPanel",
		success: function(data){
			var assetList = data.licenseExpiredAssetList;
			for (var i = 0; i < assetList.length; i++) {
				$(".licenseExpiredPanel table").append("<tr><td>" + assetList[i].assetId + "</td><td>" 
						+ assetList[i].assetName + "</td><td>" + assetList[i].customerName 
						+ "</td><td>" + assetList[i].licenseExpiredTime + "</td></tr>");
			}
		}
	});
	
	$("#viewMore_licenseExpired").click(function(){
		window.location.href = "asset/allAssets?isLicenseExpired=true";
	});
	
	
	// generate link and parameters for IT number   --------------------------------- start
	function formatType(type) {
		if (type == "Other") {
			type = "OTHERASSETS";
		} else if (type == "Device" || type == "Machine"
					|| type == "Monitor" || type == "Software") {
			type = type.toUpperCase();
		} else {
			type = "";
		}
		return type;
	}
	
	function formatStatus(status) {
		if (status == "In Use") {
			status = "IN_USE";
		} else if (status == "Write Off") {
			status = "WRITE_OFF";
		} else if (status == "Available" || status == "Returned"
					|| status == "Borrowed" || status == "Broken") {
			status = status.toUpperCase();
		} else {
			status = "";
		}
		return status;
	}
	
	function addLinkForITNum($element, posY, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var status = $element.siblings("td:eq(0)").html();
			var type = $element.parent().siblings("tr:eq(0)").children("th:eq(" + posY + ")").html();
			
			status = formatStatus(status);
			type = formatType(type);
			
			$element.html("<a href='asset/allAssets?type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	// ------------------------------------------------------------------------  end
	
	
	// generate link and parameters for My Asset number
	function addLinkForMyAssetNum($element, posY, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = $element.parent().siblings("tr:eq(0)").children("th:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='asset/listMyAssets?type=" + type + "'>" + count + "</a>");
		}
	}
	
	// generate link and parameters for fixed Asset number
	function addLinkForFixedAssetNum($element, posY, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = $element.parent().siblings("tr:eq(0)").children("th:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='asset/allAssets?isFixedAsset=true&type=" + type + "'>" + count + "</a>");
		}
	}
	
	// get asset count for IT
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "asset/getAssetCountForPanel",
        success: function(data){
        	
        	$(".mainPanel table tr:gt(0)").each(function(i){
        		
        		// get first column name: All Asset, My Asset, Customer Asset, Available, etc...
        		var type = $(this).children("td:eq(0)").html();
        		
        		// all asset line
        		if ("All Assets" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allAssetMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allAssetMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allAssetSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allAssetDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allAssetOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allAssetTotal);
        		} 
        		//my asset line
        		else if ("My Assets" == type) {
        			addLinkForMyAssetNum($(this).children("td:eq(1)"), 1, data.assetCount.myAssetMachineCount);
        			addLinkForMyAssetNum($(this).children("td:eq(2)"), 2, data.assetCount.myAssetMonitorCount);
        			addLinkForMyAssetNum($(this).children("td:eq(3)"), 3, data.assetCount.myAssetSoftwareCount);
        			addLinkForMyAssetNum($(this).children("td:eq(4)"), 4, data.assetCount.myAssetDeviceCount);
        			addLinkForMyAssetNum($(this).children("td:eq(5)"), 5, data.assetCount.myAssetOtherAssetsCount);
        			addLinkForMyAssetNum($(this).children("td:eq(6)"), 6, data.assetCount.myAssetTotal);
        		}
        		// fixed asset line
        		else if ("Fixed" == type) {
        			addLinkForFixedAssetNum($(this).children("td:eq(1)"), 1, data.assetCount.fixedAssetMachineCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(2)"), 2, data.assetCount.fixedAssetMonitorCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(3)"), 3, data.assetCount.fixedAssetSoftwareCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(4)"), 4, data.assetCount.fixedAssetDeviceCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(5)"), 5, data.assetCount.fixedAssetOtherAssetsCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(6)"), 6, data.assetCount.fixedAssetTotal);
        		}
        		// available asset line
        		else if ("Available" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allAvailableMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allAvailableMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allAvailableSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allAvailableDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allAvailableOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allAvailableAssetTotal);
        		}
        		// in_use asset line
        		else if ("In Use" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allInUseMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allInUseMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allInUseSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allInUseDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allInUseOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allInUseAssetTotal);
        		}
        		// returned asset line
        		else if ("Returned" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allReturnedMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allReturnedMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allReturnedSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allReturnedDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allReturnedOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allReturnedAssetTotal);
        		}
        		// borrowed asset line
        		else if ("Borrowed" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allBorrowedMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allBorrowedMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allBorrowedSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allBorrowedDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allBorrowedOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allBorrowedAssetTotal);
        		}
        		// broken asset line
        		else if ("Broken" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allBrokenMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allBrokenMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allBrokenSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allBrokenDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allBrokenOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allBrokenAssetTotal);
        		}
        		// write_off asset line
        		else if ("Write Off" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allWriteOffMachineCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allWriteOffMonitorCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allWriteOffSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allWriteOffDeviceCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allWriteOffOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allWriteOffAssetTotal);
        		}
        	});
        }
	});
	
	
	// generate link and parameters for customer Asset number
	function addLinkForCustomerNum($element, posY, customerCode, status, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = $element.parent().siblings("tr:eq(0)").children("th:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='customerAsset/listCustomerAsset?customerCode=" + customerCode 
					+ "&type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	
	// generate link and parameters for customer Asset number
	function addLinkForAllCustomerNum($element, posY, status, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = $element.parent().siblings("tr:eq(0)").children("th:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='customerAsset/listAllCustomerAssets?type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	
	// get asset count for manager
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "asset/getAssetCountForManager",
        success : function(data){
        	
        	if (data.customer == null || data.assetCount == null) {
        		return;
        	} else {
        		
        		$(".mainPanel table tr:gt(0)").each(function(i){
        			
        			// get first column name: All Asset, My Asset, Customer Asset, Available, etc...
        			var type = $(this).children("td:eq(0)").attr("content");
        			
        			if ("Customer Asset" == type) {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, "", data.assetCount.allCustomersMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, "", data.assetCount.allCustomersMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, "", data.assetCount.allCustomersSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, "", data.assetCount.allCustomersDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, "", data.assetCount.allCustomersOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, "", data.assetCount.allCustomersAssetTotal);
        				
        				//append each customer asset
        				for (var i = 0; i < data.customer.length; i++) {
        					var customerName = data.customer[i].customerName;
        					var customerCode = data.customer[i].customerCode;
        					
        					var deviceCount = customerName + "DeviceCount";
        					var machineCount = customerName + "MachineCount";
        					var monitorCount = customerName + "MonitorCount";
        					var otherAssetsCount = customerName + "OtherAssetsCount";
        					var softwareCount = customerName + "SoftwareCount";
        					var total = customerName + "AssetTotal";
        					
        					$(this).after("<tr class='afterTR' content='" + customerCode + "'><td><div class='tr-header treeIndent' title='" 
        							+ customerName + "'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				$(".afterTR").hide();
        				$(".afterTR").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, "", $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, "", $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, "", $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, "", $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, "", $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, "", $(this).children("td:eq(6)").html());
        				});
        				
        				
        				$(this).children("td:eq(0)").bind("click", function(){
        					$(this).parent().siblings(".afterTR").toggle();
        					changeTreeIcon($(this).children("div"));
        				});
        			} 
        			// available customer asset count
        			else if ("Available Customer Asset" == type) {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, "AVAILABLE", data.assetCount.allCustomersAvailableMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, "AVAILABLE", data.assetCount.allCustomersAvailableMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, "AVAILABLE", data.assetCount.allCustomersAvailableSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, "AVAILABLE", data.assetCount.allCustomersAvailableDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, "AVAILABLE", data.assetCount.allCustomersAvailableOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, "AVAILABLE", data.assetCount.allCustomersAvailableAssetTotal);
        				
        				//append each customer asset
        				for (var i = 0; i < data.customer.length; i++) {
        					var customerName = data.customer[i].customerName;
        					var customerCode = data.customer[i].customerCode;
        					
        					var deviceCount = customerName + "AvailableDeviceCount";
        					var machineCount = customerName + "AvailableMachineCount";
        					var monitorCount = customerName + "AvailableMonitorCount";
        					var otherAssetsCount = customerName + "AvailableOtherAssetsCount";
        					var softwareCount = customerName + "AvailableSoftwareCount";
        					var total = customerName + "AvailableAssetTotal";
        					
        					$(this).after("<tr class='afterTR-available' content='" + customerCode + "'><td><div class='tr-header treeIndent' title='" 
        							+ customerName + "'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				$(".afterTR-available").hide();
        				$(".afterTR-available").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, "AVAILABLE", $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, "AVAILABLE", $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, "AVAILABLE", $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, "AVAILABLE", $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, "AVAILABLE", $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, "AVAILABLE", $(this).children("td:eq(6)").html());
        				});
        				
        				
        				$(this).children("td:eq(0)").bind("click", function(){
        					$(this).parent().siblings(".afterTR-available").toggle();
        					changeTreeIcon($(this).children("div"));
        				});
        			}
        			// in use customer asset count
        			else if ("In Use Customer Asset" == type) {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, "IN_USE", data.assetCount.allCustomersInUseMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, "IN_USE", data.assetCount.allCustomersInUseMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, "IN_USE", data.assetCount.allCustomersInUseSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, "IN_USE", data.assetCount.allCustomersInUseDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, "IN_USE", data.assetCount.allCustomersInUseOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, "IN_USE", data.assetCount.allCustomersInUseAssetTotal);
        				
        				//append each customer asset
        				for (var i = 0; i < data.customer.length; i++) {
        					var customerName = data.customer[i].customerName;
        					var customerCode = data.customer[i].customerCode;
        					
        					var deviceCount = customerName + "InUseDeviceCount";
        					var machineCount = customerName + "InUseMachineCount";
        					var monitorCount = customerName + "InUseMonitorCount";
        					var otherAssetsCount = customerName + "InUseOtherAssetsCount";
        					var softwareCount = customerName + "InUseSoftwareCount";
        					var total = customerName + "InUseAssetTotal";
        					
        					$(this).after("<tr class='afterTR-inuse' content='" + customerCode + "'><td><div class='tr-header treeIndent' title='" 
        							+ customerName + "'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				$(".afterTR-inuse").hide();
        				$(".afterTR-inuse").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, "IN_USE", $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, "IN_USE", $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, "IN_USE", $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, "IN_USE", $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, "IN_USE", $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, "IN_USE", $(this).children("td:eq(6)").html());
        				});
        				
        				$(this).children("td:eq(0)").bind("click", function(){
        					$(this).parent().siblings(".afterTR-inuse").toggle();
        					changeTreeIcon($(this).children("div"));
        				});
        			}
        			
        			// idle customer asset count
        			else if ("Idle Customer Asset" == type) {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, "IDLE", data.assetCount.allCustomersIdleMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, "IDLE", data.assetCount.allCustomersIdleMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, "IDLE", data.assetCount.allCustomersIdleSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, "IDLE", data.assetCount.allCustomersIdleDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, "IDLE", data.assetCount.allCustomersIdleOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, "IDLE", data.assetCount.allCustomersIdleAssetTotal);
        				
        				//append each customer asset
        				for (var i = 0; i < data.customer.length; i++) {
        					var customerName = data.customer[i].customerName;
        					var customerCode = data.customer[i].customerCode;
        					
        					var deviceCount = customerName + "IdleDeviceCount";
        					var machineCount = customerName + "IdleMachineCount";
        					var monitorCount = customerName + "IdleMonitorCount";
        					var otherAssetsCount = customerName + "IdleOtherAssetsCount";
        					var softwareCount = customerName + "IdleSoftwareCount";
        					var total = customerName + "IdleAssetTotal";
        					
        					$(this).after("<tr class='afterTR-idle' content='" + customerCode + "'><td><div class='tr-header treeIndent' title='" 
        							+ customerName + "'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				$(".afterTR-idle").hide();
        				$(".afterTR-idle").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, "IDLE", $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, "IDLE", $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, "IDLE", $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, "IDLE", $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, "IDLE", $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, "IDLE", $(this).children("td:eq(6)").html());
        				});
        				
        				$(this).children("td:eq(0)").bind("click", function(){
        					$(this).parent().siblings(".afterTR-idle").toggle();
        					changeTreeIcon($(this).children("div"));
        				});
        			}
        			
        		});
        	}
        	
        }
	});
});

function changeTreeIcon($element) {
	if ($element.hasClass("tree_icon_close")) {
		$element.removeClass("tree_icon_close");
		$element.addClass("tree_icon_open");
	} else if ($element.hasClass("tree_icon_open")) {
		$element.removeClass("tree_icon_open");
		$element.addClass("tree_icon_close");
	}
}

function getActivedAssetIds(className) {
    var assetIds = [];
    $(className).each(function(){
        assetIds.push(($(this).val()));
    });
    return assetIds.toString();
}