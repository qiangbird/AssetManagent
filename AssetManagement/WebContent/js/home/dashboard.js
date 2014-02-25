$(document).ready(function(){
	
	var roleList = $("#currentUserRoles").val();
	var roles = roleList.substring(1, roleList.length - 1).split(",");
	
	function checkUserRole(str) {
		for (var i = 0; i < roles.length; i++) {
			if (str == roles[i].trim()) {
				return true;
			}
		}
	}
	
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
					$(".returnedAssetPanel table").append("<tr><td><div class='div_checkbox' pk=" 
							+ todoList[i].id + " /></td><td><div style='width:120px' class='overFlow'>" + todoList[i].assetName + "</div></td>" 
							+ "<td><div style='width:130px' class='overFlow'>" 
							+ todoList[i].customerName + "</div></td>" + "<td>" + todoList[i].returnedTime 
							+ "</td></tr>");
				}
				
				addOverFlowStyle();
			}
		});
	}
	
	if (checkUserRole("IT")) {
		getReturnedAsset();
	}
	
	$("#returnedAsset").click(function(){
		if (checkActivedAssetIds(".returnedAssetPanel .div_checkbox_actived")) {
			$.ajax({
				type : 'GET',
				contentType : 'application/json',
				url : 'todo/confirmReturnedAsset',
				dataType : 'json',
				data: {
					ids: getActivedAssetIds(".returnedAssetPanel .div_checkbox_actived"),
				},
				success : function(){
					$(".returnedAssetPanel table tr:gt(0)").remove();
					$(".returnedAssetPanel table .div_checkbox_all").remove("div_checkbox_all_actived");
					getReturnedAsset();
					getAssetCount();
				}
			});
		} else {
			return;
		}
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
					$(".receivedAssetPanel table").append("<tr><td><div class='div_checkbox' pk=" 
							+ todoList[i].id + " /></td><td><div style='width:120px' class='overFlow'>" + todoList[i].assetName 
							+ "</div></td>" + "<td><div style='width:130px' class='overFlow'>" 
							+ todoList[i].customerName + "</div></td>" + "<td>" + todoList[i].receivedTime 
							+ "</td></tr>");
				}
				
				addOverFlowStyle();
			}
		});
	}
	
	if (checkUserRole("MANAGER")) {
		getReceivedAsset();
	}
	
	$("#receivedAsset").click(function(){
		if (checkActivedAssetIds(".receivedAssetPanel .div_checkbox_actived")) {
			$.ajax({
	            type : 'GET',
	            contentType : 'application/json',
	            url : 'todo/confirmReceivedAsset',
	            dataType : 'json',
	            data: {
	                ids: getActivedAssetIds(".receivedAssetPanel .div_checkbox_actived"),
	            },
	            success : function(){
	            	$(".receivedAssetPanel table tr:gt(0)").remove();
	            	$(".receivedAssetPanel table .div_checkbox_all").remove("div_checkbox_all_actived");
	            	getReceivedAsset();
	            	getAssetCount();
	            }
	        });
		} else {
			return;
		}
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
			url: "dashboard/viewIdleAssetPanel",
			success: function(data){
				
				if (data.idleAssetList == null) {
					return;
				} else {
					
					var assetList = data.idleAssetList;
					for (var i = 0; i < assetList.length; i++) {
						$(".idleAssetPanel table").append("<tr><td><div class='div_checkbox' pk=" 
								+ assetList[i].id + " /></td><td><div style='width:120px;' class='overFlow'>" + assetList[i].assetName 
								+ "</div></td>" + "<td><div style='width:130px;' class='overFlow'>" + assetList[i].customerName 
								+ "</div></td>" + "<td><div style='width:100px;' class='overFlow'>" + assetList[i].keeper + "</div></td></tr>");
					}
					
					addOverFlowStyle();
				}
			}
		});
	}
	
	if (checkUserRole("MANAGER")) {
		getIdleAsset();
	}
	
	$("#idleAsset").click(function(){
		if (checkActivedAssetIds(".idleAssetPanel .div_checkbox_actived")) {
			$.ajax({
				  type: 'POST',
				  url: "customerAsset/changeStatus/"+"RETURNING_TO_IT",
				  data: {
					  _method: 'PUT',
					  assetsId: getActivedAssetIds(".idleAssetPanel .div_checkbox_actived"),
					  operation: "Return To IT"
				  },
				  success: function(){
					  $(".idleAssetPanel table tr:gt(0)").remove();
		              $(".idleAssetPanel table .div_checkbox_all").remove("div_checkbox_all_actived");
		              getIdleAsset();
		              $(".returnedAssetPanel table tr:gt(0)").remove();
		              getReturnedAsset();
		              getAssetCount();
				  }
			});
		} else {
			return;
		}
	});
	
	$("#viewMore_idleAsset").click(function(){
		window.location.href = "customerAsset/listAllCustomerAssets?status=idle";
	});
	
	// get warranty expired asset panel
	function getWarrantyExpiredAsset() {
		
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			url: "dashboard/viewWarrantyExpiredAssetPanel",
			success: function(data){
				var assetList = data.warrantyExpiredAssetList;
				for (var i = 0; i < assetList.length; i++) {
					$(".warrantyExpiredPanel table").append("<tr><td><div>" + assetList[i].assetId + "</div></td><td><div style='width:150px' class='overFlow'>" 
							+ assetList[i].assetName + "</div></td><td><div  style='width:150px' class='overFlow'>" + assetList[i].customerName 
							+ "</div></td><td><div>" + assetList[i].warrantyTime + "</div></td></tr>");
				}
				
				$(".overFlow").each(function(){
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
			}
		});
	}
	
	if (checkUserRole("IT")) {
		getWarrantyExpiredAsset();
	}
	
	$("#viewMore_warrantyExpired").click(function(){
		window.location.href = "asset/allAssets?isWarrantyExpired=true";
	});
	
	// find newly purchase assets panel
	function getNewlyComingPurchase() {
		
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			url: "dashboard/viewNewlyPurchaseItemsPanel",
			success: function(data){
				var purchaseItems = data.newlyPurchaseItemsList;
				for (var i = 0; i < purchaseItems.length; i++) {
					$(".newlyPurchaseItemsPanel table").append("<tr><td><input type='hidden' class='itemId' value=" 
							+ purchaseItems[i].id + "><div style='width:120px;margin-left:30px;' class='overFlow itemName'>" 
							+ purchaseItems[i].itemName + "</div></td><td>" 
							+ purchaseItems[i].deliveryDate + "</td><td><div class='deleteItem'></div></td><td>");
				}
				addOverFlowStyle();
			}
		});
	}
	
	if (checkUserRole("IT")) {
		getNewlyComingPurchase();
	}
	
	$(".newlyPurchaseItemsPanel").delegate(".deleteItem","click", function(){
		var itemId = $(this).parents(".newlyPurchaseItemsPanel").find(".itemId").val();
		$.ajax({
			type : 'GET',
			dataType : 'json',
			url: "dashboard/deletePurchaseItem",
			data: {
				id:itemId
			},
			success: function(data){
				alert("Delete the purchase item successfully!");
			}
		});
	});
	
	$(".newlyPurchaseItemsPanel").delegate(".itemName","click", function(){
		var itemId = $(this).parents(".newlyPurchaseItemsPanel").find(".itemId").val();
		window.location.href = "asset/createPurchaseItem?id=" + itemId;
	});
	
	
	// generate link and parameters for IT number   --------------------------------- start
	function getTypeForLink(i) {
		if (i == 1) {
			return "machine";
		} else if (i == 2) {
			return "monitor";
		} else if (i == 3) {
			return "software";
		} else if (i == 4) {
			return "device";
		} else if (i == 5) {
			return "otherassets";
		} else {
			return "";
		}
	}
	
	function getStatusForLink(i) {
		if (i == 1 || i == 12) {
			return "available";
		} else if (i == 2 || i == 13) {
			return "in_use";
		} else if (i == 3 || i == 14) {
			return "idle";
		} else if (i == 4) {
			return "returned";
		} else if (i == 5) {
			return "borrowed";
		} else if (i == 6) {
			return "broken";
		} else if (i == 7) {
			return "write_off";
		} else if (i == 8) {
			return "returning_to_it";
		} else if (i == 9) {
			return "assigning";
		} else {
			return "";
		}
	}
	
	function addLinkForITNum($element, posX, posY, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = getTypeForLink(posX);
			var status = getStatusForLink(posY);
			
			$element.html("<a href='asset/allAssets?type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	// ------------------------------------------------------------------------  end
	
	
	// generate link and parameters for My Asset number
	function addLinkForMyAssetNum($element, posX, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = getTypeForLink(posX);
			
			$element.html("<a href='asset/listMyAssets?type=" + type + "'>" + count + "</a>");
		}
	}
	
	/* generate link and parameters for fixed Asset number
	function addLinkForFixedAssetNum($element, posY, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = $element.parent().siblings("tr:eq(0)").children("th:eq(" + posY + ")").html();
			type = formatType(type);
			
			$element.html("<a href='asset/allAssets?isFixedAsset=true&type=" + type + "'>" + count + "</a>");
		}
	}
	*/
	
	// get asset count for IT
	function getAssetCount() {
		
		$.ajax({
			type : 'GET',
			contentType : 'application/json',
			dataType : 'json',
			url: "dashboard/getAssetCountForPanel",
			success: function(data){
				
				$(".mainPanel table tr:gt(0)").each(function(i){
					
					// get first column name: All Asset, My Asset, Customer Asset, Available, etc...
					var type = $(this).children("td:eq(0)").attr("content");
					
					// all asset line
					if (type == "allAssets") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allAssetMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allAssetMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allAssetSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allAssetDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allAssetOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allAssetTotal);
					} 
					//my asset line
					else if (type == "myAssets") {
						addLinkForMyAssetNum($(this).children("td:eq(1)"), 1, data.assetCount.myAssetMachineCount);
						addLinkForMyAssetNum($(this).children("td:eq(2)"), 2, data.assetCount.myAssetMonitorCount);
						addLinkForMyAssetNum($(this).children("td:eq(3)"), 3, data.assetCount.myAssetSoftwareCount);
						addLinkForMyAssetNum($(this).children("td:eq(4)"), 4, data.assetCount.myAssetDeviceCount);
						addLinkForMyAssetNum($(this).children("td:eq(5)"), 5, data.assetCount.myAssetOtherAssetsCount);
						addLinkForMyAssetNum($(this).children("td:eq(6)"), 6, data.assetCount.myAssetTotal);
					}
					// fixed asset line
					/*
        		else if (i == 10) {
        			addLinkForFixedAssetNum($(this).children("td:eq(1)"), 1, data.assetCount.fixedAssetMachineCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(2)"), 2, data.assetCount.fixedAssetMonitorCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(3)"), 3, data.assetCount.fixedAssetSoftwareCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(4)"), 4, data.assetCount.fixedAssetDeviceCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(5)"), 5, data.assetCount.fixedAssetOtherAssetsCount);
        			addLinkForFixedAssetNum($(this).children("td:eq(6)"), 6, data.assetCount.fixedAssetTotal);
        		}
					 */
					// available asset line
					else if (type == "allAvailable") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allAvailableMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allAvailableMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allAvailableSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allAvailableDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allAvailableOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allAvailableAssetTotal);
					}
					// in_use asset line
					else if (type == "allInuse") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allInUseMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allInUseMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allInUseSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allInUseDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allInUseOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allInUseAssetTotal);
					}
					// idle asset line
					else if (type == "allIdle") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allIdleMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allIdleMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allIdleSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allIdleDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allIdleOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allIdleAssetTotal);
					}
					// returned asset line
					else if (type == "returned") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allReturnedMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allReturnedMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allReturnedSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allReturnedDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allReturnedOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allReturnedAssetTotal);
					}
					// borrowed asset line
					else if (type == "borrowed") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allBorrowedMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allBorrowedMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allBorrowedSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allBorrowedDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allBorrowedOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allBorrowedAssetTotal);
					}
					// broken asset line
					else if (type == "broken") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allBrokenMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allBrokenMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allBrokenSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allBrokenDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allBrokenOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allBrokenAssetTotal);
					}
					// write_off asset line
					else if (type == "writeOff") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allWriteOffMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allWriteOffMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allWriteOffSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allWriteOffDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allWriteOffOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allWriteOffAssetTotal);
					}
					// returning to IT asset line
					else if (type == "returningToIT") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allReturningToITMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allReturningToITMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allReturningToITSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allReturningToITDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allReturningToITOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allReturningToITAssetTotal);
					}
					// assigning asset line
					else if (type == "assigning") {
						addLinkForITNum($(this).children("td:eq(1)"), 1, i, data.assetCount.allAssigningMachineCount);
						addLinkForITNum($(this).children("td:eq(2)"), 2, i, data.assetCount.allAssigningMonitorCount);
						addLinkForITNum($(this).children("td:eq(3)"), 3, i, data.assetCount.allAssigningSoftwareCount);
						addLinkForITNum($(this).children("td:eq(4)"), 4, i, data.assetCount.allAssigningDeviceCount);
						addLinkForITNum($(this).children("td:eq(5)"), 5, i, data.assetCount.allAssigningOtherAssetsCount);
						addLinkForITNum($(this).children("td:eq(6)"), 6, i, data.assetCount.allAssigningAssetTotal);
					}
				});
				
			}
		});
	}
	
	getAssetCount();
	
	
	// generate link and parameters for customer Asset number
	function addLinkForCustomerNum($element, posX, customerCode, status, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = getTypeForLink(posX);
			
			$element.html("<a href='customerAsset/listCustomerAsset?customerCode=" + customerCode 
					+ "&type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	
	// generate link and parameters for customer Asset number
	function addLinkForAllCustomerNum($element, posX, status, count) {
		if (count == 0) {
			$element.html(count);
		} else {
			var type = getTypeForLink(posX);
			
			$element.html("<a href='customerAsset/listAllCustomerAssets?type=" + type + "&status=" + status + "'>" + count + "</a>");
		}
	}
	
	// get asset count for manager
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "dashboard/getAssetCountForManager",
        success : function(data){
        	
        	if (data.customer == null || data.assetCount == null) {
        		$(".tr-header-customer").parent("tr").remove();
        		return;
        	} else {
        		var available = "available";
        		var in_use = "in_use";
        		var idle = "idle";
        		
        		$(".mainPanel table tr:gt(0)").each(function(i){
        			
        			// get first column name: All Asset, My Asset, Customer Asset, Available, etc...
        			var type = $(this).children("td:eq(0)").attr("content");
        			
        			if (type == "Customer Asset") {
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
        					
        					$(this).after("<tr class='afterTR' content='" + customerCode + "'><td><div class='tr-header treeIndent overFlow'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				addOverFlowStyle();
        				
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
        			else if (type == "Available Customer Asset") {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, available, data.assetCount.allCustomersAvailableMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, available, data.assetCount.allCustomersAvailableMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, available, data.assetCount.allCustomersAvailableSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, available, data.assetCount.allCustomersAvailableDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, available, data.assetCount.allCustomersAvailableOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, available, data.assetCount.allCustomersAvailableAssetTotal);
        				
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
        					
        					$(this).after("<tr class='afterTR-available' content='" + customerCode + "'><td><div class='tr-header treeIndent overFlow'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				addOverFlowStyle();
        				
        				$(".afterTR-available").hide();
        				$(".afterTR-available").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, available, $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, available, $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, available, $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, available, $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, available, $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, available, $(this).children("td:eq(6)").html());
        				});
        				
        				$(this).children("td:eq(0)").bind("click", function(){
        					$(this).parent().siblings(".afterTR-available").toggle();
        					changeTreeIcon($(this).children("div"));
        				});
        			}
        			// in use customer asset count
        			else if (type == "In Use Customer Asset") {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, in_use, data.assetCount.allCustomersInUseMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, in_use, data.assetCount.allCustomersInUseMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, in_use, data.assetCount.allCustomersInUseSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, in_use, data.assetCount.allCustomersInUseDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, in_use, data.assetCount.allCustomersInUseOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, in_use, data.assetCount.allCustomersInUseAssetTotal);
        				
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
        					
        					$(this).after("<tr class='afterTR-inuse' content='" + customerCode + "'><td><div class='tr-header treeIndent overFlow'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				addOverFlowStyle();
        				
        				$(".afterTR-inuse").hide();
        				$(".afterTR-inuse").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, in_use, $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, in_use, $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, in_use, $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, in_use, $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, in_use, $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, in_use, $(this).children("td:eq(6)").html());
        				});
        				
        				$(this).children("td:eq(0)").bind("click", function(){
        					$(this).parent().siblings(".afterTR-inuse").toggle();
        					changeTreeIcon($(this).children("div"));
        				});
        			}
        			
        			// idle customer asset count
        			else if (type == "Idle Customer Asset") {
        				addLinkForAllCustomerNum($(this).children("td:eq(1)"), 1, idle, data.assetCount.allCustomersIdleMachineCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(2)"), 2, idle, data.assetCount.allCustomersIdleMonitorCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(3)"), 3, idle, data.assetCount.allCustomersIdleSoftwareCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(4)"), 4, idle, data.assetCount.allCustomersIdleDeviceCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(5)"), 5, idle, data.assetCount.allCustomersIdleOtherAssetsCount);
        				addLinkForAllCustomerNum($(this).children("td:eq(6)"), 6, idle, data.assetCount.allCustomersIdleAssetTotal);
        				
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
        					
        					$(this).after("<tr class='afterTR-idle' content='" + customerCode + "'><td><div class='tr-header treeIndent overFlow'>" + customerName + "</div></td><td>" + data.assetCount[machineCount]
        					+ "</td><td>" + data.assetCount[monitorCount] + "</td><td>" + data.assetCount[softwareCount]
        					+ "</td><td>" + data.assetCount[deviceCount] + "</td><td>" + data.assetCount[otherAssetsCount]
        					+ "</td><td>" + data.assetCount[total] + "</td></td></tr>");
        				}
        				
        				addOverFlowStyle();
        				
        				$(".afterTR-idle").hide();
        				$(".afterTR-idle").each(function(){
        					var customerCode = $(this).attr("content");
        					
        					addLinkForCustomerNum($(this).children("td:eq(1)"), 1, customerCode, idle, $(this).children("td:eq(1)").html());
        					addLinkForCustomerNum($(this).children("td:eq(2)"), 2, customerCode, idle, $(this).children("td:eq(2)").html());
        					addLinkForCustomerNum($(this).children("td:eq(3)"), 3, customerCode, idle, $(this).children("td:eq(3)").html());
        					addLinkForCustomerNum($(this).children("td:eq(4)"), 4, customerCode, idle, $(this).children("td:eq(4)").html());
        					addLinkForCustomerNum($(this).children("td:eq(5)"), 5, customerCode, idle, $(this).children("td:eq(5)").html());
        					addLinkForCustomerNum($(this).children("td:eq(6)"), 6, customerCode, idle, $(this).children("td:eq(6)").html());
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

// get actived checkbox 
function getActivedAssetIds(className) {
    var assetIds = [];
    $(className).each(function(){
        assetIds.push(($(this).attr("pk")));
    });
    return assetIds.toString();
}


// check checkbox actived asset
function checkActivedAssetIds(className) {
	var assetIds = getActivedAssetIds(className);
    if (assetIds == "") {
    	ShowMsg(i18nProp('none_select_record'));
        return false;
    }
    return true;
}

function checkAll(className) {
	if ($(className).hasClass("div_checkbox_all_actived")) {
		$(className).removeClass("div_checkbox_all_actived");
		$(className).parents("tr").siblings("tr").each(function(){
			$(this).children("td:eq(0)").children("div").removeClass("div_checkbox_actived");
		});
	} else {
		$(className).addClass("div_checkbox_all_actived");
		$(className).parents("tr").siblings("tr").each(function(){
			$(this).children("td:eq(0)").children("div").addClass("div_checkbox_actived");
		});
	}
}

function isCheckAll($element) {
	var flag = true;
	$element.siblings("tr").each(function(){
		if (!$(this).children("td:eq(0)").children("div").hasClass("div_checkbox_actived")) {
			flag = false;
		}
	});
	return flag;
}

$(".returnedAssetPanel .div_checkbox_all").click(function(){
	checkAll(".returnedAssetPanel .div_checkbox_all");
});

$(".receivedAssetPanel .div_checkbox_all").click(function(){
	checkAll(".receivedAssetPanel .div_checkbox_all");
});

$(".idleAssetPanel .div_checkbox_all").click(function(){
	checkAll(".idleAssetPanel .div_checkbox_all");
});

//add css style for checkbox when click and add check all event
$("#right").delegate(".div_checkbox", "click", function(){
	if ($(this).hasClass("div_checkbox_actived")) {
		$(this).removeClass("div_checkbox_actived");
	} else {
		$(this).addClass("div_checkbox_actived");
	}
	
	$checkall = $(this).parents("tr").siblings("tr:eq(0)"); 
	if (!isCheckAll($checkall)) {
		$checkall.children("th:eq(0)").children("div").removeClass("div_checkbox_all_actived");
	}
});

function addOverFlowStyle() {
	$(".overFlow").each(function(){
		var length;
		if ($(this).html().charCodeAt(0) > 255) {
			length = 8;
		} else {
			length = 16;
		}
		if ($(this).html().length > length) {
			$(this).poshytip({
				className: 'tip-green',
				allowTipHover: true,
				content: $(this).html()
			});
		}
	});
}