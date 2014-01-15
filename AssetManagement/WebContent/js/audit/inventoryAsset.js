var dataList;
var criteria = {};
var dataListInfo;

$(document).ready(function() {
    
    // categoryFlag = 1, it means category is 'asset'
//    initCriteria(1);
    criteria.isAudited = true;
    criteria.auditFileName = "2014-01-08_01";
    
    initFields("asset");
    
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
        
//        setCriteria();
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
    
     // add place holder event for keyword
     removePlaceholderForKeyWord();
     
     initDataList("audited");
     dataList.search();
     
});

function initDataList(flag) {
	
	// init dataList information for search list
	dataListInfo = {
	    columns : [],
	    criteria : criteria,
	    minHeight : 150,
	    pageSizes : [10, 20, 30, 50],
	    hasCheckbox : true,
	    pageItemSize : 5,
	    url : getURLForInventoryAsset(flag),
	    updateShowField : {
	        url : '/AssetManagement/searchCommon/column/updateColumns',
	        callback : function(data) {
	            $.ajax({
	                type : "POST",
	                contentType : "application/json",
	                url : "/AssetManagement/searchCommon/column/getColumns?category=asset",
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
	        url : '/AssetManagement/searchCommon/pageSize/updatePageSize',
	        callback : function() {
	        }
	    },
	    contentHandler : function(str) {
	        return resultContentHandle(str);
	    }
	};
	
	dataList = $(".dataList").DataList(dataListInfo);
}

function initFields(category) {
	$.ajax({
        type : "POST",
        contentType : "application/json",
        url : "/AssetManagement/searchCommon/column/getColumns?category=" + category,
        dataType : "json",
        data : {},
        error : function() {
            alert("init dataList error");
        },
        success : function(data) {
            var jsonData = data;
            dataListInfo.columns = jsonData.columns;
//            searchList();
            dataList.setShow(jsonData.showFields);
            
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

function getURLForInventoryAsset(flag) {
	if (flag == "audited" || flag == "unaudits") {
		return "/AssetManagement/audit/viewInventoryAsset";
	} else if (flag == "inconsistent") {
		return "";
	}
}