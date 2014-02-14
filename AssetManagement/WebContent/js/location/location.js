var dataList;
var criteria = {};

$(document).ready(function() {
    
    // categoryFlag = 7, it means category is 'location'
    initCriteria(7);
    findDataListInfo("location");
    
    $("#searchButton").click(function() {
        
        setCriteria();
        criteria.pageNum = 1;
        dataList.criteria = criteria;
        dataList.search();
    });
    
    // add keypress event for search feature
    $("#keyword").keydown(function() {
        if(event.keyCode == 13) {
            setCriteria();
            criteria.pageNum = 1;
            dataList.criteria = criteria;
            dataList.search();
        }
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
     
});

// init dataList information for search list
var dataListInfo = {
    columns : 
    [{ EN : 'Site', ZH : '地点', sortName : 'site', width : 200, headerId: 1, isMustShow : true },
    { EN : 'Room', ZH : '房间号', sortName : 'room', width : 200, headerId : 11 },
    { EN : 'Operation', ZH : '操作', sortName : 'operation', width : 300, headerId : 12 }],
    criteria : criteria,
    minHeight : 150,
    pageSizes : [10, 20, 30, 50],
    hasCheckbox : true,
    pageItemSize : 5,
//    url : 'asset/searchAsset',
    url : 'location/searchLocation',
    updateShowField : {
        url : 'searchCommon/column/updateColumns',
        callback : function(data) {
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "searchCommon/column/getColumns?category=location",
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
    dataList.search();
}

//set Criteria(search conditions) for search feature
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

// gain customer name autocomplete
$("#customerName").focus(function() {
    $(this).attr("placeholder", "");
    var custCode = [];
    var custName = [];
    
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'customer/getCustomerInfo',
        dataType : 'json',
        success : function(data) {
           var length = data.customerList.length;
           for ( var i = 0; i < length; i++) {
              custName[i] = data.customerList[i].customerName;
              custCode[i] = data.customerList[i].customerCode;
           }
           $("#customerName").autocomplete(
               {
                  minLength: 0,
                  source : custName,
                  select : function(e, ui) {
                      $("#customerCode").val(custCode[getIndexInArr(custName, ui.item.value)]);
                      $("#projectName").val("");
                   }
             });
            }
         });
});

$("#customerName").blur(function() {
    if ($(this).val() == "") {
        $(this).attr("placeholder", "Please enter customer name");
    }
});


// gain project name autocomplete based on customer name
$("#projectName").focus(function() {
    $(this).attr("placeholder", "");
    var customerCode = $("#customerCode").val();
    var projectName = [];
    var projectCode = [];
    
    if (customerCode != "") {
        $.ajax({
          type : 'GET',
          contentType : 'application/json',
          url : 'project/getProjectByCustomerCode?customerCode='+customerCode,
          dataType : 'json',
          success : function(data) {
              var length = data.projectList.length;
              for ( var i = 0; i < length; i++) {
                  projectName[i] = data.projectList[i].projectName;
                  projectCode[i] = data.projectList[i].projectCode;
               }
               $("#projectName").autocomplete({
                  minLength: 0,
                  source : projectName,
                  select : function(e, ui) {
                     $("#projectCode").val(projectCode[getIndexInArr(projectName, ui.item.value)]);
                  }
               });
            }
        });
    } else {
        return;
    }
});

$("#projectName").blur(function() {
    if ($(this).val() == "") {
        $(this).attr("placeholder", "Please enter project name");
    }
});

// asset reviver autocomplete (all employees)
$("#userName").focus(function() {
    $(this).attr("placeholder", "");
    var employeeName = [];
    var employeeValue = [];
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'user/getEmployeeDataSource',
        dataType : 'json',
        success : function(data) {
            var length = data.employeeInfo.length;
            for ( var i = 0; i < length; i++) {
               employeeName[i] = data.employeeInfo[i].label;
               employeeValue[i] = data.employeeInfo[i].value;
            }
            $("#userName").autocomplete({
               minLength: 0,
               source : employeeName,
               select : function(e, ui) {
                    var temp = employeeValue[getIndexInArr(employeeName, ui.item.value)];
                   $("#userId").val(temp.split("#")[1]);
               }
            });
        }
    });
 });

$("#userName").blur(function() {
    if ($(this).val() == "") {
        $(this).attr("placeholder", "Please enter user name");
    }
});

// get all checkbox active asset ids
function getActivedAssetIds() {
    var assetIds = [];
    var assetIdsStr = "";
    $('.row .dataList-checkbox-active').each(function(){
        assetIds.push(($(this).attr('pk')));
    });
    
    for (var i = 0; i < assetIds.length; i++) {
        assetIdsStr = (assetIdsStr + assetIds[i]) + (((i + 1) == assetIds.length) ? '':','); 
    }
    return assetIdsStr;
}

// check checkbox actived asset
function checkActivedAssetIds() {
    var assetIds = getActivedAssetIds();
    if (assetIds == "") {
        ShowMsg("Please select one asset at least");
        return false;
    }
    return true;
}

// check acticed assets status for assign
function checkActivedAssetsStatusForAssign() {
    var flag = true;
    $('.row .dataList-checkbox-active').each(function(){
        if ($(this).siblings(".Status").html() == "ASSIGNING") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Assigning asset can not be assigned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "RETURNING_TO_CUSTOMER") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Returning to customer asset can not be assigned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "RETURNING_TO_IT") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Returning to IT asset can not be assigned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "RETURNED") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Returned asset can not be assigned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "BORROWED") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Borrowed asset can not be assigned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "IDLE") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Idle asset can not be assigned");
            flag = false;
        } 
    });
    return flag;
}

// check actived assets status and ownership for return to customer
function checkActivedAssetsStatusForReturn() {
    var flag = true;
    $('.row .dataList-checkbox-active').each(function(){
        if ($(this).siblings(".Status").html() == "ASSIGNING") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Assigning asset can not be returned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "RETURNING_TO_CUSTOMER") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Returning to customer asset can not be returned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "RETURNING_TO_IT") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Returning to IT asset can not be returned");
            flag = false;
        } else if ($(this).siblings(".Status").html() == "RETURNED") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Returned asset can not be returned");
            flag = false;
        } else if ($(this).siblings(".Ownership").html() == "Augmentum") {
            ShowMsg("Line " + $(this).siblings(".w-30").html() + ": Ownership is Augmentum, asset can not be returned to customer");
            flag = false;
        }
    });
    return flag;
}

// confirm assign assets
$("#confirm_assign").click(function() {
    
    if ($("#customerName").val() == "" || $("#customerCode").val() == "") {
        $("#customerName").css("border-color", "red");
        return;
    } else {
        $.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'asset/itAssignAssets',
            dataType : 'json',
            data: {
                userId: $("#userId").val(),
                assetIds: getActivedAssetIds(),
                projectName: $("#projectName").val(),
                projectCode: $("#projectCode").val(),
                customerName: $("#customerName").val(),
                customerCode: $("#customerCode").val()
            },
            success : function(data) {
                criteria.pageNum = 1;
                dataList.search();
            }
        });
        closeDialog();
    }
});

// cancel assign assets
$("#cancel_assign").click(function() {
    closeDialog();
});

// close dialog and clean text content
function closeDialog() {
    $("#projectName").val("");
    $("#projectCode").val("");
    $("#projectName").attr("placeholder", "Please enter project name");
    
    $("#customerName").val("");
    $("#customerCode").val("");
    $("#customerName").attr("placeholder", "Please enter customer name");
    $("#customerName").css("border-color", "");
    
    $("#userName").val("");
    $("#userId").val("");
    $("#userName").attr("placeholder", "Please enter user name");
    
    $("#dialog_assign").dialog("close");
}





$(document).ready(function(){
	sites = [];
	$("#dialog").dialog({
        autoOpen:false,
        closeOnEscape: true,
        draggable: false,
        height: 220,
        width: 400,
        show: "blind",
        hide: "blind",
        modal: true,
        position: "center",
        resizable: false,
        title: i18nProp("Location_Management"),
        bgiframe: true
    });
	
	$("#addButton").click(function(){
		$("#site").removeClass("site-error");
		$("#room").removeClass("site-error");
		$("#dialog").dialog("open");
		$("#location_id").val("");
		$("#site").val("");
		$("#room").val("");
	});
	
	$("#sites").DropDownList({
	    multiple : false,
	    header : false,
	    noneSelectedText : 'Select site',
	});
	
	$("#site").click(function(){
		$("#site").removeClass("site-error");
		  $.ajax({
			    type : 'GET',
			    contentType : 'application/json',
			    url : 'location/getLocationSites',
			    dataType : 'json',
			    success : function(data) {
			    	console.log(data.siteList);
			    	length = data.siteList.length;
			    	for(i = 0;i< length; i++){
			    		sites[i] = data.siteList[i].siteNameAbbr;
			    	}
			    	$("#site").autocomplete({
			            minLength : 0,
			            source : sites
			            });
			     }
			  });
	});
	$("#room").click(function(){
	$("#room").removeClass("site-error");
	});
	//edit group
	$(".dataList").delegate(".editLocationIcon","click",function(){
		$("#site").removeClass("site-error");
		$("#room").removeClass("site-error");
		var pk = $(this).parents(".row").find(".dataList-div-checkbox").attr("pk");
		$.ajax({
		    type : 'GET',
		    contentType : 'application/json',
		    url : 'location/edit/' + pk,
		    dataType : 'json',
		    success : function(data) {
				$("#dialog").dialog("open");
				$("#location_id").val(data.location.id);
				$("#site").val(data.location.site);
				$("#room").val(data.location.room);
		     }
		  });
	});
    //delete group
    	$(".dataList").delegate(".deleteLocationIcon","click",function(){
    		var pk = $(this).parents(".row").find(".dataList-div-checkbox").attr("pk");
    		
    		
    		ShowMsg(i18nProp('operation_confirm_message'),function(yes){
				 if (yes) {
					 $.ajax({
			    		    type : 'DELETE',
			    		    contentType : 'application/json',
			    		    url : 'location/delete/' + pk,
			    		    dataType : 'json',
			    		    data:{
			    		    	 _method: 'DELETE',
			    		    },
			    		    success : function(data) {
			    		    	dataList.search();
			    		     }
			    		  });
	                }else{
	                	return;
	                }
				});
    	});
    	
    	//submit
    	$("#submitLocation").click(function(){
    	    flag = 0;
    		inputSite = $("#site").val()
    		inputRoom = $("#room").val()
    		if(inputSite.trim()==""){
    			$("#site").addClass("site-error");
    			flag = 1;
    		}
    		if(inputRoom.trim()==""){
    			$("#room").addClass("site-error");
    			flag = 2;
    		}
    		if(flag==0){
    		if(checkInArr(sites,$("#site").val())){
    			$("#dialog").submit();
    		}else{
    			$("#site").addClass("site-error");
    			return;
    		}
    		}
    		
    	});
	
});


//common method
function checkInArr(Arr, ele) {
	    console.log(Arr);
	    for ( var i = 0; i < Arr.length; i++) {
	        if (ele == Arr[i]) {
	            return true;
	        }
	    }
	    return false;
	}