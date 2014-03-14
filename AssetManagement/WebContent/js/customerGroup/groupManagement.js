var dataList;
var criteria = {};

$(document).ready(function() {
    
    initCriteria(10);
    findDataListInfo("group");
    
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
    columns : [],
    criteria : criteria,
    minHeight : 150,
    pageSizes : [10, 20, 30, 50],
    hasCheckbox : true,
    pageItemSize : 5,
    url : 'group/search',
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
}

$(document).ready(function(){
	$("#dialog").dialog({
        autoOpen:false,
        closeOnEscape: true,
        draggable: false,
        height: 380,
        width: 600,
        show: "blind",
        hide: "blind",
        modal: true,
        position: "center",
        resizable: false,
        title: i18nProp('group_operation'),
        bgiframe: true
    });

	getCustomerInfo = function(){
		$.ajax({
    	    type : 'GET',
    	    contentType : 'application/json',
    	    url : 'customer/getCustomerInfo',
    	    dataType : 'json',
    	    success : function(data) {
    	    	length = data.customers.length;
    	    	var customers = [];
        	    for ( var i = 0; i < length; i++) {
        	        customers.push({
        	        	label:data.customers[i].customerName,
        	        	value:data.customers[i].customerCode
        	        });
        	    }
        	   $("#customers").autoComplete({
        	    	source:customers,
            		width:215, 
                    height:90, 
                    dropdownWidth:150, 
                    maxRows:10, 
                    minChars:1, 
                    searchDelay:100, 
            		tokenDelimiter:','
                 });
    	    }
    	});
	} ;
	
	// init autocomplete
	if($("#customers").getAutoCompleteInstance()==null){
   		getCustomerInfo();
   	}
	
    $("#addButton").click(function(){
    	getCustomerInfo();
    	$(".token-input-list-facebook").remove();
        $("#dialog").dialog("open");
        $(".dropDownList").remove();
        $("#groupId").val("");
        $("#groupName").val("");
        $("#description").val("");
        $("#customers").val("");
        $("#processType").DropDownList({
    	    multiple : false,
    	    header : false,
    	    noneSelectedText : 'Select process type',
    	});
        
    });
	
    //delete group
	$(".dataList").delegate(".deleteGroupIcon","click",function(){
		
		var pk = $(this).parents(".row").find(".dataList-div-checkbox").attr("pk");
		ShowMsg(i18nProp('operation_confirm_message'),function(yes){
			 if (yes) {
				 $.ajax({
	    		       type : 'get',
	    		       contentType : 'application/json',
	    		       url : 'group/delete?id='+ pk,
	    		       dataType : 'json',
	    		       success : function(data) {
	    		    	   dataList.search();
	    		       },
	    		       error : function() {
	    		          alert("error");
	    		       }
	    		    });
                }else{
                	return;
                }
			});
	});
    
	//edit group
	$(".dataList").delegate(".editGroupIcon","click",function(){
		
		var pk = $(this).parents(".row").find(".dataList-div-checkbox").attr("pk");
		$.ajax({
		       type : 'get',
		       contentType : 'application/json',
		       url : 'group/edit?id='+ pk,
		       dataType : 'json',
		       success : function(data) {
		    	   console.log(data);
		    	   $("#dialog").dialog("open");
		    	   $(".dropDownList").remove();
		    	   $("#groupId").val(data.customerGroup.id);
               $("#groupName").val(data.customerGroup.groupName);
       	      $("#description").val(data.customerGroup.description);
       	      $("#processType").val(data.customerGroup.processType);
		   	  $("#processType").DropDownList({
		       	    multiple : false,
		       	    header : false,
		       	    noneSelectedText : 'Select process type',
		       	});
		   	  length = data.customerList.length;
		   	  customerNames = [];
		   	  customerCodes = [];
		   	  for(var i = 0; i<length; i++){
		   		customerNames[i] = data.customerList[i].customerName+";";
		   		customerCodes[i] = data.customerList[i].customerCode;
		   	  }
		   	  $(".token-input-token-facebook").remove();
		   	  $("#customerName").val(customerNames);
		   	  $("#customerCode").val(customerCodes);
		   	loadAutoCompData($("#customerName"), $("#customerCode"), $("#customers"));
		       },
		       error : function() {
		          alert("error");
		       }
		    });
	});
	
	//submit
	$("#submitGroup").click(function(){
	    flag = 0;
		inputGroupName = $("#groupName").val();
		inputProcessType= $("#processType").val();
		
		if(inputGroupName.trim()==""){
			$("#groupName").addClass("group-error");
			flag = 1;
			alert("aaaa");
		}
		$(".token-input-token-facebook").each(function(i){
			if($(this).attr("error")){
				$(this).addClass("input-error-autocomplete");
				flag = 3;
			}
		
	});
		
		if(flag==0){
			$("#dialog").submit();
		}
		
	});
	
	$("#groupName").click(function(){
		$("#groupName").removeClass("group-error");
		});
});

/**
 * Get auto complete values for edit
 * 
 * @param sourceObj:
 *            hidden area
 * @param showObj:
 *            input for show
 */
function loadAutoCompData(sourceLabelObj, sourceValueObj, showObj) {
	if (sourceLabelObj.val() == '' || sourceValueObj.val() == '') {
		return;
	}
	var sourceLabel = sourceLabelObj.val().split(";,");
	var sourceValue = sourceValueObj.val().split(",");
	for (var i = 0; i < sourceValue.length; i++) {
		var item = {
			label : sourceLabel[i],
			value : sourceValue[i]
		};
		showObj.getAutoCompleteInstance().add(item);
	}
}









