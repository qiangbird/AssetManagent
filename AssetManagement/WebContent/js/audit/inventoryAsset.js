var dataList;
var criteria = {};

var dataListInfo = {
	    columns : [],
	    criteria : criteria,
	    minHeight : 150,
	    pageSizes : [10, 20, 30, 50],
	    hasCheckbox : true,
	    pageItemSize : 5,
	    url : '',
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

var fileName = $("#auditFileName").val();

$(document).ready(function() {
	
	$(".filterDiv").filterBox({});
	
	$("#searchButton").click(function() {
	    
	    setCriteria();
	    criteria.pageNum = 1;
	    dataList.criteria = criteria;
	    dataList.search();
	});
	
	$(".filterDiv input[type='checkBox']").each(function(){
		if ($(this).val() != "all") {
			$(this).attr("content", $(this).siblings("label").html());
		}
	});

	$(".filterDiv input[type='text']").each(function(){
		$(this).attr("content", $(this).val());
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

	 // add place holder event for keyword
	 removePlaceholderForKeyWord();
	
	var flag = $("#flag").val();
	if(flag == "audited"){
		$("#auditLink").css("background", "#418FB5");
		$("#unAuditLink").css("background", "#71B3D6");
		$("#inconsistentLink").css("background", "#71B3D6");
		showInventoryAsset("audited");
	}
	else if(flag == "unaudited"){
		$("#auditLink").css("background", "#71B3D6");
		$("#unAuditLink").css("background", "#418FB5");
		$("#inconsistentLink").css("background", "#71B3D6");
		showInventoryAsset("unaudited");
	}
	else if(flag == "inconsistent"){
		$("#auditLink").css("background", "#71B3D6");
		$("#unAuditLink").css("background", "#71B3D6");
		$("#inconsistentLink").css("background", "#418FB5");
		showInventoryAsset("inconsistent");
	}
	
	$("#auditLink").click(function(){
		$("#auditLink").css("background", "#418FB5");
		$("#unAuditLink").css("background", "#71B3D6");
		$("#inconsistentLink").css("background", "#71B3D6");
		showInventoryAsset("audited");
	});
	
	$("#unAuditLink").click(function(){
		$("#auditLink").css("background", "#71B3D6");
		$("#unAuditLink").css("background", "#418FB5");
		$("#inconsistentLink").css("background", "#71B3D6");
		showInventoryAsset("unaudited");
	});
	
	$("#inconsistentLink").click(function(){
		$("#auditLink").css("background", "#71B3D6");
		$("#unAuditLink").css("background", "#71B3D6");
		$("#inconsistentLink").css("background", "#418FB5");
		showInventoryAsset("inconsistent");
	});
	
});


function showInventoryAsset(flag) {
//	$("#auditLink").css({"background":"#23a5e3", "color": "#ffffff","border-bottom":"1px solid #23a5e3"});
//	$("#unAuditLink").css({"background":"#ffffff","color":"#23a5e3","border-bottom":"1px solid #ffffff"});
//	$("#inconsistentLink").css({"background":"#ffffff","color":"#23a5e3","border-bottom":"1px solid #ffffff"});
	$.ajax({
		type: 'POST',
	    url: "searchCommon/column/getColumns?category=asset",
	    contentType : "application/json",
	    dataType : "json",
	    data: {},
	    success:function(data){
	    	$(".dataList > div:gt(0)").remove();
	    	dataListInfo.columns = data.columns;
	    	
	    	initTable(flag, fileName);
            dataList.setShow(data.showFields);
            dataList.search();
            
            //columns sortable event
            $(".dataList-div-fields").sortable({
                cancel: 'a',
                items: '>div:gt(0)',
                placeholder: "sortable-placeholder",
                revert: true,
                start: function(event, ui) {
                    $(ui.item).addClass("dataList-div-fields-border");
                },
                stop: function(event, ui){
                    $(ui.item).removeClass("dataList-div-fields-border");
                }
            });
	    }
	});
}

function initTable(flag, fileName) {
	$.ajax({
        type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        data : {
            categoryFlag:1
        },
        url : 'searchCommon/pageSize/getPageSize',
        error : function() {
            alert("get page size error");
        },
        success : function(data) {
            $(".dataList-a-pageSize").html(data.pageSize);
            criteria.pageSize = data.pageSize;
        }
    });
    criteria.pageNum = 1;
    criteria.sortName = 'updatedTime';
    criteria.sortSign = 'desc';
    criteria.auditFlag = flag;
    criteria.auditFileName = fileName;
    
    dataListInfo.criteria = criteria;
    dataListInfo.language = $("#locale").val().substring(0, 2).toUpperCase();
    dataListInfo.url = getURLForInventoryAsset(flag);
    
    dataList = $(".dataList").DataList(dataListInfo);
}

function getURLForInventoryAsset(flag) {
	if (flag == "audited" || flag == "unaudited") {
		return "audit/viewInventoryAsset";
	} else if (flag == "inconsistent") {
		return "inconsistent/viewInconsistentAsset";
	}
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

//change pic in brief style and content handle
function resultContentHandle(str) {
    if (!str || 'null' == str) {
        return '';
    }
    var temp = '';
    temp = str;
    if (str) {
        temp = temp.replace(/<br>|<br \/>|<p>|<\/p>/gi, ' ');
        if (/<img.*>/gi.test(temp)) {
            temp = temp.replace(/<img/g, "<img class='showImag'");
        }
    }
    return temp;
}

function removePlaceholderForKeyWord() {
    if ($("#keyword").val() != "") {
        $("#searchInputTipDiv").hide();
    } else {
        $("#searchInputTipDiv").show();
    }
    $("#keyword").focus(function() {
        $("#searchInputTipDiv").hide();
    });
    $("#keyword").blur(function() {
        if ($(this).val() == "") {
            $("#searchInputTipDiv").show();
        }
    });
    $("#searchInputTipDiv").click(function() {
        $(this).hide();
        $("#keyword").focus();
    });
}
