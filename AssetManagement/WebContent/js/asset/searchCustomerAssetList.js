var dataList;
var criteria = {};

$(document).ready(function() {
	
	placeholder_project = $("#projectName").attr("placeholder");
	placeholder_user = $("#userName").attr("placeholder");
	
    // categoryFlag = 1, it means category is 'asset'
    initCriteria(1);
    findDataListInfo("asset");
    
    $(".filterDiv input[type='checkBox']").each(function(){
    	if ($(this).val() != "all") {
    		$(this).attr("content", $(this).siblings("label").html());
    	}
    });
    
    $(".filterDiv input[type='text']").each(function(){
    	$(this).attr("content", $(this).val());
    });
    
    $(".filterDiv").filterBox({});
    
    // update filterbox style for dashboard link and search as condition  ------ start
    var type = $("#type").val();
    $("#assetType p input").each(function(){
    	var value = $(this).val();
    	if (value == type) {
    		$(this).attr("checked", "checked");
    		$(this).siblings("span").addClass("span_checked");
    	}
    });
    
    var status = $("#status").val();
    $("#assetStatus p input").each(function(){
    	var value = $(this).val();
    	if (value == status) {
    		$(this).attr("checked", "checked");
    		$(this).siblings("span").addClass("span_checked");
    	}
    });
    // ------------------------------------------------------------------------- end 
    
    removePlaceholderForKeyWord();
    
    $("#searchButton").click(function() {
        
        setCriteria();
        dataList.criteria = criteria;
        criteria.sortName = null;
        criteria.pageNum = 1;
        dataList.search();
    });
    
   // add keypress event for search feature
    $("#keyword").keydown(function() {
        if(event.keyCode == 13) {
            setCriteria();
            criteria.pageNo = 1;
            dataList.search();
        }
    });
    
    $(".dateInput").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        yearRange: "2000:2030"
    });
    
    
	//operation menu
	$(".operation_assets_list").hover(function() {
			$(".operation_assets_list ul li").show();
		}, function() {
			$(".operation_assets_list ul li").hide();
	});
	
    if ("zh_CN" == $("#locale").val()) {
	   	 $("#label_CustomerName").css("margin-left", "58px");
	   	 $("#label_UserName").css("margin-left", "58px");
	   	 $("#dialog_assign div span").css("left", "80px");
    } else if ("en_US" == $("#locale").val()) {
	   	 $("#label_CustomerName").css("margin-left", "40px");
	   	 $("#label_UserName").css("margin-left", "73px");
	   	 $("#dialog_assign div span").css("left", "60px");
    }
    
    var	customerMs = initDropDownMap($("#projectCode"), data.projectList, false, "label", "value", undefined, 275);
	$("#projectCode").data("projectCode",customerMs);
    var customerCode = $("#customer").val();
    getProjectsByCustomer(customerCode);
    getUsers();
	
	//employee operations
	$("#takeOver").click(function(){
		assetsId = new Array();
		flag=true;
		line="";
		$(".dataList-div-body .row .dataList-checkbox-active").each(function(i){
			assetStatus = $(this).parent().find(".Status").text();
			if($("#userRole").val()=="Employee"){
				if(assetStatus == "AVAILABLE"){
					assetsId[i]=$(this).attr("pk");
				}else{
					flag = false;
					line+=$(this).next().text()+",";
					return;
				}
			}else{
				if(assetStatus == "AVAILABLE"||assetStatus =="IN_USE"){
					assetsId[i]=$(this).attr("pk");
				}else{
					flag = false;
					line+=$(this).next().text()+",";
					return;
				}
			}
		});
		if(flag){
			if(assetsId == ""){
				ShowMsg(i18nProp('none_select_record'));
				return;
			}else{
				ShowMsg(i18nProp('message_confirm_asset_takeOver', $('.row .dataList-checkbox-active').size().toString()),function(yes){
					 if (yes) {
						 $.ajax({
							  type: 'POST',
							  url: "customerAsset/takeOver",
							  data: {
								  _method: 'PUT',
								  customerCode:$("#customerCode").val(),
								  assetsId:assetsId.toString(),
								  userCode:$("#userCode").val()
								  },
							  dataType : 'json',
							  success: function(data){
								  dataList.search();
							  }
							});
		                }else{
		                	return;
		                }
				});
			}
		}else{
			ShowMsg(i18nProp('message_warn_asset_takeOver',line.substring(0, line.length - 1)));
			return;
		}
	});
	
	//manager operations
	$("#returnToProject").click(function() {
		flag=true;
		assetsId = new Array();
		line="";
		$(".dataList-div-body .row .dataList-checkbox-active").each(function(i){
			assetStatus = $(this).parent().find(".Status").text();
			if(assetStatus != "IN_USE"){
				flag = false;
				line+=$(this).next().text()+",";
				return;
			}else{
				assetsId[i]=$(this).attr("pk");
			}
		});
		if(flag){
			if(assetsId == ""){
				ShowMsg(i18nProp('none_select_record'));
				return;
			}else{
				//do i18n for all show message
				ShowMsg(i18nProp('message_confirm_asset_returnToProject', $('.row .dataList-checkbox-active').size().toString()),function(yes){
					 if (yes) {
						 $.ajax({
							 contentType : 'application/x-www-form-urlencoded', 
							  type: 'POST',
							  url: "customerAsset/changeStatus/"+"AVAILABLE",
							  data: {
								  _method: 'PUT',
								  "customerCode":$("#customerCode").val(),
								  "assetsId":assetsId.toString(),
								  "operation":"Return To Project"
								  },
							  dataType : 'json',
							  success: function(data){
								  dataList.search();
							  }
							});
		                }else{
		                	return;
		                }
				});
			}
		}else{
			ShowMsg(i18nProp('message_warn_asset_return',line.substring(0, line.length - 1)));
			return;
		}
		
		
	});
	

	$("#assgin").click(function(){
		
		$("#dialog").dialog({
	        autoOpen:false,
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
		
		assignIds = new Array();
		flag = true;
		line = "";
		
		$(".dataList-div-body .row .dataList-checkbox-active").each(function(i){
			assetStatus = $(this).parent().find(".Status").text();
			
			if(assetStatus == "AVAILABLE"||assetStatus =="IN_USE"){
				assignIds[i]=$(this).attr("pk");
			}else{
				flag = false;
				line+=$(this).next().text()+",";
				return;
			}
		});
		if(flag){
			if(assignIds == ""){
				ShowMsg(i18nProp('none_select_record'));
				return;
			}else{
				$("#ids").val(getActivedAssetIds());
				$("#dialog").dialog("open");
				
				$(".dropDownList").remove();
				$("#customer").DropDownList({
				       multiple : false,
				       header : false
				  });
			}
		}else{
			ShowMsg(i18nProp('message_warn_asset_assign',line.substring(0, line.length - 1)));
			return;
		}
	});
	
	
	$("#returnToIT").click(function(){
		assetsId=new Array();
		flag=true;
		line="";
		$(".dataList-div-body .row .dataList-checkbox-active").each(function(i){
			assetStatus = $(this).parent().find(".Status").text();
			if(assetStatus == "RETURNED"||assetStatus =="ASSIGNING"||
					assetStatus == "RETURNING_TO_IT" || $(this).siblings(".Ownership").html() != "Augmentum"){
				flag = false;
				line+=$(this).next().text()+",";
				return;
			}else{
				assetsId[i]=$(this).attr("pk");
			}
		});
		if(flag){
			if(assetsId == ""){
				ShowMsg(i18nProp('none_select_record'));
				return;
			}else{
				ShowMsg(i18nProp('message_confirm_asset_returnToIT', $('.row .dataList-checkbox-active').size().toString()),function(yes){
				 if (yes) {
					 $.ajax({
						  type: 'POST',
						  url: "customerAsset/changeStatus/"+"RETURNING_TO_IT",
						  data: {
							  _method: 'PUT',
							  customerCode:$("#customerCode").val(),
							  assetsId:assetsId.toString(),
							  "operation":"Return To IT"
							  },
						  dataType : 'json',
						  success: function(data){
							  dataList.search();
						  }
						});
	                }else{
	                	return;
	                }
				});
			}
		}else{
			ShowMsg(i18nProp('message_warn_asset_return',line.substring(0, line.length - 1)));
			return;
		}
	});
	
	$("#returnToCustomer").click(function(){
		assetsId=new Array();
		flag=true;
		line="";
		$(".dataList-div-body .row .dataList-checkbox-active").each(function(i){
			assetStatus = $(this).parent().find(".Status").text();
			
			if(assetStatus == "RETURNED"||assetStatus =="ASSIGNING"||
					assetStatus == "RETURNING_TO_IT" || $(this).siblings(".Ownership").html() == "Augmentum"){
				flag = false;
				line+=$(this).next().text()+",";
				return;
			}else{
				assetsId[i]=$(this).attr("pk");
			}
		});
		if(flag){
			if(assetsId == ""){
				ShowMsg(i18nProp('none_select_record'));
				return;
			}else{
				ShowMsg(i18nProp('message_confirm_asset_returnToCustomer', $('.row .dataList-checkbox-active').size().toString()),function(yes){
				 if (yes) {
					 $.ajax({
						  type: 'POST',
						  url: "customerAsset/changeStatus/"+"RETURNED",
						  data: {
							  _method: 'PUT',
							  customerCode:$("#customerCode").val(),
							  assetsId:assetsId.toString(),
							  "operation":"Return To Customer"
							  },
						  dataType : 'json',
						  success: function(data){
							  dataList.search();
						  }
						});
	                }else{
	                	return;
	                }
				});
			}
		}else{
			ShowMsg(i18nProp('message_warn_asset_return',line.substring(0, line.length - 1)));
			return;
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
	           	 $("#condition_assetType").val(criteria.assetType);
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

function getURL() {
	if ($("#customerCode").val() != "") {
		return "customerAsset/searchCustomerAssetsList?customerCode=" + $("#customerCode").val();
	} else {
		return "customerAsset/findAllCustomersAssets";
	}
}

//search list
var dataListInfo = {
    columns : [],
    criteria : criteria,
    minHeight : 150,
    pageSizes : [10, 20, 30, 50],
    hasCheckbox : true,
    pageItemSize : 5,
    url : getURL(),
    updateShowField : {
        url : 'searchCommon/column/updateColumns',
        callback : function(data) {
        	console.log(data);
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "searchCommon/column/getColumns?category=asset",
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
function searchList() {
    dataList = $(".dataList").DataList(dataListInfo);
    dataList.criteria = setCriteria();
    dataList.search();
}

//set Criteria
function setCriteria() {

    criteria.keyWord = $("#keyword").val();
    criteria.fromTime = $("#fromTime").val();
    criteria.toTime = $("#toTime").val();
    criteria.assetStatus = $("#status").val();
    criteria.assetType = $("#type").val();
    
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
    var assetStatus = "";
    $("#assetStatus").find(":checked").each(function() {
        if (assetStatus == null || assetStatus == "") {
            assetStatus = this.value;
        } else {
            assetStatus = assetStatus + "," + this.value;
        }
    });
    criteria.assetStatus = assetStatus;
    
    return criteria;
}

function checkInArr(Arr, ele) {
    console.log(Arr);
    for ( var i = 0; i < Arr.length; i++) {
       if (ele == Arr[i]) {
          return true;
       }
    }
    return false;
 }

function getIndexInArr(Arr, ele) {
    for ( var i = 0; i < Arr.length; i++) {
       if (ele == Arr[i]) {
          return i;
       }
    }
    return -1;
 }

function getActivedAssetIds() {
    var assetIds = [];
    $('.row .dataList-checkbox-active').each(function(){
        assetIds.push(($(this).attr('pk')));
    });
    return assetIds.toString();
}

$("#customer").change(function(){
	$("#projectName").val("");
	$("#projectCode").data("projectCode").clear();
	
	$("#userName").val("");
	$("#userId").data("userId").clear();
	
	var customerCode = $("#customer").val();
	getProjectsByCustomer(customerCode);
	getUsers();
});


//confirm assign assets
$("#confirm_assign").click(function() {
    
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'customerAsset/assginAssets',
        dataType : 'json',
        data: {
            userId: $("#userId").data("userId").getValue().join(),
            userName: $("#userName").val(),
            assetIds: getActivedAssetIds(),
            projectCode: $("#projectCode").data("projectCode").getValue().join(),
            assignCustomerCode: $("#customer").val()
        },
        success : function(data) {
        	$("div .dataList-div-loader").show();
            criteria.pageNum = 1;
            dataList.search();
        }
    });
    closeDialog();
    $("#dialog").dialog("close");
    $("div .dataList-div-loader").show();
});

// cancel assign assets
$("#cancel_assign").click(function() {
    closeDialog();
    $("#dialog").dialog("close");
});

// close dialog and clean text content
function closeDialog() {
    $("#projectName").val("");
    $("#projectCode").val("");
    
    $("#userName").val("");
    $("#userId").val(""); 
    
    $("#customer").find("option").each(function(){
    	$(this).removeAttr("selected");
    });
}

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