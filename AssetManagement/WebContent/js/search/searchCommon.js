
// Prepare data for dataList
function findDataListInfo(category) {
    $.ajax({
        type : "POST",
        contentType : "application/json",
        url : "searchCommon/column/getColumns?category=" + category,
        dataType : "json",
        data : {},
        error : function() {
            alert("init dataList error");
        },
        success : function(data) {
            var jsonData = data;
            dataListInfo.columns = jsonData.columns;
            searchList();
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

function initCriteria(categoryFlag) {
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
    dataListInfo.criteria = criteria;
    
    dataListInfo.language = $("#locale").val().substring(0, 2).toUpperCase();
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