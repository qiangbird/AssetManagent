var dataList;
var criteria = {};

$(document).ready(function() {
    // categoryFlag = 9, it means category is 'transferLog'
    initCriteria(9);
    findDataListInfo("transfer log");
    $(".filterDiv").filterBox({});
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
    url : 'transferLog/search?id='+$("#assetUuId").val(),
    updateShowField : {
        url : 'searchCommon/column/updateColumns',
        callback : function(data) {
        	console.log(data);
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "searchCommon/column/getColumns?category=transfer log",
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
    }
//    ,
//    contentHandler : function(str) {
//        return resultContentHandle(str);
//    }
};
function searchList() {
    dataList = $(".dataList").DataList(dataListInfo);
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
}


//Below is  about the operations of assets
