var dataList;
var criteria = {};

$(document).ready(function() {
    
    // categoryFlag = 1, it means category is 'asset'
    initCriteria(1);
    findDataListInfo("asset");
    
    $(".filterDiv").filterBox({});
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
    
    function AddI18n(message) {
        $("#label_" + message).html(msg(message));
        if (message == "CheckInTime") {
            var $temp = $("#label_" + message).parent().siblings(".condition_optional").children("p").children("input");
            $temp.attr("content", msg(message));
        } 
        $("#label_" + message).siblings("input").attr("content", msg(message));
     }
     var localeCode = $("#localeCode").val();
     if (localeCode == 'en') {
        i18n = 'en_US';
     } else {
        i18n = 'zh_CN';
     }
     jQuery.i18n.properties({
        name : 'message',
        path : 'i18n/',
        mode : 'map',
        language : i18n,
        callback : function() {
           msg = jQuery.i18n.prop;
           AddI18n('SearchButton');
           AddI18n('SearchConditionReset');
           AddI18n('KeywordPlaceholder');
           AddI18n('SearchBy');
           AddI18n('CheckedAllFields');
           AddI18n('CheckedAllTypes');
           AddI18n('CheckedAllStatus');
           AddI18n('AssetId');
           AddI18n('AssetName');
           AddI18n('User');
           AddI18n('Project');
           AddI18n('Customer');
           AddI18n('PoNo');
           AddI18n('BarCode');
           AddI18n('AssetType');
           AddI18n('Machine');
           AddI18n('Monitor');
           AddI18n('Device');
           AddI18n('Software');
           AddI18n('OtherAssets');
           AddI18n('AssetStatus');
           AddI18n('Available');
           AddI18n('InUse');
           AddI18n('Idle');
           AddI18n('Returned');
           AddI18n('CheckInTime');
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
     
     // assign assets
     $("#assignAssetsButton").parent("li").click(function() {
         
         $("#dialog_assign").dialog({
             title: "IT Assign Assets",
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
             ShowMsg($('.row .dataList-checkbox-active').size() + " assets will be returned to customer. Are you sure?", function(yes){
                 if (yes) {
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
             ShowMsg($('.row .dataList-checkbox-active').size() + " assets will be changed to fixed assets. Are you sure?", function(yes){
                 if (yes) {
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
             ShowMsg($('.row .dataList-checkbox-active').size() + " assets will be changed to non-fixed assets. Are you sure?", function(yes){
                 if (yes) {
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
             tipMessage = $('.row .dataList-checkbox-active').size() + " assets will be added to audit. Are you sure?";
             ShowMsg(tipMessage, function(yes){
                 if (yes) {
                     window.location.href = "asset/addAssetsToAuditForSelected?assetIds=" + getActivedAssetIds();
                 }else{
                     return;
                 }
             });
         } else {
             tipMessage = $(".dataList .dataList-div-perPage span:nth-child(3)").html() + " assets will be added to audit. Are you sure?";
             ShowMsg(tipMessage, function(yes){
                 if (yes) {
                     window.location.href = "asset/addAssetsToAuditForSearchResult";
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
    url : 'asset/allAssetsList',
    updateShowField : {
        url : 'searchCommon/column/updateColumns',
        callback : function(data) {
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
        url : 'asset/getCustomerInfo',
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
    $('.row .dataList-checkbox-active').each(function(){
        assetIds.push(($(this).attr('pk')));
    });
    return assetIds.toString();
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
    var lineNum = "";
    $('.row .dataList-checkbox-active').each(function(){
        var status = $(this).siblings(".Status").html();
       
        if (status == "ASSIGNING" || status == "RETURNING_TO_CUSTOMER"
            || status == "RETURNING_TO_IT" || status == "RETURNED"
                || status == "BORROWED" || status == "IDLE") {
            
            lineNum += $(this).siblings(".w-30").html() + ", ";
            flag = false;
        } 
    });
    
    if (!flag) {
        ShowMsg("Line " + lineNum.substring(0, lineNum.length - 2) + ": illegal status asset can not be assigned");
    }
    return flag;
}

// check actived assets status and ownership for return to customer
function checkActivedAssetsStatusForReturn() {
    var flag = true;
    var lineNum = "";
    $('.row .dataList-checkbox-active').each(function(){
        var status = $(this).siblings(".Status").html();
        
        if (status == "ASSIGNING" || status == "RETURNING_TO_CUSTOMER"
            || status == "RETURNING_TO_IT" || status == "RETURNED"
                || $(this).siblings(".Ownership").html() == "Augmentum") {
            
            lineNum += $(this).siblings(".w-30").html() + ", ";
            flag = false;
        } 
    });
    
    if (!flag) {
        ShowMsg("Line " + lineNum.substring(0, lineNum.length - 2) + ": illegal status asset can not be returned");
    }
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
