var simpleCreateInfoMap = [];
var searchConditionInfoMap = [];
var valueInfoMap = [];
var autocomplateValueInfoMap = [];
var columnTypes = [];
var realNames = [];
var realTables = [];
var searchColumns = [];
var isEditting = false;

$(document).ready(function() {
    chooseColumn();
    
    $("#columnName, #viewName").blur(function(){
		//delete the tips when input data exists
		if("" != $(this).val()){
			$(this).clearValidationMessage();
		}
	});
    
    $("#addToFilter").click(function(){
    	var validations = new Array();
        
    	var columnName = $("#columnName").val();
    	var searchCondition = $("#searchCondition").val();
    	
        validations.push($("#columnName")
    			.validateNull(columnName,i18nProp('message_warn_column_is_null')));
        validations.push($("#searchCondition")
        		.validateNull(searchCondition,i18nProp('message_warn_criteria_is_null')));
        
        if("failed" == recordFailInfo(validations)){
        	return;
        }else{
        	var value = $("#value").val();
        	var columnType = $("#columnType").val();
        	var searchColumn = $("#searchColumn").val();
        	var realTable = $("#realTable").val();
        	
        	if("none" == $("#value").css("display")){
        		value = $(".datepic").val();
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
			      	if("" == itemId){
			      		$(object).parents(".filterInfo").remove();
			      	}else{
			      		$(object).parents(".filterInfo").css("display", "none");
			      		$(object).parents(".filterInfo").find("#isDelete").val("yes");
			      	}
		      }else{
		          return;
		      }
		    });
    });
    
    $("#filterContent").delegate(".eidtLink", "click",function(){
    	markTheEditedRow(this);
    	var parent = $(this).parents(".filterInfo");
    	saveAndHiddenEditInputBox();
    	
    	// get the value of the criteria and value in the filterInfo
    	var criteria = parent.find(".criteriaInColumn").children("p").text();
    	var value = parent.find(".valueInColumn").children("p").text();
    	
    	//clear the text of the criteria and value in the filterInfo
    	parent.find(".criteriaInColumn").children("p").text("");
    	parent.find(".valueInColumn").children("p").text("");
    	
    	//show the edit input
    	var editCriteriaInput = parent.find(".criteriaInColumn").find(".eidtCriteriaInput");
    	var eidtValueInput = parent.find(".valueInColumn").find(".eidtValueInput");
    	var eidtDateInput = parent.find(".valueInColumn").find(".editDatepic");
    	var columnType = parent.find(".typeInColumn").children("p").text();
    	
    	editCriteriaInput.show();
    	editCriteriaInput.val(criteria);
    	
    	if("date" == columnType){
    		eidtValueInput.hide();
    		eidtDateInput.show();
    		eidtDateInput.val(value);
    	}else{
    		eidtValueInput.show();
    		eidtDateInput.hide();
    		eidtValueInput.val(value);
    	}
    	
    	//set edit criteria input to drop down list
    	var searchColumn = parent.find(".searchColumnInColumn").children("p").text();
    	var columnType = parent.find(".typeInColumn").children("p").text();
    	chooseCriteria(editCriteriaInput, columnType, searchColumn);
    	
    	//set edit value input to drop down list
    	var realName = parent.find(".searchColumnInColumn").text();
    	var realTable = parent.find(".realTableInColumn").text();
	    chooseValue(eidtValueInput, columnType, realName, realTable, searchColumn);
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
    		saveAndHiddenEditInputBox();
    	}
    });
    
    function saveAndHiddenEditInputBox(){
    	//check the edited row in the list
    	$("#filterContent").find(".filterInfo").each(function(){
    		var eidtCriteriaInput = $(this).find(".eidtCriteriaInput");
    		
        	if("inline-block" == eidtCriteriaInput.css("display")){
        		var eidtValueInput = $(this).find(".eidtValueInput");
        		var eidtDateInput = $(this).find(".editDatepic");
        		
        		var editCriteria = eidtCriteriaInput.val();
        		var editValue;
        		
        		if("inline-block" == eidtDateInput.css("display")){
        			editValue = eidtDateInput.val();
        		}else{
        			editValue = eidtValueInput.val();
        		}
        		$(this).find(".criteriaInColumn").children("p").text(editCriteria);
        		$(this).find(".valueInColumn").children("p").text(editValue);
        		
        		eidtCriteriaInput.val("");
        		eidtValueInput.val("");
        		eidtDateInput.val("");
        		
        		eidtCriteriaInput.hide();
        		eidtValueInput.hide();
        		eidtDateInput.hide();
        		
        		eidtCriteriaInput.find("input").attr("class", "inText select-type eidtCriteriaInput");
        		eidtValueInput.find("input:first").attr("class", "inText eidtValueInput");
        		eidtValueInput.find("input:last").attr("class", "inText editDatepic");
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
	var columns = [];
	var searchConditions = [];
	var values = [];
	var customizedViewItemIds = [];
	var isDeletes = [];
	var columnTypes = [];
	var searchColumns = [];
	var realTables = [];
	
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
	    		simpleCreateInfoMap[i] = objectColumn[i].enName;
	    		columnTypes[i] = objectColumn[i].columnType;
	    		realNames[i] = objectColumn[i].realName;
	    		realTables[i] = objectColumn[i].realTable;
	    		searchColumns[i] =  objectColumn[i].searchColumn;
	    	}
	    
		    var input = $("#columnName");
		    var p = input.parent("div");
		    p.css("z-index", "10");
		    var list = simpleCreateInfoMap;
		    input.val("");
		 
		    var panel = $("<div class='select-panel'>").appendTo(p);
		    var messDiv = $("<div class='message-div'></div>").appendTo(panel);
		    var shdowDiv = initShdowDiv(panel, list , 10);
		    closeOrOpenDiv(panel);
		    
		    var lis = panel.find("li");
		    lis.each(function(index) {
		        $(this).click(function() {
		            $(".type-details").css("display", "none");
		            var value = $(this).text();
		            if("" != value){
		            	$("#columnName").clearValidationMessage();
		            } 
		            setHiddenValueOfColumn(index);
		            input.val(value);
		        });
		    });
	    }
	});
}

$(".ui-autocomplete").find(".ui-menu-item").each(function(){
		  var uiCornerAll = $(this).find(".ui-corner-all");
		  var length;
		  if (uiCornerAll.html().charCodeAt(0) > 255) {
			  length = 10;
		  } else {
			  length = 20;
		  }
		  if (uiCornerAll.html().length > length) {
			  uiCornerAll.poshytip({
				  className: 'tip-green',
				  allowTipHover: true,
				  content: uiCornerAll.html()
			  });
		  }
 });

$(".shadow-div").find("ul li").each(function(){
	  var p = $(this).find("p");
	  var length;
	  if (p.html().charCodeAt(0) > 255) {
		  length = 10;
	  } else {
		  length = 20;
	  }
	  if (p.html().length > length) {
		  p.poshytip({
			  className: 'tip-green',
			  allowTipHover: true,
			  content: p.html()
		  });
	  }
});

function setHiddenValueOfColumn(index){
	var columnType = columnTypes[index];
    $("#columnType").val(columnType);
    var realName = realNames[index];
    var searchColumn = searchColumns[index];
    $("#searchColumn").val(searchColumn);
    var realTable = realTables[index];
    $("#realTable").val(realTable);
    
    if("" != columnType) {
    	var searchCondition = $("#searchCondition");
    	var value = $("#value");
    	chooseCriteria(searchCondition, columnType, searchColumn);
    	chooseValue(value, columnType, realName,realTable, searchColumn);
    }
}

function getIndexInArr(Arr, ele) {
    for ( var i = 0; i < Arr.length; i++) {
        if (ele == Arr[i]) {
            return i;
        }
    }
    return -1;
}

$("#columnName").click(function() {
    $("#columnName").autocomplete({
         source : simpleCreateInfoMap,
         select : function(e,ui) {
        	 var index = getIndexInArr(simpleCreateInfoMap,ui.item.value);
        	 setHiddenValueOfColumn(index);
         }
   });
});

function chooseCriteria(inputID, type, searchColumn) {
	//check column type
	if("boolean" == type){
		$("#searchCondition").val("is");
		$(".searchCondition").attr("readonly", "readonly");
		$(".searchCondition").find(".select-panel").remove();
		return;
	}
	
	if("status" == searchColumn || "type" == searchColumn){
		var conditionsInfoMap = ["is", "is not"];
		setCriteriaDropDownList(inputID, conditionsInfoMap);
		return;
	}
	
	$.ajax( {  
	    type : 'POST',  
	    contentType : 'application/json',  
	    url : 'customizedColumn/getSearchCondition?type=' + type,  
	    dataType : 'json',  
	    success : function(data) {
	    	setCriteriaDropDownList(inputID, data.searchCondition);
	    }
	});
}

function setCriteriaDropDownList(inputID, searchCondition){
	 
	objectSearchCondition = searchCondition;
	
	if(undefined == searchCondition){
		searchConditionInfoMap.length = 0;
	}else{
		searchConditionInfoMap.length = objectSearchCondition.length;
		for(var i = 0; i < objectSearchCondition.length; i++) {
			searchConditionInfoMap[i] = objectSearchCondition[i];
		}
	}
	var input = inputID;
    var p = input.parent("div");
    p.css("z-index", "10");
    var list = searchConditionInfoMap;
    var panel = $("<div class='select-panel'>").appendTo(p);
    var messDiv = $("<div class='message-div'></div>").appendTo(panel);
    var shdowDiv = initShdowDiv(panel, list , 10);
    closeOrOpenDiv(panel);
    
    var lis = panel.find("li");
    lis.each(function() {
        $(this).click(function() {
            $(".type-details").css("display", "none");
            var value = $(this).text();
            if("" != value){
            	$("#searchCondition").clearValidationMessage();
            } 
            input.val(value);
        });
    });

}

function chooseValue(inputID, columnType, realName, realTable, searchColumn) {
	// initiate the UI
	$("#value").show();
	$(".datepic").hide();
	valueInfoMap.length = 0;
	
	if("value" == inputID.attr("id")){
		$("#value").val("");
	}
	
	// check column type
	if("boolean" == columnType){
		if("value" == inputID.attr("id")){
			$("#value").removeClass("valueInput");
			$("#value").addClass("select-type");
		}else{
			$("#filterContent .filterInfo").each(function(){
				if("yes" == $(this).find(".isEdit").val()){
					$(this).find(".eidtValueInput").addClass("select-type");
				}
			});
		}
		valueInfoMap = ["true", "false"];
		setValueDropdownList(inputID, valueInfoMap);
		return;
	}else if("date" == columnType){
		if("value" == inputID.attr("id")){
			$("#value").hide();
			$("#datepic").show();
			$(".value").find(".select-panel").remove();
			
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
		}else{
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
		if("value" == inputID.attr("id")){
			$("#value").removeClass("valueInput");
			$("#value").addClass("select-type");
		}else{
			$("#filterContent .filterInfo").each(function(){
				if("yes" == $(this).find(".isEdit").val()){
					$(this).find(".eidtValueInput").addClass("select-type");
				}
			});
		}
		valueInfoMap = ["AVAILABLE", "IDLE","IN_USE", "BORROWED", "RETURNED", "BROKEN", "WRITE_OFF"];
		setValueDropdownList(inputID, valueInfoMap);
		return;
	}
	if("type" == searchColumn){
		if("value" == inputID.attr("id")){
			$("#value").removeClass("valueInput");
			$("#value").addClass("select-type");
		}else{
			$(".eidtValueInput").addClass("select-type");
		}
		valueInfoMap = ["DEVICE", "MACHINE", "MONITOR", "SOFTWARE", "OTHERASSETS"];
		setValueDropdownList(inputID, valueInfoMap);
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
	    	autocomplateValueInfoMap = data.values;
	    }
	});
}

$("#value").click(function() {
	
	if(undefined != autocomplateValueInfoMap && 0 < autocomplateValueInfoMap.length){
		$("#value").autocomplete({
			source : autocomplateValueInfoMap,
			select : function(e,ui) {
			}
		});
	}
});

$("#filterContent").delegate(".eidtValueInput","click", function() {
	if(0 < autocomplateValueInfoMap.length){
		$(".eidtValueInput").autocomplete({
			source : autocomplateValueInfoMap,
			select : function(e,ui) {
			}
		});
	}
});

function setValueDropdownList(inputID, values){
	autocomplateValueInfoMap.length = 0;
	objectValue = values;
	length = values.length;
	if(0 == length){
		valueInfoMap.length = 0;
	}else{
		valueInfoMap.length = objectValue.length;
		for(var i = 0; i < objectValue.length; i++) {
			valueInfoMap[i] = String(objectValue[i]);
		}
	}

	var input = inputID;
	var p = input.parent("div");
	p.css("z-index", "10");
	var list = valueInfoMap;
	var panel = $("<div class='select-panel'>").appendTo(p);
	var messDiv = $("<div class='message-div'></div>").appendTo(panel);
	var shdowDiv = initShdowDiv(panel, list , 10);
	closeOrOpenDiv(panel);
	
	var lis = panel.find("li");
	lis.each(function() {
	    $(this).click(function() {
	        $(".type-details").css("display", "none");
	        var value = $(this).text();
	        input.val(value);
	        $("#value").addClass("select-type");
	    });
	});
}

//dropdown list plugin.
function initShdowDiv(obj, list, z) {
    if(list.length == 0) {
        list = [""];
    }
    var len = list.length < 8 ? list.length : 8;
    var divHeight = len*26;
    
    var inputWidth = 150; 
    
    var width = inputWidth + 4;
    var height = divHeight + 15;
    
    var cWidth = inputWidth - 10;
    var cHeight = height - 15;
    
    var d =$("<div class='shadow-div'>").css("width", "150")
               .css("height", height)
               .css("position", "relative")
               .css("top", "0")
               .css("margin-left","-130px")
               .css("display", "none")
               .css("z-index", z)
               .appendTo(obj); 
     var t_l = $("<div>").css("width", "7px")
                         .css("height", "5px")          
                         .css("background", "url('img/BKG_Dropdown_T_L_7x5.png') no-repeat")
                         .css("position", "absolute")
                         .css("top", "0")
                         .css("left", "0")
                         .appendTo(d);          
     var t_c = $("<div>").css("width", cWidth)
                         .css("height", "5px") 
                         .css("position", "absolute")         
                         .css("top", "0")
                         .css("left", "7px")
                         .css("background", "url('img/BKG_Dropdown_T_C_1x5.png') repeat-x")
                         .appendTo(d);          
     var t_r = $("<div>").css("width", "7px")
                         .css("height", "5px")          
                         .css("background", "url('img/BKG_Dropdown_T_R_7x5.png') no-repeat")
                         .css("position", "absolute")
                         .css("top", "0")
                         .css("right", "0")
                         .appendTo(d);          
     var c_l = $("<div>").css("width", "7px")
                         .css("height", cHeight)          
                         .css("background", "url('img/BKG_Dropdown_C_L_7x1.png') repeat-y")
                         .css("position", "absolute")
                         .css("top", "5px")
                         .css("left", "0")
                         .appendTo(d);          
     var c_c = $("<div>").css("width", cWidth)
                         .css("height", cHeight)
                         .css("z-index", "5")
                         .css("position", "absolute")
                         .css("top", "5px")
                         .css("left", "7px")
                         .css("overflow-y", "auto")
                         .css("overflow-x", "hidden")
                         .css("background-color", "#FFFFFF")
                         .appendTo(d);
     var c_r = $("<div>").css("width", "7px")
                         .css("height", cHeight)          
                         .css("background", "url('img/BKG_Dropdown_C_R_7x1.png') repeat-y")
                         .css("position", "absolute")
                         .css("top", "5px")
                         .css("right", "0")
                         .appendTo(d);          
     var b_l = $("<div>").css("width", "7px")
                         .css("height", "10px")          
                         .css("background", "url('img/BKG_Dropdown_B_L_7x10.png') no-repeat")
                         .css("position", "absolute")
                         .css("bottom", "0")
                         .css("left", "0")
                         .appendTo(d);          
     var b_c = $("<div>").css("width", cWidth)
                         .css("height", "10px")          
                         .css("background", "url('img/BKG_Dropdown_B_C_1x10.png') repeat-x")
                         .css("position", "absolute")
                         .css("bottom", "0")
                         .css("left", "7px")
                         .appendTo(d);          
     var b_r = $("<div>").css("width", "7px")
                         .css("height", "10px")          
                         .css("background", "url('img/BKG_Dropdown_B_R_7x10.png') no-repeat")
                         .css("position", "absolute")
                         .css("bottom", "0")
                         .css("right", "0")
                         .appendTo(d);
     var ul = $("<ul>").css("width", cWidth)
                       .css("height", cHeight)
                       .css("margin", "0")
                       .css("list-style", "none")
                       .appendTo(c_c);
     
    for(index in list) {
    	var flag=list[index].match("\\+\\+\\+\\+\\+\\+\\+\\+\\+\\+");
    	if(flag === null){
    		$("<li><p>" + list[index] + "</p></li>").appendTo(ul);
    	}else{
    			$("<li id='"+list[index].split("++++++++++")[0]+"' title='Manager:"+list[index].split("++++++++++")[2]+"'><p>" + list[index].split("++++++++++")[1] + "</p></li>").appendTo(ul);
    		}
    	}
    return d;
    }

function closeOrOpenDiv(panel) {
    var messDiv = panel.find(".message-div");
    var showDiv = panel.find(".shadow-div");
    
    messDiv.click(function() {
        var display = showDiv.css("display");
        if(display == "none") {
           showDiv.addClass("active");
           showDiv.css("display", "block");
        }else{
        	showDiv.removeClass("active");
            showDiv.css("display", "none");
        }
        return false;
    });
    
    $(document).click(function() {
        $(".active").css("display", "none");
        $(".active").removeClass("active");
    });
}  

function inputBatchCreate(obj) {
    obj.keyup(function() {
        var v = $(this).val();
        if(v != "" && numberCheck(v)) {
            $(this).siblings(".image-span").css("background", "url('img/ICN_Correct_Active_25x20.png') no-repeat");
        } else if(v == "") {
            $(this).siblings(".image-span").css("background", "url('img/ICN_Warning_25x20.png') no-repeat");
        } else {
            $(this).siblings(".image-span").css("background", "url('img/ICN_Error_25x20.png') no-repeat");
        }
    });
}

	