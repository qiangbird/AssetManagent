var dataList;
var criteria = {};

var dataListInfo = {
	    columns : [],
	    minHeight : 150,
	    pageSizes : [10, 20, 30, 50],
	    hasCheckbox : true,
	    pageItemSize : 5,
	    url : getToDoURL(),
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
	    updateShowSize : {
	        url : 'searchCommon/pageSize/updatePageSize',
	        callback : function() {
	        }
	    },
	    contentHandler : function(str) {
	        return resultContentHandle(str);
	    }
};


$(document).ready(function(){
	
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "searchCommon/column/getColumns?category=todo",
		dataType : "json",
		data : {},
		error : function() {
			alert("init dataList error");
		},
		success : function(data) {
			dataListInfo.columns = data.columns;
			searchList();
			dataList.setShow(data.showFields);

			// columns sortable event
			$(".dataList-div-fields").sortable({
				cancel : 'a',
				items : '>div:gt(0)',
				placeholder : "sortable-placeholder",
				revert : true,
				start : function(event, ui) {
					$(ui.item).addClass("dataList-div-fields-border");
				},
				stop : function(event, ui) {
					$(ui.item).removeClass("dataList-div-fields-border");
				}
			});
		}
	});
	
	
	criteria.pageNum = 1;
    criteria.sortName = 'updatedTime';
    criteria.sortSign = 'desc';
    
    dataListInfo.criteria = criteria;
    dataListInfo.language = $("#locale").val().substring(0, 2).toUpperCase();
    
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
    
});

function searchList() {
	dataList = $(".dataList").DataList(dataListInfo);
	dataList.search();
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

function getToDoURL() {
	var todoFlag = $("#todoFlag").val();
	var url = "todo/viewReturnedAsset";
	
	if (todoFlag == "returned") {
		url = "todo/viewReturnedAsset";
	} else if (todoFlag == "received") {
		url = "todo/viewReceivedAsset";
	} else {
		url = "todo/viewReturnedAsset";
	}
	return url;
	
}