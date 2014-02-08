$(document).ready(function(){
	
	// get returned asset panel
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
        				+ todoList[i].projectName + "</td>" + "<td>" + todoList[i].returnedTime 
        				+ "</td></tr>");
        	}
        }
	});
	
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
            	location = location;
            }
        });
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
			var type = $element.parent().siblings("tr:eq(0)").children("td:eq(" + posY + ")").html();
			
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
			var type = $element.parent().siblings("tr:eq(0)").children("td:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='asset/listMyAssets?type=" + type + "'>" + count + "</a>");
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
        		if ("All Asset" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allAssetDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allAssetMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allAssetMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allAssetOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allAssetSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allAssetTotal);
        		} 
        		//my asset line
        		else if ("My Asset" == type) {
        			addLinkForMyAssetNum($(this).children("td:eq(1)"), 1, data.assetCount.myAssetDeviceCount);
        			addLinkForMyAssetNum($(this).children("td:eq(2)"), 2, data.assetCount.myAssetMachineCount);
        			addLinkForMyAssetNum($(this).children("td:eq(3)"), 3, data.assetCount.myAssetMonitorCount);
        			addLinkForMyAssetNum($(this).children("td:eq(4)"), 4, data.assetCount.myAssetOtherAssetsCount);
        			addLinkForMyAssetNum($(this).children("td:eq(5)"), 5, data.assetCount.myAssetSoftwareCount);
        			addLinkForMyAssetNum($(this).children("td:eq(6)"), 6, data.assetCount.myAssetTotal);
        		}
        		// fixed asset line
        		else if ("Fixed" == type) {
        			$(this).children("td:eq(1)").html(data.assetCount.fixedAssetDeviceCount);
        			$(this).children("td:eq(2)").html(data.assetCount.fixedAssetMachineCount);
        			$(this).children("td:eq(3)").html(data.assetCount.fixedAssetMonitorCount);
        			$(this).children("td:eq(4)").html(data.assetCount.fixedAssetOtherAssetsCount);
        			$(this).children("td:eq(5)").html(data.assetCount.fixedAssetSoftwareCount);
        			$(this).children("td:eq(6)").html(data.assetCount.fixedAssetTotal);
        		}
        		// available asset line
        		else if ("Available" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allAvailableDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allAvailableMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allAvailableMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allAvailableOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allAvailableSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allAvailableAssetTotal);
        		}
        		// in_use asset line
        		else if ("In Use" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allInUseDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allInUseMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allInUseMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allInUseOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allInUseSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allInUseAssetTotal);
        		}
        		// returned asset line
        		else if ("Returned" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allReturnedDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allReturnedMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allReturnedMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allReturnedOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allReturnedSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allReturnedAssetTotal);
        		}
        		// borrowed asset line
        		else if ("Borrowed" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allBorrowedDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allBorrowedMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allBorrowedMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allBorrowedOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allBorrowedSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allBorrowedAssetTotal);
        		}
        		// broken asset line
        		else if ("Broken" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allBrokenDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allBrokenMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allBrokenMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allBrokenOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allBrokenSoftwareCount);
        			addLinkForITNum($(this).children("td:eq(6)"), 6, data.assetCount.allBrokenAssetTotal);
        		}
        		// write_off asset line
        		else if ("Write Off" == type) {
        			addLinkForITNum($(this).children("td:eq(1)"), 1, data.assetCount.allWriteOffDeviceCount);
        			addLinkForITNum($(this).children("td:eq(2)"), 2, data.assetCount.allWriteOffMachineCount);
        			addLinkForITNum($(this).children("td:eq(3)"), 3, data.assetCount.allWriteOffMonitorCount);
        			addLinkForITNum($(this).children("td:eq(4)"), 4, data.assetCount.allWriteOffOtherAssetsCount);
        			addLinkForITNum($(this).children("td:eq(5)"), 5, data.assetCount.allWriteOffSoftwareCount);
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
			var type = $element.parent().siblings("tr:eq(0)").children("td:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='customerAsset/listCustomerAsset?customerCode=" + customerCode 
					+ "&type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	
	// get asset count for manager
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "asset/getAssetCountForManager",
        success : function(data){
        	
        	$(".mainPanel table tr:gt(0)").each(function(i){
        		
	    		// get first column name: All Asset, My Asset, Customer Asset, Available, etc...
	    		var type = $(this).children("td:eq(0)").html();
	    		
	    		if ("Customer Asset" == type) {
	    			$(this).children("td:eq(1)").html(data.assetCount.allCustomersDeviceCount);
					$(this).children("td:eq(2)").html(data.assetCount.allCustomersMachineCount);
					$(this).children("td:eq(3)").html(data.assetCount.allCustomersMonitorCount);
					$(this).children("td:eq(4)").html(data.assetCount.allCustomersOtherAssetsCount);
					$(this).children("td:eq(5)").html(data.assetCount.allCustomersSoftwareCount);
					$(this).children("td:eq(6)").html(data.assetCount.allCustomersAssetTotal);
					
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
						
						$(this).after("<tr class='afterTR' bgcolor='gray' content='" + customerCode + "'><td>" + customerName + "</td><td>" + data.assetCount[deviceCount]
						+ "</td><td>" + data.assetCount[machineCount] + "</td><td>" + data.assetCount[monitorCount]
						+ "</td><td>" + data.assetCount[otherAssetsCount] + "</td><td>" + data.assetCount[softwareCount]
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
					});
	    		} 
	    		// available customer asset count
	    		else if ("Available Customer Asset" == type) {
	    			$(this).children("td:eq(1)").html(data.assetCount.allCustomersAvailableDeviceCount);
					$(this).children("td:eq(2)").html(data.assetCount.allCustomersAvailableMachineCount);
					$(this).children("td:eq(3)").html(data.assetCount.allCustomersAvailableMonitorCount);
					$(this).children("td:eq(4)").html(data.assetCount.allCustomersAvailableOtherAssetsCount);
					$(this).children("td:eq(5)").html(data.assetCount.allCustomersAvailableSoftwareCount);
					$(this).children("td:eq(6)").html(data.assetCount.allCustomersAvailableAssetTotal);
					
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
						
						$(this).after("<tr class='afterTR-available' bgcolor='gray' content='" + customerCode + "'><td>" + customerName + "</td><td>" + data.assetCount[deviceCount]
						+ "</td><td>" + data.assetCount[machineCount] + "</td><td>" + data.assetCount[monitorCount]
						+ "</td><td>" + data.assetCount[otherAssetsCount] + "</td><td>" + data.assetCount[softwareCount]
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
					});
	    		}
	    		// in use customer asset count
	    		else if ("In Use Customer Asset" == type) {
	    			$(this).children("td:eq(1)").html(data.assetCount.allCustomersInUseDeviceCount);
					$(this).children("td:eq(2)").html(data.assetCount.allCustomersInUseMachineCount);
					$(this).children("td:eq(3)").html(data.assetCount.allCustomersInUseMonitorCount);
					$(this).children("td:eq(4)").html(data.assetCount.allCustomersInUseOtherAssetsCount);
					$(this).children("td:eq(5)").html(data.assetCount.allCustomersInUseSoftwareCount);
					$(this).children("td:eq(6)").html(data.assetCount.allCustomersInUseAssetTotal);
					
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
						
						$(this).after("<tr class='afterTR-inuse' bgcolor='gray' content='" + customerCode + "'><td>" + customerName + "</td><td>" + data.assetCount[deviceCount]
						+ "</td><td>" + data.assetCount[machineCount] + "</td><td>" + data.assetCount[monitorCount]
						+ "</td><td>" + data.assetCount[otherAssetsCount] + "</td><td>" + data.assetCount[softwareCount]
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
					});
	    		}
        	});
        }
	});
});

function getActivedAssetIds(className) {
    var assetIds = [];
    $(className).each(function(){
        assetIds.push(($(this).val()));
    });
    return assetIds.toString();
}