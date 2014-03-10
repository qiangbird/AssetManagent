var dataList;
var criteria = {};

var dataListInfo = {
	    columns : [],
	    criteria : criteria,
	    minHeight : 150,
	    pageSizes : [10, 20, 30, 50],
	    hasCheckbox : false,
	    pageItemSize : 5,
	    url : '',
	    updateShowField : {
	        url : 'searchCommon/column/updateColumns',
	        callback : function(data) {
	            $.ajax({
	                type : "POST",
	                contentType : "application/json",
	                url : getCustomColumnAfterUpdate(),
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

var fileName = $("#fileName").val();

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
		$("#flag").val("audited");
		changeLinkBgcolor("#auditLink", "#unAuditLink", "#inconsistentLink");
		showInventoryAsset("audited");
	}
	else if(flag == "unaudited"){
		$("#flag").val("unaudited");
		changeLinkBgcolor("#unAuditLink", "#auditLink", "#inconsistentLink");
		showInventoryAsset("unaudited");
	}
	else if(flag == "inconsistent"){
		$("#flag").val("inconsistent");
		changeLinkBgcolor("#inconsistentLink", "#auditLink", "#unAuditLink");
		showInconsistent("inconsistent");
	}
	
	$("#auditLink").click(function(){
		$("#flag").val("audited");
		changeLinkBgcolor("#auditLink", "#unAuditLink", "#inconsistentLink");
		showFilterbox();
		showInventoryAsset("audited");
	});

	$("#unAuditLink").click(function(){
		$("#flag").val("unaudited");
		changeLinkBgcolor("#unAuditLink", "#auditLink", "#inconsistentLink");
		showFilterbox();
		showInventoryAsset("unaudited");
	});

	$("#inconsistentLink").click(function(){
		$("#flag").val("inconsistent");
		changeLinkBgcolor("#inconsistentLink", "#auditLink", "#unAuditLink");
		showFilterbox();
		showInconsistent("inconsistent");
	});
	
});


function showInventoryAsset(flag) {
	$.ajax({
		type: 'POST',
	    url: "searchCommon/column/getColumns?category=asset",
	    contentType : "application/json",
	    dataType : "json",
	    data: {},
	    success:function(data){
	    	$(".dataList > div:gt(0)").remove();
	    	dataListInfo.columns = data.columns;
	    	
	    	initTable(flag);
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

function showInconsistent(flag) {
	$.ajax({
		type: 'POST',
	    url: "searchCommon/column/getColumns?category=inconsistent",
	    contentType : "application/json",
	    dataType : "json",
	    data: {},
	    success:function(data){
	    	$(".dataList > div:gt(0)").remove();
	    	dataListInfo.columns = data.columns;
	    	
	    	initTable(flag);
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

function initTable(flag) {
	if (flag == "inconsistent") {
		categoryFlag = 12;
	} else {
		categoryFlag = 1;
	}
	
	$.ajax({
        type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        data : {
            categoryFlag:categoryFlag
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
    criteria.auditFileName = $("#fileName").val();
    
    dataListInfo.criteria = criteria;
    dataListInfo.language = $("#locale").val().substring(0, 2).toUpperCase();
    dataListInfo.url = getURLForInventoryAsset(flag);
    
    dataList = $(".dataList").DataList(dataListInfo);
}

function getURLForInventoryAsset(flag) {
	if (flag == "audited" || flag == "unaudited") {
		return "audit/viewInventoryAsset";
	} else if (flag == "inconsistent") {
		return "inconsistent/findInconsistentList?auditFileId=" + $("#auditFileId").val();
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

function showFilterbox() {
	$(".filterDiv").show();
	$(".filterDiv").css("display", "inline-block");
	$("#searchButton").css("left", "50px");
	$("#customizedViewButton").show();
}

function changeLinkBgcolor(link1, link2, link3) {
	$(link1).addClass("selected-status");
	$(link2).removeClass("selected-status");
	$(link3).removeClass("selected-status");
}

function getCustomColumnAfterUpdate() {
	var categoryType = $("#flag").val();
	
	if (categoryType == "inconsistent") {
		return "searchCommon/column/getColumns?category=inconsistent";
	} else {
		return "searchCommon/column/getColumns?category=asset";
	}
}