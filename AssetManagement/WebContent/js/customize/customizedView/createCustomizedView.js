var simpleCreateInfoMap = [];
var searchConditionInfoMap = [];
var valueInfoMap = [];
var columnTypes = [];
var realNames = [];
var realTables = [];
var searchColumns = [];

$(document).ready(function() {
    chooseColumn();
    
    $("#addToFilter").click(function(){
    	var columnName = $("#columnName").val();
    	var searchCondition = $("#searchCondition").val();
    	var value = $("#value").val();
    	var columnType = $("#columnType").val();
    	var searchColumn = $("#searchColumn").val();
    	var realTable = $("#realTable").val();
    	
    	if("none" == $("#value").css("display")){
    		value = $(".datepic").val();
    	}
    	if("" != columnName && "" != searchCondition && "" != value){
    		doAppendFunction(columnName, searchCondition, columnType, searchColumn, realTable, value);
    	}
    });
    
   $("#viewName").bind('input propertychange', function() {
    	var viewName = $("#viewName").val();
    	if("" != viewName.trim()){
    		$(".error-box-viewName").removeClass("error-box-dipaly");
    		$("#viewNameImg").removeClass("error-image-span");
    	}else {
    		$(".error-box-viewName").addClass("error-box-dipaly");
    		$("#viewNameImg").addClass("error-image-span");
    	}
   });
   
    $("#save").click(function(){
    	setForm();
    	var viewName = $("#viewName").val();
    	
    	if("" == viewName.trim()){
    		$(".error-box-viewName").addClass("error-box-dipaly");
    		$("#viewNameImg").addClass("error-image-span");
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
    	
    	//change the button to 'Update'
    	$("#addToFilter").hide();
    	$("#updateToFilter").show();
    	
    	addNoticeBound();
    	
    	var parent = $(this).parents(".filterInfo");
    	var columnType = parent.find(".typeInColumn").text();
    	var realName = parent.find(".searchColumnInColumn").text();
    	var realTable = parent.find(".realTableInColumn").text();
    	
    	$(".customizedViewItem").find(".select-panel").remove();
    	$(".customizedViewItem").find("#columnName").attr("readonly", "readonly");
    	$("#columnName").val(parent.find(".nameInColumn").text());
    	
    	var searchCondition = parent.find(".criteriaInColumn").text();
    	var value = parent.find(".valueInColumn").text();
    	
	    chooseCriteria(columnType, searchCondition),
	    chooseValue(columnType, realName, realTable, value);
    });
    
    $(".addToFilter").delegate("#updateToFilter", "click",function(){
    	var flag = false;
    	flag = checkCriteriaAndValue();
    	
    	if(true == flag){
    		updateTheFilter();
    		
    		//change the button to 'Add to Filter'
        	$("#addToFilter").show();
        	$("#updateToFilter").hide();
        	
        	//reset the columnName, criteria and value input box
        	$("#columnName").val("");
        	$("#searchCondition").val("");
        	$("#value").val("");
        	$(".datepic").val("");
        	
        	$(".customizedViewItem").find(".select-panel").remove();
        	$(".searchCondition").find(".select-panel").remove();
        	$(".value").find(".select-panel").remove();
        	
        	chooseColumn();
    	}
    });
    
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

function addNoticeBound(){
	$("#columnName").addClass("notice-bound");
	$("#searchCondition").addClass("notice-bound");
	$("#value").addClass("notice-bound");
	$("#datepic").addClass("notice-bound");
	setTimeout("removeNoticeBound()", 5000);
}

function removeNoticeBound(){
	$("#columnName").removeClass("notice-bound");
	$("#searchCondition").removeClass("notice-bound");
	$("#value").removeClass("notice-bound");
	$("#datepic").removeClass("notice-bound");
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
	$(".filterInfo").each(function(){
		$(this).find(".isEdit").val("no");
	});
	
	$(object).parents(".filterInfo").find(".isEdit").val("yes");
}

function doAppendFunction(columnName, searchCondition, columnType, searchColumn, realTable, value){
	var p = $(".filterHead").parent();
	var row = $("<div class='filterInfo'></div>").appendTo(p);
	var itemIdInColumn = $("<div class='columnData sequence itemIdInColumn'> " +
			"<p><input type='hidden' id='itemId' value='' />" +
			"<input type='hidden' id='isDelete' class='isDelete' value='no' />" +
			"<input type='hidden' class='isEdit' value='no' /></p></div>").appendTo(row);
	var nameInColumn = $("<div class='columnData columnNameTitle nameInColumn'> " +
			"<p>" + columnName + "</p></div>").appendTo(row);
	var typeInColumn = $("<div class='columnData columnNameTitle typeInColumn'>" +
			"<p>" + columnType + "</p></div>").appendTo(row);
	var searchColumnInColumn = $("<div class='columnData columnNameTitle searchColumnInColumn'>" +
			"<p>" + searchColumn + "</p></div>").appendTo(row);
	var realTableInColumn = $("<div class='columnData columnNameTitle realTableInColumn'>" +
			"<p>" + realTable + "</p></div>").appendTo(row);
	var criteriaInColumn = $("<div class='columnData criteriaTitle criteriaInColumn'> " +
			"<p>" + searchCondition + "</p></div>").appendTo(row);
	var valueInColumn = $("<div class='columnData valueTitle valueInColumn'> " +
			"<p>" + value + "</p></div>").appendTo(row);
	var deleteButton = $("<div class='columnData deleteButton'>" +
	"<p class='linkText'><a class='deleteLink'></a></p></div>").appendTo(row);
	var editButton = $("<div class='columnData editButton'><p class='linkText'>" +
	"<a class='eidtLink'></a></p></div>").appendTo(row);
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
	
	$(".nameInColumn").each(function(index){
		columns[index] = $(this).text();
	});
	
	$(".typeInColumn").each(function(index){
		columnTypes[index] = $(this).text();
	});
	
	$(".searchColumnInColumn").each(function(index){
		searchColumns[index] = $(this).text();
	});
	
	$(".realTableInColumn").each(function(index){
		realTables[index] = $(this).text();
	});
	
	$(".criteriaInColumn").each(function(index){
		searchConditions[index] = $(this).text();
	});
	
	$(".valueInColumn").each(function(index){
		values[index] = $(this).text();
	});
	
	$(".itemIdInColumn").each(function(index){
		customizedViewItemIds[index] = $(this).find("#itemId").val();
	});
	
	$(".itemIdInColumn").each(function(index){
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
	
	$.ajax( {  
	    type : 'GET',  
	    contentType : 'application/json',  
	    url : 'customizedColumn/getDefaultCustomizedColumn',  
	    dataType : 'json',  
	    success : function(data) { 
	    	objectColumn = data.customizedColumns;
	    	length = data.customizedColumns.length;
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
		    var messDiv = $("<div class='message-div'>" + "Select column" + "</div>").appendTo(panel);
		    var shdowDiv = initShdowDiv(panel, list , 10);
		    closeOrOpenDiv(panel);
		    
		    var lis = panel.find("li");
		    lis.each(function(index) {
		        $(this).click(function() {
		            $(".type-details").css("display", "none");
		            var value = $(this).text();
		            var columnType = columnTypes[index];
		            $("#columnType").val(columnType);
		            var realName = realNames[index];
		            var searchColumn = searchColumns[index];
		            $("#searchColumn").val(searchColumn);
		            var realTable = realTables[index];
		            $("#realTable").val(realTable);
		            input.val(value);
		            $("#columnName").addClass("select-type");
		            
		            if("" != columnType) {
		            	chooseCriteria(columnType, null);
		            	chooseValue(columnType, realName,realTable, null);
		            }
		        });
		    });
	    }
	});
}

function chooseCriteria(type, searchConditon) {
	// initiate the UI
	$(".searchCondition").show();
	$("#value").show();
	
	//check column type
	if("boolean" == type){
		$("#searchCondition").val("is");
		$(".searchCondition").attr("readonly", "readonly");
		$(".searchCondition").find(".select-panel").remove();
		return;
	}
	
	$.ajax( {  
	    type : 'POST',  
	    contentType : 'application/json',  
	    url : 'customizedColumn/getSearchCondition?type=' + type,  
	    dataType : 'json',  
	    success : function(data) { 
	    	objectSearchCondition = data.searchCondition;
	    	length = data.searchCondition.length;
	    	if(0 == length){
	    		searchConditionInfoMap = [];
	    	}
	    	for(var i = 0; i < length; i++) {
	    		searchConditionInfoMap[i] = objectSearchCondition[i];
	    	}
	    
		    var input = $("#searchCondition");
		    var p = input.parent("div");
		    p.css("z-index", "10");
		    var list = searchConditionInfoMap;
		    var panel = $("<div class='select-panel'>").appendTo(p);
		    
		    // if the searchConditon is not null, poformance the eidt operation
		    if(null != searchConditon){
		    	input.val(searchConditon);
		    	var messDiv = $("<div class='message-div'></div>").appendTo(panel);
		    }else{
		    	input.val("");
		    	var messDiv = $("<div class='message-div'>" + "Select criteria" + "</div>").appendTo(panel);
		    }
		    var shdowDiv = initShdowDiv(panel, list , 10);
		    closeOrOpenDiv(panel);
		    
		    var lis = panel.find("li");
		    lis.each(function() {
		        $(this).click(function() {
		            $(".type-details").css("display", "none");
		            var value = $(this).text();
		            input.val(value);
		            $("#searchCondition").addClass("select-type");
		        });
		    });
	    }
	});
}

function chooseValue(columnType, realName, realTable, value) {
	$.ajax( {  
	    type : 'POST',  
	    contentType : 'application/json',  
	    url : 'customizedColumn/getValues?realName=' + realName + '&realTable=' + realTable,  
	    dataType : 'json',  
	    success : function(data) { 
	    	// initiate the UI
	    	$("#value").show();
    		$(".datepic").hide();
    		
    		// check column type
	    	if("boolean" == columnType){
	    		valueInfoMap = ["true", "false"];
	    	}else if("date" == columnType){
	    		$("#value").hide();
	    		$(".datepic").show();
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
	    		return;
	    	}else{
	    		objectValue = data.values;
	    		length = data.values.length;
	    		if(0 == length){
	    			valueInfoMap = [];
	    		}
	    		for(var i = 0; i < length; i++) {
	    			valueInfoMap[i] = String(objectValue[i]);
	    		}
	    	}
	    
		    var input = $("#value");
		    var p = input.parent("div");
		    p.css("z-index", "10");
		    var list = valueInfoMap;
		    var panel = $("<div class='select-panel'>").appendTo(p);
		    
		    // if the value is not null, poformance the eidt operation
		    if(null != value){
		    	input.val(value);
		    	var messDiv = $("<div class='message-div'></div>").appendTo(panel);
		    }else{
		    	input.val("");
		    	var messDiv = $("<div class='message-div'>" + "Select value" + "</div>").appendTo(panel);
		    }
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
	});
}

//dropdown list plugin.
function initShdowDiv(obj, list, z) {
    if(list.length == 0) {
        list = [""];
    }
    var len = list.length < 8 ? list.length : 8;
    var divHeight = len*26;
    
    var inputWidth = obj.width(); 
    
    var width = inputWidth + 4;
    var height = divHeight + 15;
    
    var cWidth = inputWidth - 10;
    var cHeight = height - 15;
    
    var d =$("<div class='shadow-div'>").css("width", width)
               .css("height", height)
               .css("position", "relative")
               .css("top", "0")
               .css("left", "3px")
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
        $(".active").css("display", "none");
        $(".active").removeClass("active");
        $(this).text("");
        var display = showDiv.css("display");
        if(display == "none") {
           showDiv.addClass("active");
           showDiv.css("display", "block");
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

	