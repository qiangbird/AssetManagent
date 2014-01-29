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
	
	
	// get asset count for IT
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "asset/getAssetCountForPanel",
        success: function(data){
        	
        	// all asset line
        	$(".mainPanel table tr:eq(1) td:eq(1)").html(data.assetCount.allAssetDeviceCount);
        	$(".mainPanel table tr:eq(1) td:eq(2)").html(data.assetCount.allAssetMachineCount);
        	$(".mainPanel table tr:eq(1) td:eq(3)").html(data.assetCount.allAssetMonitorCount);
        	$(".mainPanel table tr:eq(1) td:eq(4)").html(data.assetCount.allAssetOtherAssetsCount);
        	$(".mainPanel table tr:eq(1) td:eq(5)").html(data.assetCount.allAssetSoftwareCount);
        	$(".mainPanel table tr:eq(1) td:eq(6)").html(data.assetCount.allAssetTotal);
        	
        	// my asset line
        	$(".mainPanel table tr:eq(3) td:eq(1)").html(data.assetCount.myAssetDeviceCount);
        	$(".mainPanel table tr:eq(3) td:eq(2)").html(data.assetCount.myAssetMachineCount);
        	$(".mainPanel table tr:eq(3) td:eq(3)").html(data.assetCount.myAssetMonitorCount);
        	$(".mainPanel table tr:eq(3) td:eq(4)").html(data.assetCount.myAssetOtherAssetsCount);
        	$(".mainPanel table tr:eq(3) td:eq(5)").html(data.assetCount.myAssetSoftwareCount);
        	$(".mainPanel table tr:eq(3) td:eq(6)").html(data.assetCount.myAssetTotal);
        	
        	// fixed asset line
        	$(".mainPanel table tr:eq(4) td:eq(1)").html(data.assetCount.fixedAssetDeviceCount);
        	$(".mainPanel table tr:eq(4) td:eq(2)").html(data.assetCount.fixedAssetMachineCount);
        	$(".mainPanel table tr:eq(4) td:eq(3)").html(data.assetCount.fixedAssetMonitorCount);
        	$(".mainPanel table tr:eq(4) td:eq(4)").html(data.assetCount.fixedAssetOtherAssetsCount);
        	$(".mainPanel table tr:eq(4) td:eq(5)").html(data.assetCount.fixedAssetSoftwareCount);
        	$(".mainPanel table tr:eq(4) td:eq(6)").html(data.assetCount.fixedAssetTotal);
        	
        	// available asset line
        	$(".mainPanel table tr:eq(5) td:eq(1)").html(data.assetCount.allAvailableDeviceCount);
        	$(".mainPanel table tr:eq(5) td:eq(2)").html(data.assetCount.allAvailableMachineCount);
        	$(".mainPanel table tr:eq(5) td:eq(3)").html(data.assetCount.allAvailableMonitorCount);
        	$(".mainPanel table tr:eq(5) td:eq(4)").html(data.assetCount.allAvailableOtherAssetsCount);
        	$(".mainPanel table tr:eq(5) td:eq(5)").html(data.assetCount.allAvailableSoftwareCount);
        	$(".mainPanel table tr:eq(5) td:eq(6)").html(data.assetCount.allAvailableAssetTotal);
        	
        	// in_use asset line
        	$(".mainPanel table tr:eq(6) td:eq(1)").html(data.assetCount.allInUseDeviceCount);
        	$(".mainPanel table tr:eq(6) td:eq(2)").html(data.assetCount.allInUseMachineCount);
        	$(".mainPanel table tr:eq(6) td:eq(3)").html(data.assetCount.allInUseMonitorCount);
        	$(".mainPanel table tr:eq(6) td:eq(4)").html(data.assetCount.allInUseOtherAssetsCount);
        	$(".mainPanel table tr:eq(6) td:eq(5)").html(data.assetCount.allInUseSoftwareCount);
        	$(".mainPanel table tr:eq(6) td:eq(6)").html(data.assetCount.allInUseAssetTotal);
        	
        	// returned asset line
        	$(".mainPanel table tr:eq(7) td:eq(1)").html(data.assetCount.allReturnedDeviceCount);
        	$(".mainPanel table tr:eq(7) td:eq(2)").html(data.assetCount.allReturnedMachineCount);
        	$(".mainPanel table tr:eq(7) td:eq(3)").html(data.assetCount.allReturnedMonitorCount);
        	$(".mainPanel table tr:eq(7) td:eq(4)").html(data.assetCount.allReturnedOtherAssetsCount);
        	$(".mainPanel table tr:eq(7) td:eq(5)").html(data.assetCount.allReturnedSoftwareCount);
        	$(".mainPanel table tr:eq(7) td:eq(6)").html(data.assetCount.allReturnedAssetTotal);
        	
        	// borrowed asset line
        	$(".mainPanel table tr:eq(8) td:eq(1)").html(data.assetCount.allBorrowedDeviceCount);
        	$(".mainPanel table tr:eq(8) td:eq(2)").html(data.assetCount.allBorrowedMachineCount);
        	$(".mainPanel table tr:eq(8) td:eq(3)").html(data.assetCount.allBorrowedMonitorCount);
        	$(".mainPanel table tr:eq(8) td:eq(4)").html(data.assetCount.allBorrowedOtherAssetsCount);
        	$(".mainPanel table tr:eq(8) td:eq(5)").html(data.assetCount.allBorrowedSoftwareCount);
        	$(".mainPanel table tr:eq(8) td:eq(6)").html(data.assetCount.allBorrowedAssetTotal);
        	
        	// broken asset line
        	$(".mainPanel table tr:eq(9) td:eq(1)").html(data.assetCount.allBrokenDeviceCount);
        	$(".mainPanel table tr:eq(9) td:eq(2)").html(data.assetCount.allBrokenMachineCount);
        	$(".mainPanel table tr:eq(9) td:eq(3)").html(data.assetCount.allBrokenMonitorCount);
        	$(".mainPanel table tr:eq(9) td:eq(4)").html(data.assetCount.allBrokenOtherAssetsCount);
        	$(".mainPanel table tr:eq(9) td:eq(5)").html(data.assetCount.allBrokenSoftwareCount);
        	$(".mainPanel table tr:eq(9) td:eq(6)").html(data.assetCount.allBrokenAssetTotal);
        	
        	// write_off asset line
        	$(".mainPanel table tr:eq(10) td:eq(1)").html(data.assetCount.allWriteOffDeviceCount);
        	$(".mainPanel table tr:eq(10) td:eq(2)").html(data.assetCount.allWriteOffMachineCount);
        	$(".mainPanel table tr:eq(10) td:eq(3)").html(data.assetCount.allWriteOffMonitorCount);
        	$(".mainPanel table tr:eq(10) td:eq(4)").html(data.assetCount.allWriteOffOtherAssetsCount);
        	$(".mainPanel table tr:eq(10) td:eq(5)").html(data.assetCount.allWriteOffSoftwareCount);
        	$(".mainPanel table tr:eq(10) td:eq(6)").html(data.assetCount.allWriteOffAssetTotal);
        }
	});
	
	
	// get asset count for IT
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "asset/getAssetCountForManager",
        success : function(data){
        	
        	// all customer asset
        	$(".mainPanel table tr:eq(2) td:eq(1)").html(data.assetCount.allCustomersDeviceCount);
        	$(".mainPanel table tr:eq(2) td:eq(2)").html(data.assetCount.allCustomersMachineCount);
        	$(".mainPanel table tr:eq(2) td:eq(3)").html(data.assetCount.allCustomersMonitorCount);
        	$(".mainPanel table tr:eq(2) td:eq(4)").html(data.assetCount.allCustomersOtherAssetsCount);
        	$(".mainPanel table tr:eq(2) td:eq(5)").html(data.assetCount.allCustomersSoftwareCount);
        	$(".mainPanel table tr:eq(2) td:eq(6)").html(data.assetCount.allCustomersAssetTotal);
    			
			for (var i = 0; i < data.customer.length; i++) {
				var customerName = data.customer[i].customerName;
				var deviceCount = customerName + "DeviceCount";
				var machineCount = customerName + "MachineCount";
				var monitorCount = customerName + "MonitorCount";
				var otherAssetsCount = customerName + "OtherAssetsCount";
				var softwareCount = customerName + "SoftwareCount";
				var total = customerName + "AssetTotal";
				
				$(".mainPanel table tr:eq(2)").after("<tr class='afterTR' bgcolor='gray'><td>" + customerName + "</td><td>" + data.assetCount[deviceCount]
						+ "</td><td>" + data.assetCount[machineCount] + "</td><td>" + data.assetCount[monitorCount]
						+ "</td><td>" + data.assetCount[otherAssetsCount] + "</td><td>" + data.assetCount[softwareCount]
						+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
			}
    			
    		$(".afterTR").hide();
        	$(".mainPanel table tr:eq(2) td:eq(0)").bind("click", function(){
        		$(".mainPanel table tr:eq(2)").siblings(".afterTR").toggle();
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