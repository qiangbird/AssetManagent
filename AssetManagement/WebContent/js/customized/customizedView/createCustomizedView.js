var searchConditionInfoMap = [];
var valueInfoMap = [];
var isEditting = false;

var newColumnNames = [];
var newColumnTypes = [];
var newRealNames = [];
var newRealTables = [];
var newSearchColumns = [];

var columns = [];
var searchConditions = [];
var values = [];
var customizedViewItemIds = [];
var isDeletes = [];
var columnTypes = [];
var searchColumns = [];
var realTables = [];

var allAssetPage = "allAssetPage";
var customerAssetPage = "customerAssetPage";
var transferLogPage = "transferLogPage";
var operationLogPage ="operationLogPage";

$(document).ready(function() {
	initBindData();
	getValueFromList();
    chooseColumn();
    
    var prePage = $("#prePage").val();
    if(allAssetPage == prePage){
    	$(".allAssetPage").show();
    }else if(customerAssetPage == prePage){
    	$(".customerAssetPage").show();
    }else if(transferLogPage == prePage){
    	$(".transferLogPage").show();
    }else if(operationLogPage == prePage){
    	$(".operationLogPage").show();
    }
    
    $("#columnName, #viewName").blur(function(){
		//delete the tips when input data exists
		if("" != $(this).val()){
			$(this).clearValidationMessage();
		}
	});
    
    $("#addToFilter").click(function(){
    	var validations = new Array();
        
    	var columnName = $("#columnName").data("columnName").getValue().join();
    	var searchCondition = $("#searchCondition").data("searchCondition").getValue().join();
    	
        validations.push($("#columnName")
    			.validateNull(columnName,i18nProp('message_warn_column_is_null')));
        validations.push($("#searchCondition")
        		.validateNull(searchCondition,i18nProp('message_warn_criteria_is_null')));
        
        if("failed" == recordFailInfo(validations)){
        	return;
        }else{
        	var value = "";
        	var columnType = $("#columnType").val();
        	var searchColumn = $("#searchColumn").val();
        	var realTable = $("#realTable").val();
        	
        	if("none" == $("#value").css("display")){
        		value = $(".datepic").val();
        	}else{
        		value = $("#value").data("value").getValue().join();
        	}
        	doAppendFunction(columnName, searchCondition, columnType, searchColumn, realTable, value);
        }
    });
    
    $("#save").click(function(){
    	setForm();
    	var viewName = $("#viewName").val();
    	var validations = new Array();
        
        validations.push($("#viewName")
    			.validateNull(viewName,i18nProp('message_warn_viewName_is_null')));
        
    	if("failed" == recordFailInfo(validations)){
    		return;
    	}else{
    		document.newView.submit();
    	}
    });
    
    $("#update").click(function(){
    	setForm();
    	document.newView.action = "customizedView/updateCustomizedView";
    	document.newView.submit();
    });
    
    $("#filterContent").delegate(".deleteLink","click",function(){
    	var object = this;
    	ShowMsg(i18nProp('message_confirm_customizedView_delete_filter'),function(yes){
		      if (yes) {
			    	var itemId = $(object).parents(".filterInfo").find("#itemId").val();
			    	var index = $(object).parents(".filterInfo").index();
			      	if("" == itemId){
			      		removeNewAddedItem(index);
			      		$(object).parents(".filterInfo").remove();
			      	}else{
			      		$(object).parents(".filterInfo").css("display", "none");
			      		$(object).parents(".filterInfo").find("#isDelete").val("yes");
			      		markTheItemAsDeleted(index);
			      	}
		      }else{
		          return;
		      }
		    });
    });
    
    $("#filterContent").delegate(".eidtLink", "click",function(){
    	var parent = $(this).parents(".filterInfo");
    	var index = parent.index()-1;
    	
    	markTheEditedRow(this);
    	saveAndHiddenEditInputBox();
    	
    	// get the value of the criteria and value in the filterInfo
    	var criteria = parent.find(".criteriaInColumn").children("p").text();
    	var value = parent.find(".valueInColumn").children("p").text();
    	
    	//clear the text of the criteria and value in the filterInfo
    	parent.find(".criteriaInColumn").children("p").text("");
    	parent.find(".valueInColumn").children("p").text("");
    	
    	//show the edit input
    	var editCriteriaInput;
    	var eidtValueInput;
    	if(parent.find(".criteriaInColumn .ms-ctn").length == 0){ //first time edit
    		editCriteriaInput = parent.find(".criteriaInColumn").find(".eidtCriteriaInput");
    		eidtValueInput = parent.find(".valueInColumn").find(".eidtValueInput");
    	}else{ // not first time edited
    		
    		editCriteriaInput = parent.find(".criteriaInColumn .ms-ctn");
    		eidtValueInput = parent.find(".valueInColumn .ms-ctn");
    	}
    	var eidtDateInput = parent.find(".valueInColumn").find(".editDatepic");
    	var columnType = parent.find(".typeInColumn").children("p").text();
    	
    	editCriteriaInput.show();
    	
    	if("date" == columnType){
    		eidtValueInput.hide();
    		eidtDateInput.show();
    		eidtDateInput.val(value);
    	}else{
    		eidtValueInput.show();
    		eidtDateInput.hide();
    	}
    	
    	//set edit criteria input to drop down list
    	var searchColumn = parent.find(".searchColumnInColumn").children("p").text();
    	var columnType = parent.find(".typeInColumn").children("p").text();
    	chooseCriteria(editCriteriaInput, columnType, searchColumn, criteria, index);
    	
    	//set edit value input to drop down list
    	var realName = parent.find(".searchColumnInColumn").text();
    	var realTable = parent.find(".realTableInColumn").text();
	    chooseValue(eidtValueInput, columnType, realName, realTable, searchColumn, value, index);
    });
    
    $(document).click(function(e){
    	var currentObject = $(e.target);
    	
    	//exclude the irrelevant elements
    	if(currentObject.parents(".filterInfo").length != 0){
    		if(currentObject.is('p') || currentObject.is('a')){
    			return;
    		}else if("message-div" == currentObject.attr("class")){
    			return;
    		}else if(undefined != currentObject.attr("class")){
    			var classes = [];
    			classes = currentObject.attr("class").split(" ");
    			var length = classes.length;
    			
    			for(var i = 0; i < length; i++){
    				if(classes[i].match("inText")){
    					return;
    				}
    			}
    		}else{
    			saveAndHiddenEditInputBox();
    		}
    	}else{
    		if("ms-res-item" == currentObject.attr("class") || "ms-close-btn" == currentObject.attr("class")){
    			return;
    		}else{
    			saveAndHiddenEditInputBox();
    		}
    	}
    });
    
    function saveAndHiddenEditInputBox(){
    	//check the edited row in the list
    	$("#filterContent").find(".filterInfo").each(function(index){
    		var eidtCriteriaInput = $(this).find(".criteriaInColumn .ms-ctn");
    		
        	if("inline-block" == eidtCriteriaInput.css("display") || "block" == eidtCriteriaInput.css("display")){
        		var eidtValueInput = $(this).find(".valueInColumn .ms-ctn");
        		var eidtDateInput = $(this).find(".editDatepic");
        		
        		var editCriteria = searchConditions[index];
        		var editValue;
        		
        		if("inline-block" == eidtDateInput.css("display") || "block" == eidtDateInput.css("display")){
        			editValue = eidtDateInput.val();
        		}else{
        			editValue = values[index];
        		}
        		$(this).find(".criteriaInColumn").children("p").text(editCriteria);
        		$(this).find(".valueInColumn").children("p").text(editValue);
        		
        		eidtDateInput.val("");
        		
        		eidtCriteriaInput.hide();
        		eidtValueInput.hide();
        		eidtDateInput.hide();
        		}
        	});
    }
    
    $("#cancel").click(function(){
    	window.history.back();
    });
    
    $(".radioBoxes").delegate(".radioCheckOff, .radioCheckOn", "click", function(){
    	if("radioCheckOff" == $(this).attr("class")){
    		$(this).parents(".radioBoxes").find(".radioCheckOn").attr("class","radioCheckOff");
    		$(this).attr("class","radioCheckOn");
    		var operator = $(this).attr("name");
    		$("#operator").val(operator);
    	}
    });
});

function initBindData(){
    var searchConditionMs = initDropDownList($("#searchCondition"), [], false, undefined, 225);
    $("#searchCondition").data("searchCondition",searchConditionMs);
    
    var valueMs = initDropDownList($("#value"), [], false, undefined, 225, true);
    $("#value").data("value",valueMs);
}

function markTheItemAsDeleted(index){
	isDeletes[index] = "yes";
}

function removeNewAddedItem(index){
	columns.splice(index, 1);
	searchConditions.splice(index, 1);
	values.splice(index, 1);
	customizedViewItemIds.splice(index, 1);
	isDeletes.splice(index, 1);
	columnTypes.splice(index, 1);
	searchColumns.splice(index, 1);
	realTables.splice(index, 1);
}

function updateTheFilter(){
	
	var searchCondition = $("#searchCondition").val();
	var value = $("#value").val();
	if("none" == $("#value").css("display")){
		value = $(".datepic").val();
	}
	$(".filterInfo").each(function(){
		var isEdit = $(this).find(".isEdit").val();
		if("yes" == isEdit){
			$(this).find(".criteriaInColumn p").text(searchCondition);
			$(this).find(".valueInColumn p").text(value);
		}
	});
}

function checkCriteriaAndValue(){
	
	var searchCondition = $("#searchCondition").val();
	var value = $("#value").val();
	
	if("none" == $("#value").css("display")){
		value = $(".datepic").val();
	}
	if("" != searchCondition && "" != value){
		return true;
	}else{
		return false;
	}
}

function markTheEditedRow(object){
	$("#filterContent .filterInfo").each(function(){
		$(this).find(".isEdit").val("no");
	});
	
	$(object).parents(".filterInfo").find(".isEdit").val("yes");
}

function doAppendFunction(columnName, searchCondition, columnType, searchColumn, realTable, value){
	columns.push(columnName);
	searchConditions.push(searchCondition);
	values.push(value);
	customizedViewItemIds.push("");
	isDeletes.push("no");
	columnTypes.push(columnType);
	searchColumns.push(searchColumn);
	realTables.push(realTable);
	
	var parent = $(".filterHead").parent();
	var filterInfoTemplate = $("#filterInfoTemplate"); 
	
	parent.append(filterInfoTemplate.html());
	var lastFilterInfo = parent.find(".filterInfo:last");
	
	lastFilterInfo.find(".nameInColumn").children("p").text(columnName);
	lastFilterInfo.find(".typeInColumn").children("p").text(columnType);
	lastFilterInfo.find(".searchColumnInColumn").children("p").text(searchColumn);
	lastFilterInfo.find(".realTableInColumn").children("p").text(realTable);
	lastFilterInfo.find(".criteriaInColumn").children("p").text(searchCondition);
	lastFilterInfo.find(".valueInColumn").children("p").text(value);
}

function setForm(){
	var viewName = $("#viewName").val();
	
	$("#formIsDeletes").val(isDeletes);
	$("#formViewName").val(viewName);
	$("#formValues").val(values);
	$("#formColumns").val(columns);
	$("#formColumnTypes").val(columnTypes);
	$("#searchColumns").val(searchColumns);
	$("#formRealTables").val(realTables);
	$("#formSearchConditions").val(searchConditions);
	$("#customizedViewItemIds").val(customizedViewItemIds);
}

function getValueFromList(){
	$("#filterContent .nameInColumn").each(function(index){
		columns[index] = $(this).text();
	});
	
	$("#filterContent .typeInColumn").each(function(index){
		columnTypes[index] = $(this).text();
	});
	
	$("#filterContent .searchColumnInColumn").each(function(index){
		searchColumns[index] = $(this).text();
	});
	
	$("#filterContent .realTableInColumn").each(function(index){
		realTables[index] = $(this).text();
	});
	
	$("#filterContent .criteriaInColumn").each(function(index){
		searchConditions[index] = $(this).children("p").text();
	});
	
	$("#filterContent .valueInColumn").each(function(index){
		values[index] = $(this).children("p").text();
	});
	
	$("#filterContent .itemIdInColumn").each(function(index){
		customizedViewItemIds[index] = $(this).find("#itemId").val();
	});
	
	$("#filterContent .itemIdInColumn").each(function(index){
		isDeletes[index] = $(this).find("#isDelete").val();
	});
}


function chooseColumn() {
	var categoryType = $("#categoryType").val();
	
	$.ajax( {  
		type: "POST",
		contentType : 'application/x-www-form-urlencoded',
		dataType : 'json',  
	    url : 'customizedColumn/getDefaultCustomizedColumn',
	    data: {
	    	categoryType:categoryType
	    },
	    success : function(data) {
	    	 
	    	objectColumn = data;
	    	length = data.length;
	    	
	    	for(var i = 0; i < length; i++) {
	    		newColumnNames[i] = objectColumn[i].enName;
	    		newColumnTypes[i] = objectColumn[i].columnType;
	    		newRealNames[i] = objectColumn[i].realName;
	    		newRealTables[i] = objectColumn[i].realTable;
	    		newSearchColumns[i] =  objectColumn[i].searchColumn;
	    	}
	    	var columnMs = initDropDownList($("#columnName"), newColumnNames, false, undefined, 225);
            $("#columnName").data("columnName",columnMs);
            
            columnBindEvent();
	    }
	});
}

function columnBindEvent(){
	var columnMs = $("#columnName").data("columnName");
	$(columnMs).on('selectionchange', function(event, combo, selection){
		var columnName = $("#columnName").data("columnName").getValue().join();
		
		if(doColumnValidation()){
			var index = getIndexInArr(newColumnNames, columnName);
			setHiddenValueOfColumn(index);
		}
    });
}

function doColumnValidation(){
	var columnName = $("#columnName").data("columnName").getValue().join();
	return validateValueIsEmpty($("#columnName"), columnName, i18nProp('message_warn_customer_is_null_or_not_exist'));
}

function setHiddenValueOfColumn(index){
	var columnType = newColumnTypes[index];
    $("#columnType").val(columnType);
    var realName = newRealNames[index];
    var searchColumn = newSearchColumns[index];
    $("#searchColumn").val(searchColumn);
    var realTable = newRealTables[index];
    $("#realTable").val(realTable);
    
    if("" != columnType) {
    	var searchCondition = "searchCondition";
    	var value = "value";
    	chooseCriteria(searchCondition, columnType, searchColumn);
    	chooseValue(value, columnType, realName,realTable, searchColumn);
    }
}

function chooseCriteria(inputID, type, searchColumn, currentValue, index) {
	//check column type
	var conditionsInfoMap = [];
	conditionsInfoMap.length = 0;
	
	if("boolean" == type){
		conditionsInfoMap = ["is"];
		setCriteriaDropDownList(inputID, conditionsInfoMap, currentValue, index);
		return;
	}
	
	if("status" == searchColumn || "type" == searchColumn){
		conditionsInfoMap = ["is", "is not"];
		setCriteriaDropDownList(inputID, conditionsInfoMap, currentValue, index);
		return;
	}
	
	$.ajax( {  
	    type : 'POST',  
	    contentType : 'application/json',  
	    url : 'customizedColumn/getSearchCondition?type=' + type,  
	    dataType : 'json',  
	    success : function(data) {
	    	setCriteriaDropDownList(inputID, data.searchCondition, currentValue, index);
	    }
	});
}

function setCriteriaDropDownList(inputID, searchCondition, currentValue, indexOfSearchCondition){
	objectSearchCondition = searchCondition;
	
	if(undefined == searchCondition){
		searchConditionInfoMap.length = 0;
	}else{
		searchConditionInfoMap.length = objectSearchCondition.length;
		for(var i = 0; i < objectSearchCondition.length; i++) {
			searchConditionInfoMap[i] = objectSearchCondition[i];
		}
	}
	if("searchCondition" == inputID){ //new
		$("#searchCondition").data("searchCondition").clear();
		$("#searchCondition").data("searchCondition").setData(searchConditionInfoMap);
	}else{  //update
		var criteriaMs = initDropDownList(inputID, searchConditionInfoMap, false, currentValue, 225);
		inputID.data("updatedSearchCondition",criteriaMs);
		
		var updatedSearchCondition = inputID.data("updatedSearchCondition");
		
		$(updatedSearchCondition).on('selectionchange', function(event, combo, selection){
			var updatedSearchConditionValue = inputID.data("updatedSearchCondition").getValue().join();
			searchConditions[indexOfSearchCondition] = updatedSearchConditionValue;
	    });
	}
}

function chooseValue(inputID, columnType, realName, realTable, searchColumn, currentValue, index) {
	// initiate the UI
	$("#value").show();
	$(".datepic").hide();
	valueInfoMap.length = 0;
	
	// check column type
	if("boolean" == columnType){
		valueInfoMap = ["true", "false"];
		setValueDropdownList(inputID, valueInfoMap, currentValue, index);
		return;
	}else if("date" == columnType){
		if("value" == inputID){  //add
			$("#value").hide();
			$("#datepic").show();
			
			//initiate the original class
			$(".value").find("input:last").attr("class", "inText datepic");
			
			//Bind datapicker event of jqueryUI:
			//Generate a unique id to bind the datepicker event,
			//otherwise the new generate element to bind the 
			//datepicker can't work well.
			var date = new Date().getTime();
			$(".value").find("input:last").attr("id", "datepic" + date);
			$("#datepic" + date).datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd"
			});
		}else{  //update
			$("#filterContent .filterInfo").each(function(){
				if("yes" == $(this).find(".isEdit").val()){
					var valueInColumn = $(this).find(".valueInColumn");
					
					valueInColumn.find("input:first").hide();
					valueInColumn.find("input:last").show();
					
					valueInColumn.find(".select-panel").remove();
					valueInColumn.find("input:last").attr("class", "inText editDatepic");
					
					var date = new Date().getTime();
					valueInColumn.find("input:last").attr("id", "datepic" + date);
					valueInColumn.find("#datepic" + date).datepicker({
						changeMonth : true,
						changeYear : true,
						dateFormat : "yy-mm-dd"
					});
				}
			});
		}
		return;
	}
	
	if("status" == searchColumn){
		valueInfoMap = ["AVAILABLE", "IDLE","IN_USE", "BORROWED", "RETURNED", "BROKEN", "WRITE_OFF"];
		setValueDropdownList(inputID, valueInfoMap, currentValue, index);
		return;
	}
	if("type" == searchColumn){
		valueInfoMap = ["DEVICE", "MACHINE", "MONITOR", "SOFTWARE", "OTHERASSETS"];
		setValueDropdownList(inputID, valueInfoMap, currentValue, index);
		return;
	}
	$.ajax( {  
	    type : 'POST',  
	    contentType : 'application/json',
	    url : 'customizedColumn/getValues?realName=' + realName + '&realTable=' + realTable,  
	    dataType : 'json',  
	    success : function(data) { 
	    	$("#value").removeClass("select-type");
			$("#value").addClass("valueInput");
			
			$("#filterContent .filterInfo").each(function(){
				if("yes" == $(this).find(".isEdit").val()){
					$(".eidtValueInput").removeClass("select-type");
					$(".eidtValueInput").addClass("eidtValueInput");
				}
			});
			setValueDropdownList(inputID, data.values.del(), currentValue, index);
	    }
	});
}

function setValueDropdownList(inputID, value, currentValue, indexOfValue){
	objectValue = value;
	length = value.length;
	if(0 == length){
		valueInfoMap.length = 0;
	}else{
		valueInfoMap.length = objectValue.length;
		for(var i = 0; i < objectValue.length; i++) {
			valueInfoMap[i] = String(objectValue[i]);
		}
	}
	
	if("value" == inputID){ //new
		$("#value").data("value").clear();
		$("#value").data("value").setData(valueInfoMap);
	}else{  //update
		var valueMs = initDropDownList(inputID, valueInfoMap, false, currentValue, 225, true);
		inputID.data("updatedValue",valueMs);
		
		var updatedValue = inputID.data("updatedValue");
		
		$(updatedValue).on('selectionchange', function(event, combo, selection){
			var newValue = inputID.data("updatedValue").getValue().join();
			values[indexOfValue] = newValue;
	    });
	}
}
	