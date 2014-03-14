var dataList;
var criteria = {};
var placeholder_customer;
var placeholder_project;
var placeholder_user;

$(document).ready(function() {
	findUserCustomizedView();
	
	placeholder_customer = $("#customerName").attr("placeholder");
	placeholder_project = $("#projectName").attr("placeholder");
	placeholder_user = $("#userName").attr("placeholder");

	var type = $("#type").val();
	var status = $("#status").val();
	
	// categoryFlag = 1, it means category is 'asset'
	// categoryFlag = 2, it means category is 'machine'
	// categoryFlag = 3, it means category is 'monitor'
	// categoryFlag = 4, it means category is 'device'
	// categoryFlag = 5, it means category is 'otherassets'
	// categoryFlag = 6, it means category is 'software'
	
	if (type == "") {
		initCriteria(1);
		findDataListInfo("asset");
	} else if (type == "machine") {
		initCriteria(2);
		findDataListInfo("machine");
	} else if (type == "device") {
		initCriteria(4);
		findDataListInfo("device");
	} else if (type == "monitor") {
		initCriteria(3);
		findDataListInfo("monitor");
	} else if (type == "software") {
		initCriteria(6);
		findDataListInfo("software");
	} else if (type == "otherassets") {
		initCriteria(5);
		findDataListInfo("otherassets");
	}
    
    $(".filterDiv input[type='checkBox']").each(function(){
    	if ($(this).val() != "all") {
    		$(this).attr("content", $(this).siblings("label").html());
    	}
    });
    
    $(".filterDiv input[type='text']").each(function(){
    	$(this).attr("content", $(this).val());
    });
    
    $(".filterDiv").filterBox({});
    
    $("#searchButton").click(function() {
        setCriteria();
        criteria.pageNum = 1;
        dataList.criteria = criteria;
        dataList.search();
    });
    
    // update filterbox style for dashboard link and search as condition  ------ start
    $("#assetType p input").each(function(){
    	var value = $(this).val();
    	if (value == type) {
    		$(this).attr("checked", "checked");
    		$(this).siblings("span").addClass("span_checked");
    	}
    });
    
    $("input:checked").siblings("span").addClass("span_checked");
    
    $("#assetStatus p input").each(function(){
    	var value = $(this).val();
    	if (value == status) {
    		$(this).attr("checked", "checked");
    		$(this).siblings("span").addClass("span_checked");
    	}
    });
    // ------------------------------------------------------------------------- end 
    
    $("input:checked").siblings("span").addClass("span_checked");
    
    // add keypress event for search feature
    $("#keyword").keydown(function() {
        if(event.keyCode == 13) {
            setCriteria();
            criteria.pageNum = 1;
            dataList.criteria = criteria;
            dataList.search();
        }
    });
    
    $("#customizedViewButton").delegate(".existCustomizedView", "click", function(){
		var id = $(this).attr("id");
		criteria.customizedViewId = id;
		$("#searchButton").click();
		criteria.customizedViewId = "";
	});
    
    $(".dateInput").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        yearRange: "2000:2030"
    });
    
     // add place holder event for keyword
     removePlaceholderForKeyWord();
     
  // drop down operation for asset list
     $(".operation_assets_list").find("ul").hide();
     $(".dialogBody").hide();
     
     $(".operation_assets_list").mouseover(function() {
         $(this).find("ul").show();
     }).mouseout(function() {
         $(this).find("ul").hide();
     });
     
     // change css style based on locale
     if ("zh_CN" == $("#locale").val()) {
    	 $("#not_null_flag").css("margin-left", "60px");
    	 $("#label_ProjectName").css("margin-left", "74px");
    	 $("#label_UserName").css("margin-left", "74px");
     } else if ("en_US" == $("#locale").val()) {
    	 $("#not_null_flag").css("margin-left", "30px");
    	 $("#label_ProjectName").css("margin-left", "47px");
    	 $("#label_UserName").css("margin-left", "48px");
     }
     
     // get customers and users for dialog
     getCustomers();
     var projectMs = initDropDownMap($("#projectCode"), [], false, "label", "value", undefined, 275);
 	 $("#projectCode").data("projectCode",projectMs);
     getUsers();
     
     // assign assets
     $("#assignAssetsButton").parent("li").click(function() {
         
         $("#dialog_assign").dialog({
             title: i18nProp('itAssign_dialog_title'),
             autoOpen: false,
             closeOnEscape: true,
             draggable: false,
             height: 300,
             width: 500,
             modal: true,
             position: "center",
             resizable: false,
             bgiframe: true,
             
             close: closeDialog()
         });
         
         if (checkActivedAssetIds() && checkActivedAssetsStatusForAssign()) {
             $("#dialog_assign").dialog("open");
         }
     });
     
     // return assets to customer
     $("#returnAssetsToCustomer").parent("li").click(function(){
         if (checkActivedAssetIds() && checkActivedAssetsStatusForReturn()) {
             ShowMsg(i18nProp('message_confirm_asset_returnToCustomer', $('.row .dataList-checkbox-active').size().toString()), function(yes){
                 if (yes) {
                	 $("div .dataList-div-loader").show();
                     $.ajax({
                         type : 'GET',
                         contentType : 'application/json',
                         url : 'asset/returnAssetsToCustomer',
                         dataType : 'json',
                         data: {
                        	 assetIds: getActivedAssetIds()
                         },
	                     success : function(data) {
                             criteria.pageNum = 1;
                             dataList.search();
                             if (data.errorCodes) {
                             	showMessageBarForAssetList(data.errorCodes);
                             } else if (data.errorCode) {
                             	showMessageBarForMessage(data.errorCode);
                             }
	                     }
                     });
                 }else{
                     return;
                 }
               });
         }
     });
     
     // change assets to fixed
     $("#changeToFixed").parent("li").click(function(){
         if (checkActivedAssetIds()) {
             ShowMsg(i18nProp('message_confirm_asset_changeToFixedAsset', $('.row .dataList-checkbox-active').size().toString()), function(yes){
                 if (yes) {
                	 $("div .dataList-div-loader").show();
                     $.ajax({
                         type : 'GET',
                         contentType : 'application/json',
                         url : 'asset/changeAssetsToFixed',
                         dataType : 'json',
                         data: {
                             assetIds: getActivedAssetIds()
                         },
                         success : function(data) {
                             criteria.pageNum = 1;
                             dataList.search();
                             if (data.errorCodes) {
                             	showMessageBarForAssetList(data.errorCodes);
                             } else if (data.errorCode) {
                             	showMessageBarForMessage(data.errorCode);
                             }
                         }
                     });
                 }else{
                     return;
                 }
               });
         }
     });
     
     // change assets to not fixed
     $("#changeToNonFixed").parent("li").click(function(){
         if (checkActivedAssetIds()) {
             ShowMsg(i18nProp('message_confirm_asset_changeToNonFixedAsset', $('.row .dataList-checkbox-active').size().toString()), function(yes){
                 if (yes) {
                	 $("div .dataList-div-loader").show();
                     $.ajax({
                         type : 'GET',
                         contentType : 'application/json',
                         url : 'asset/changeAssetsToNonFixed',
                         dataType : 'json',
                         data: {
                             assetIds: getActivedAssetIds()
                         },
                         success : function(data) {
                             criteria.pageNum = 1;
                             dataList.search();
                             if (data.errorCodes) {
                             	showMessageBarForAssetList(data.errorCodes);
                             } else if (data.errorCode) {
                             	showMessageBarForMessage(data.errorCode);
                             }
                         }
                     });
                 }else{
                     return;
                 }
               });
         }
     });
     
     // add assets to audit
     $("#addToAudit").parent("li").click(function(){
         var tipMessage = "";
         
         if (getActivedAssetIds() != "") {
             tipMessage = i18nProp('message_confirm_asset_addToAudit', $('.row .dataList-checkbox-active').size().toString());
             ShowMsg(tipMessage, function(yes){
                 if (yes) {
                	 $("div .dataList-div-loader").show();
                     window.location.href = "asset/addAssetsToAuditForSelected?assetIds=" + getActivedAssetIds();
                 }else{
                     return;
                 }
             });
         } else {
        	 tipMessage = i18nProp('message_confirm_asset_addToAudit', $(".dataList .dataList-div-perPage span:nth-child(3)").html().toString());
             ShowMsg(tipMessage, function(yes){
                 if (yes) {
                	 $("div .dataList-div-loader").show();
                     window.location.href = "asset/addAssetsToAuditForSearchResult?" + setParamsForAddToAudit();
                 }else{
                     return;
                 }
             });
         }
     });
     
     $("#exportIcon").click(function(){
    	 var tipMessage = "";
    	 var assetIds = getActivedAssetIds();
    	 
         if (assetIds != "") {
        	 
             tipMessage = i18nProp('message_confirm_asset_export', $('.row .dataList-checkbox-active').size().toString());
             ShowMsg(tipMessage, function(yes){
                 if (yes) {
                	 $("#div-loader").show();
                	 $("#assetIds").val(assetIds);
                	 
                	 $('#exportForm').ajaxSubmit({
                		 success: function(data){
                			 location.href="asset/download?fileName=" + data;
                			 $("#div-loader").hide();
                    		 showMessageBarForMessage("exoprt_assets_success");
                		 }
                	 });
                 }else{
                     return;
                 }
             });
         } else {
        	 tipMessage = i18nProp('message_confirm_asset_export', $(".dataList .dataList-div-perPage span:nth-child(3)").html().toString());
             ShowMsg(tipMessage, function(yes){
                 if (yes) {
                	 $("#div-loader").show();
                	 $("#assetIds").val(null);
                	 
                	 $("#condition_keyWord").val(criteria.keyWord);
                	 $("#condition_fromTime").val(criteria.fromTime);
                	 $("#condition_toTime").val(criteria.toTime);
                	 $("#condition_assetStatus").val(criteria.assetStatus);
                	 if (criteria.assetType == '') {
                		 $("#condition_assetType").val($("#type").val());
                	 } else {
                		 $("#condition_assetType").val(criteria.assetType);
                	 }
                	 $("#condition_searchFields").val(criteria.searchFields);
                	 
                	 $('#exportForm').ajaxSubmit({
                		 success: function(data){
                			 location.href="asset/download?fileName=" + data;
                			 $("#div-loader").hide();
                    		 showMessageBarForMessage("exoprt_assets_success");
                		 }
                	 });
                 }else{
                     return;
                 }
             });
         }
     });
});

// init dataList information for search list
var dataListInfo = {
    columns : [],
    criteria : criteria,
    minHeight : 150,
    pageSizes : [10, 20, 30, 50],
    hasCheckbox : true,
    pageItemSize : 5,
    url : getURLForAssetList($("#type").val()),
    updateShowField : {
        url : 'searchCommon/column/updateColumns',
        callback : function() {
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : getURLForUserCustomColumn($("#type").val()),
                dataType : "json",
                success : function(data) {
                    dataList.opts.columns = data.columns;
                    dataList.setShow(data.showFields);
                    dataList.search();
                }
            });
        }
    },
    updateShowSize : {
        url : 'searchCommon/pageSize/updatePageSize',
        callback : function() {
        }
    },
    contentHandler : function(str) {
        return resultContentHandle(str);
    }
};

// call dataList
function searchList() {
    dataList = $(".dataList").DataList(dataListInfo);
    dataList.criteria = setCriteria();
    dataList.search();
}


//set Criteria(search conditions) for search feature
function setCriteria() {

    criteria.keyWord = $("#keyword").val();
    criteria.fromTime = $("#fromTime").val();
    criteria.toTime = $("#toTime").val();
    criteria.userUuid = $("#userUuid").val();
    criteria.isFixedAsset = $("#isFixedAsset").val();
    criteria.isWarrantyExpired = $("#isWarrantyExpired").val();
    
    // set search fields
    var searchFields = "";
    $("#searchFields").find(":checked").each(function() {
        if (searchFields == null || searchFields == "") {
            searchFields = this.value;
        } else {
            searchFields = searchFields + "," + this.value;
        }
    });
    criteria.searchFields = searchFields;
    
    // set asset type
    var assetType = "";
    $("#assetType").find(":checked").each(function() {
        if (assetType == null || assetType == "") {
            assetType = this.value;
        } else {
            assetType = assetType + "," + this.value;
        }
    });
    criteria.assetType = assetType;

    // set asset status
    var assetStatus = $("#status").val();
    $("#assetStatus").find(":checked").each(function() {
        if (assetStatus == null || assetStatus == "") {
            assetStatus = this.value;
        } else {
        	
        	if (assetStatus != this.value) {
        		assetStatus = assetStatus + "," + this.value;
        	}
        }
    });
    criteria.assetStatus = assetStatus;
    
    return criteria;
}

// get value according to index sequence
function getIndexInArr(Arr, ele) {
    for ( var i = 0; i < Arr.length; i++) {
       if (ele == Arr[i]) {
          return i;
       }
    }
    return -1;
}


// Show Operation tips after create, edit,update, delete

if(null!=$("#tips").val()&&$("#tips").val()!=""){
//	showMessageBar($("#tips").val());
//	showMessageBarForMessage($("#tips").val());
	showMessageBarForOperationResultMessage($("#tips").val());

}


// get all checkbox active asset ids
function getActivedAssetIds() {
    var assetIds = [];
    $('.row .dataList-checkbox-active').each(function(){
        assetIds.push(($(this).attr('pk')));
    });
    return assetIds.toString();
}

// check checkbox actived asset
function checkActivedAssetIds() {
    var assetIds = getActivedAssetIds();
    if (assetIds == "") {
    	ShowMsg(i18nProp('none_select_record'));
        return false;
    }
    return true;
}

// check acticed assets status for assign
function checkActivedAssetsStatusForAssign() {
    var flag = true;
    var lineNum = "";
    $('.row .dataList-checkbox-active').each(function(){
        var status = $(this).siblings(".Status").html();
       
        if (status == "ASSIGNING" || status == "RETURNING_TO_IT" || status == "RETURNED"
                || status == "BORROWED" || status == "IDLE") {
            
            lineNum += $(this).siblings(".w-30").html() + ", ";
            flag = false;
        } 
    });
    
    if (!flag) {
    	ShowMsg(i18nProp('message_warn_asset_assign', lineNum));
    }
    return flag;
}

// check actived assets status and ownership for return to customer
function checkActivedAssetsStatusForReturn() {
    var flag = true;
    var lineNum = "";
    $('.row .dataList-checkbox-active').each(function(){
        var status = $(this).siblings(".Status").html();
        
        if (status == "ASSIGNING" || status == "RETURNING_TO_IT" || status == "RETURNED"
                || $(this).siblings(".Ownership").html() == "Augmentum") {
            
            lineNum += $(this).siblings(".w-30").html() + ", ";
            flag = false;
        } 
    });
    
    if (!flag) {
    	ShowMsg(i18nProp('message_warn_asset_return', lineNum));
    }
    return flag;
}

// confirm assign assets
$("#confirm_assign").click(function() {
    
    if ($("#customerName").val() == "") {
        return;
    } else {
        $.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'asset/itAssignAssets',
            dataType : 'json',
            data: {
                userId: $("#userId").data("userId").getValue().join(),
                assetIds: getActivedAssetIds(),
                projectName: $("#projectName").val(),
                projectCode: $("#projectCode").data("projectCode").getValue().join(),
                customerName: $("#customerName").val(),
                customerCode: $("#customerCode").data("customerCode").getValue().join()
            },
            success : function(data) {
            	$("div .dataList-div-loader").show();
                criteria.pageNum = 1;
                dataList.search();
            }
        });
        closeDialog();
        $("#dialog_assign").dialog("close");
        
        $("div .dataList-div-loader").show();
    }
});

// cancel assign assets
$("#cancel_assign").click(function() {
    closeDialog();
    $("#dialog_assign").dialog("close");
});

// close dialog and clean text content
function closeDialog() {
    $("#projectName").val("");
    $("#projectCode").data("projectCode").clear();
    
    $("#customerName").val("");
    $("#customerCode").data("customerCode").clear();
    $("#customerName").css("border-color", "");
    
    $("#userName").val("");
    $("#userId").data("userId").clear();
}

function setParamsForAddToAudit() {
	criteria.isGetAllRecords = true;
	criteria = setCriteria();
	var params = "";
	for (var key in criteria) {
		if (criteria[key] != "") {
			 params += key + "=" + criteria[key] + "&";
		}
	}
	if (criteria.assetType == '') {
		params += "assetType=" + $("#type").val();
	} else {
		params = params.substring(0, params.length - 1);
	}
	return params;
}

function getURLForAssetList(type) {
	
	var url = "asset/allAssetsList?uuid=" + $("#userUuid").val();
	if (type != "") {
		url = "asset/allAssetsList/" + type;
	} 
	return url;
}

function getURLForUserCustomColumn(type) {
	
	var url = "searchCommon/column/getColumns?category=asset";
	if (type != "") {
		url = "searchCommon/column/getColumns?category=" + type;
	} 
	return url;
}

function getCustomers() {
	$.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'customer/getCustomerInfo',
        dataType : 'json',
        success : function(data) {
	    	var customerMs = initDropDownMap($("#customerCode"), data.customerList, false, "label", "value", undefined, 275);
            $("#customerCode").data("customerCode",customerMs);
            customerBindEvent();
         }
     });
}

function customerBindEvent(){
//	var customerCode = $("#customerCode").data("customerCode").getValue().join();
	var customerMs = $("#customerCode").data("customerCode");
	$(customerMs).on('selectionchange', function(event, combo, selection){
		var selectedItems = $("#customerCode").data("customerCode").getSelectedItems();
		
		if(0 < selectedItems.length){
			var customerName = selectedItems[0].label;
			$("#customerName").val(customerName);
		}else{
			$("#customerName").val("");
		}
		
		var customerCode = $("#customerCode").data("customerCode").getValue().join();
		if("" == customerCode){
			return;
		}else{
			getProjectsByCustomer(customerCode);
		}
    });
}

$("body").delegate("#customerCode input:first", "blur", function(){
	if ("" == $("#customerName").val()) {
		$("#customerCode").css("border-color", "red");
        $("#customerCode").poshytip({
        	className: 'tip-green',
        	allowTipHover: true,
        	content: "customer should not be null"
        });
	} else {
		$("#customerCode").css("border-color", "");
        $("#customerCode").poshytip("destroy");
	}
});

function getProjectsByCustomer(customerCode) {
	
	$.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'project/getProjectByCustomerCode?customerCode='+customerCode,
        dataType : 'json',
        success : function(data) {
        	var	projectMs = initDropDownMap($("#projectCode"), data.projectList, false, "label", "value", undefined, 275);
        	$("#projectCode").data("projectCode",projectMs);
        	projectBindEvent();
          }
      });
}

function projectBindEvent() {
//	var projectCode = $("#projectCode").data("projectCode").getValue().join();
	var projectMs = $("#projectCode").data("projectCode");
	$(projectMs).on('selectionchange', function(event, combo, selection){
        var selectedItems = $("#projectCode").data("projectCode").getSelectedItems();
        
        if(0 < selectedItems.length){
        	var projectName = selectedItems[0].label;
    		$("#projectName").val(projectName);
        }else{
        	$("#projectName").val("");
        }
    });
}

function getUsers(){
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'user/getEmployeeDataSource',
        dataType : 'json',
        success : function(data) {
        	var employeeMs = initDropDownMap($("#userId"), data.employeeLabel, false, "label", "value", undefined, 275);
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
    });
}