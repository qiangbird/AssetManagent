var dataList;
var criteria = {};

$(document).ready(function() {
    // categoryFlag = 1, it means category is 'asset'
    initCriteria(1);
    findDataListInfo("asset");
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
    
    function AddI18n(message) {
        $("#label_" + message).html(msg(message));
        if (message == "CheckInTime") {
            var $temp = $("#label_" + message).parent().siblings(".condition_optional").children("p").children("input");
            $temp.attr("content", msg(message));
        } 
        $("#label_" + message).siblings("input").attr("content", msg(message));
     }
//     var localeCode = $("#localeCode").val();
    var i18n = $("#language").val();
     jQuery.i18n.properties({
        name : 'message',
        path : 'i18n/',
        mode : 'map',
        language : i18n,
        callback : function() {
//           msg = jQuery.i18n.prop;
//           AddI18n('SearchButton');
//           AddI18n('SearchConditionReset');
//           AddI18n('KeywordPlaceholder');
//           AddI18n('SearchBy');
//           AddI18n('CheckedAllFields');
//           AddI18n('CheckedAllTypes');
//           AddI18n('CheckedAllStatus');
//           AddI18n('AssetId');
//           AddI18n('AssetName');
//           AddI18n('User');
//           AddI18n('Project');
//           AddI18n('Customer');
//           AddI18n('PoNo');
//           AddI18n('BarCode');
//           AddI18n('AssetType');
//           AddI18n('Machine');
//           AddI18n('Monitor');
//           AddI18n('Device');
//           AddI18n('Software');
//           AddI18n('OtherAssets');
//           AddI18n('AssetStatus');
//           AddI18n('Available');
//           AddI18n('InUse');
//           AddI18n('Idle');
//           AddI18n('Returned');
//           AddI18n('CheckInTime');
//           AddI18n('Operation_Warning');
        }
     });
     
});

//search list
var dataListInfo = {
    columns : [],
    criteria : criteria,
    minHeight : 150,
    pageSizes : [10, 20, 30, 50],
    hasCheckbox : true,
    pageItemSize : 5,
    url : 'customerAsset/searchCustomerAssetsList?customerCode='+$("#customerCode").val(),
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


//Below is  about the operations of assets
$(document).ready(function(){
	$("#dialog").dialog({
        autoOpen:false,
        closeOnEscape: true,
        draggable: false,
        height: 280,
        width: 500,
        show: "blind",
        hide: "blind",
        modal: true,
        position: "center",
        resizable: false,
        title: i18nProp('manageAssign_dialog_title'),
        bgiframe: true
    });
	//operation menu
	$(".operation_assets_list").hover(
			function() {
				$(".operation_assets_list ul li").show();
	}, 
	function() {
		$(".operation_assets_list ul li").hide();
	});
	
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
				ShowMsg(i18nProp('operation_confirm_message'),function(yes){
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
			ShowMsg(i18nProp('status_error_prompt_message',line));
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
			if(assetStatus == "RETURNED"||assetStatus =="ASSIGNING"||
					assetStatus == "RETURNING_TO_IT"||assetStatus =="RETURNING_TO_CUSTOMER"
						||assetStatus == "AVAILABLE"){
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
				ShowMsg(i18nProp('operation_confirm_message'),function(yes){
					 if (yes) {
						 $.ajax({
							 contentType : 'application/x-www-form-urlencoded', 
							  type: 'POST',
							  //Status  <--  buttton value
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
			ShowMsg(i18nProp('status_error_prompt_message',line));
			return;
		}
		
		
	});
	

	$("#assgin").click(function(){
		assignIds = new Array();
		flag=true;
		line="";
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
			$("#dialog").dialog("open");
			$(".dropDownList").remove();
			$("#DropList").DropDownList({
			       multiple : false,
			       header : false
			  });
			}
		}else{
			ShowMsg(i18nProp('status_error_prompt_message',line));
			return;
		}
		
		
	});

	//select all employee below customer
	$("#user").click(function(){
		customerCode = $("#customerCode").val();
		userCode="";
	      $.ajax({
	          type : 'GET',
	          contentType : 'application/json',
	          url : 'user/getEmployeeAsCustomer',
	          dataType : 'json',
	          data:{customerCode:customerCode},
	          success : function(data) {
	             console.log(data);
	             employeeName = [];
	             employeeCode = [];
	             length = data.employeeInfo.length;
	             for ( var i = 0; i < length; i++) {
	                employeeName[i] = data.employeeInfo[i].label;
	                employeeCode[i] = data.employeeInfo[i].employeeCode;
	             }
	             userArray = employeeName;
	             $("#user").autocomplete(
	            		 {
                       	 minLength: 0,
                         source : employeeName,
                         select : function(e,ui) {
 	                       $("#assetUserCode").val(employeeCode[getIndexInArr(employeeName,ui.item.label)]);
 	                     }
                         
                         });
	          },
	          error : function() {
	             alert("error");
	          }
	      });
	});
	
	$(".submit-button").click(function(){
		$("#ids").val(assignIds.toString());
	});
	$("#returnToIT").click(function(){
		assetsId=new Array();
		flag=true;
		line="";
		$(".dataList-div-body .row .dataList-checkbox-active").each(function(i){
			assetStatus = $(this).parent().find(".Status").text();
			if(assetStatus == "RETURNED"||assetStatus =="ASSIGNING"||
					assetStatus == "RETURNING_TO_IT"||assetStatus =="RETURNING_TO_CUSTOMER"){
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
				ShowMsg(i18nProp('operation_confirm_message'),function(yes){
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
			ShowMsg(i18nProp('status_error_prompt_message',line));
			return;
		}
	});
});

//function i18nProp(message,line) {
//	if(line==""||line==null){
//		return $.i18n.prop(message);
//	}else{
//		return $.i18n.prop(message,line.substr(0,line.length-1));
//	}
//}
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
