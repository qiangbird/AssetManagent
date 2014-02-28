var dataList;
var criteria = {};

var dataListInfo = {
	    columns : [],
	    minHeight : 150,
	    pageSizes : [10, 20, 30, 50],
	    hasCheckbox : true,
	    pageItemSize : 5,
	    url : 'todo/findTodoList?flag=' + $("#todoFlag").val(),
	    updateShowField : {
	        url : 'searchCommon/column/updateColumns',
	        callback : function(data) {
	            $.ajax({
	                type : "POST",
	                contentType : "application/json",
	                url : "searchCommon/column/getColumns?category=todo",
	                dataType : "json",
	                success : function(data) {
	                    dataList.opts.columns = data.columns;
	                    dataList.setShow(data.showFields);
	                    dataList.search();
	                }
	            });
	        }
	    },
	    contentHandler : function(str) {
	        return resultContentHandle(str);
	    }
};


$(document).ready(function(){
	
	// categoryFlag = 1, it means category is 'asset'
	initCriteria(11);
	findDataListInfo("todo");
    
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
    
    $("#keyword").keydown(function() {
        if(event.keyCode == 13) {
            setCriteria();
            criteria.pageNum = 1;
            dataList.criteria = criteria;
            dataList.search();
        }
    });
    
    $(".dateInput").datepicker({
        changeMonth: true,
        changeYear: true,
        dateFormat: "yy-mm-dd",
        yearRange: "2000:2030"
    });
    
     // add place holder event for keyword
     removePlaceholderForKeyWord();
	
    // confirm returned asset event
    $("#confirmReturnedButton").click(function(){
    	if (checkActivedAssetIds()) {
            ShowMsg(i18nProp('message_confirm_returnedAsset', $('.row .dataList-checkbox-active').size().toString()), function(yes){
                if (yes) {
                	$.ajax({
                        type : 'GET',
                        contentType : 'application/json',
                        url : 'todo/confirmReturnedAsset',
                        dataType : 'json',
                        data: {
                        	ids: getActivedAssetIds()
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
    
 // confirm received asset event
    $("#confirmReceivedButton").click(function(){
    	if (checkActivedAssetIds()) {
            ShowMsg(i18nProp('message_confirm_returnedAsset', $('.row .dataList-checkbox-active').size().toString()), function(yes){
                if (yes) {
                	$.ajax({
                        type : 'GET',
                        contentType : 'application/json',
                        url : 'todo/confirmReturnedAsset',
                        dataType : 'json',
                        data: {
                        	ids: getActivedAssetIds()
                        },
                        success : function(data) {
                            criteria.currentPage = 1;
                            dataList.search();
                        }
                    });
                }else{
                    return;
                }
              });
    	}
    });
    
});

function searchList() {
	dataList = $(".dataList").DataList(dataListInfo);
	dataList.search();
}

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
}

function getActivedAssetIds() {
    var assetIds = [];
    $('.row .dataList-checkbox-active').each(function(){
        assetIds.push(($(this).attr('pk')));
    });
    return assetIds.toString();
}

function checkActivedAssetIds() {
    var assetIds = getActivedAssetIds();
    if (assetIds == "") {
    	ShowMsg(i18nProp('none_select_record'));
        return false;
    }
    return true;
}
