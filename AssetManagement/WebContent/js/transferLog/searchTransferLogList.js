var dataList;
var criteria = {};

$(document).ready(function() {
    // categoryFlag = 9, it means category is 'transferLog'
    initCriteria(9);
    findDataListInfo("transfer log");
    
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
    
    removePlaceholderForKeyWord();
    
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
        callback : function() {
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
    
    return criteria;
}
