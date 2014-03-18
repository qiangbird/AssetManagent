var employeeNames = new Array();
var employeeIds =  new Array();
var isDelete =  new Array();
var usersRoleInfo =  new Array();
var employeeInfo = new Array();
var createOperation = "create";
var updateOperation = "update";
var employees = new Array();

var dataList;
var criteria = {};

$(document).ready(function() {
	
	getEmployeeDataSource();
	
	// categoryFlag = 13, it means category is 'user role'
	initCriteria(13);
	findDataListInfo("user role");
	
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
    
    removePlaceholderForKeyWord();
	
	$(".roleAddContent").delegate(".roleCheckBoxOff, .roleCheckBoxOn", "click", function(){
		var attrId = "it";
		var itValueId = $(".roleAddContent").find("#itValue");
		var adminValueId = $(".roleAddContent").find("#adminValue");
		
		if("roleCheckBoxOff" == $(this).attr("class")){
			$(this).removeClass("roleCheckBoxOff");
			$(this).addClass("roleCheckBoxOn");
			setValueOfRole(this, "true", attrId, itValueId, adminValueId);
		}else{
			$(this).removeClass("roleCheckBoxOn");
			$(this).addClass("roleCheckBoxOff");
			setValueOfRole(this, "false", attrId, itValueId, adminValueId);
		}
	});
	
	$(".dataList").delegate(".roleCheckBoxInRowOff, .roleCheckBoxInRowOn, .deleteLink", "click", function(){
		var attrId = "itInRow";
		var parent = $(this).parents(".row");
		var itValueId = parent.find(".itInRow").find("#itInRowValue");
		var adminValueId = parent.find(".adminInRow").find("#adminInRowValue");
		var index = $(this).parents(".row").index();
		
		// update role
		if("roleCheckBoxInRowOff" == $(this).attr("class")){
			$(this).removeClass("roleCheckBoxInRowOff");
			$(this).addClass("roleCheckBoxInRowOn");
			setValueOfRole(this, "true", attrId, itValueId, adminValueId);
			showUnderLineByCheckIsNew(this);
		}else if("roleCheckBoxInRowOn" == $(this).attr("class")){
			$(this).removeClass("roleCheckBoxInRowOn");
			$(this).addClass("roleCheckBoxInRowOff");
			setValueOfRole(this, "false", attrId, itValueId, adminValueId);
			showUnderLineByCheckIsNew(this);
		}
		//delete role
		if("deleteLink" == $(this).attr("class")){
	    	ShowMsg(i18nProp('message_confirm_rolelist_delete_role'),function(yes){
			      if (yes) {
						var flag = false;
						flag = isAtLeastOneITAndAdmin();
						if(true == flag){
							parent.find(".isDelete").val("true");
							parent.css("display", "none");
						}else{
							showMessageBarForMessage('none_IT_and_Admin');
						}
			      }else{
			          return;
			      }
			});
		}
	});
	
	$("#addButton").click(function(){
		initAddProccess();
	});
	
	$("#saveButton").click(function(){
		var hasITAndAdmin = isAtLeastOneITAndAdmin();
		
		if(hasITAndAdmin){
			getUserRoloInfo();
			saveOperation(usersRoleInfo);
		}else{
			showMessageBarForMessage('none_IT_and_Admin');
		}
	});
	
	$("#cancelButton").click(function(){
		dataList.search();
	});
	$("#bodyMinHight").delegate("#token-input-employeeName","blur",function(){
		if("" != $(".employeeName").val()){
			$(".token-input-list-facebook").clearValidationMessage();
		}
	});
	
});

function getEmployeeDataSource(){
	var params = {
			elementId : "employeeName"
	};
	jQuery.ajax({
		contentType : 'application/json',  
		url: "user/getEmployeeDataSource",
		data: null,
		success: function(data){
			params.dataSource= data.employeeInfo;
			employeeInfo = data.employeeInfo;
			autoComplete(params);  
		},
		dataType: "json",
		type: "POST"
	});
}

function isAtLeastOneITAndAdmin(){
	var row = $(".dataList").find(".row");
	var ITNumber = row.find(".itInRow").find(".roleCheckBoxInRowOn").size();
	var AdminNumber = row.find(".adminInRow").find(".roleCheckBoxInRowOn").size();
	
	if(0 < ITNumber && 0 < AdminNumber){
		return true;
	}else{
		return false;
	}
	
}

function showUnderLineByCheckIsNew(object){
	var parent = $(object).parents(".row");
	var underLine = $(object).parent(".operateCheckbox").find(".underLine");
	var currentValue = $(object).parent(".operateCheckbox").find("input:first").val();
	var originalValue = $(object).parent(".operateCheckbox").find("input:last").val();
	
	if(currentValue != originalValue){
		underLine.show();
	}else{
		underLine.hide();
	}
}

function setValueOfRole(objec, value, attrId, itValueId, adminValueId,index){
	if(attrId == $(objec).attr("id")){
		itValueId.val(value);
	}else{
		adminValueId.val(value);
	}
}

function autoComplete(params) {
	var setting = {
		    minChars : 1,
		    width : 380,
		    height : 55,
		    dataSource : null,
		    placeholder : null
		};
	params = $.extend(setting, params);
	$("#" + params.elementId).autoComplete({
	    minChars : params.minChars,
	    width : params.width,
	    height : params.height,
	    placeholder : params.placeholder,
	    source :  params.dataSource
	});
}

function initAddProccess(){
	var employeeInput = $("#employeeName").val();
	var listEmployees = employeeInput.split(",");
	checkEmployees(listEmployees);
}

function checkEmployees(listEmployees){
	$("#div-loader").show();
	getUserRoloInfo();
	var itRole = $("#itValue").val();
	var systemAdminRole = $("#adminValue").val();
	var errorEmployeeNames = [];
	errorEmployeeNames.length = 0;
	
		for(var i=0;i<listEmployees.length;i++) {
			var employeeName = listEmployees[i].split("#")[0];
			var employeeId = listEmployees[i].split("#")[1];
			if(employeeName == "") {
				$("#div-loader").hide();
				$(".token-input-list-facebook").push($(".token-input-list-facebook")
						.validateNull(employeeName,i18nProp('message_warn_user_is_null')));
				return;
			}else if(employeeId == "" || employeeId == undefined) {  // employee not exist
				$("#autoText li").each(function() {
					var errorEmployeeName = $(this).find("p").text();
					if(employeeName == errorEmployeeName) {
						errorEmployeeNames.push(errorEmployeeName);
						$(this).css("background-color","#FFF58F");
						$(this).css("border", "1px solid red");
					}
				});
			}
			
			// employee already exist
			for(var j = 0; j< usersRoleInfo.length; j++) {
				// employee already exist and has already been delete
				if(employeeId == usersRoleInfo[j].employeeId && usersRoleInfo[j].isDelete == "false") {
					$("#autoText li").each(function() {
						var errorEmployeeName = $(this).find("p").text();
						if(employeeName == errorEmployeeName) {
							usersRoleInfo[j].isDelete = "true";
							
							$(".datalist").find(".row").each(function(index){
								if(employeeName == $(this).find(".employeeNameInRow").text()){
									$(this).hide();
								}
							});
						}
					});
				}
			}
			var employee = new Object();
			employee.employeeId = employeeId;
			employee.employeeName = employeeName;
			employee.itRole = itRole;
			employee.systemAdminRole = systemAdminRole;
			employee.isDelete = "false";
			employees.push(employee);
			}
		
		if(0 < errorEmployeeNames.length){
			var names = errorEmployeeNames.join(",");
			employees.length = 0;
			$("#div-loader").hide();
			showMessageBarForOperationResultMessage(i18nProp('message_warn_role_illegal', names));
			return;
		}else{
			if("false" == itRole && "false" == systemAdminRole) {
				$("#div-loader").hide();
				showMessageBarForMessage('message_warn_role_is_null');
				return;
			}
			for(var m = 0; m < employees.length; m++){
				usersRoleInfo.push(employees[m]);
			}
			employees.length = 0;
			saveOperation(usersRoleInfo);
		}
}

function saveOperation(usersRoleInfo){
	$.ajax({
		contentType : 'application/x-www-form-urlencoded',  
		url: 'user/saveUserRole',
		data: { 
			"usersRoleInfo":JSON.stringify(usersRoleInfo)
		},
		success: function(){
			$("#div-loader").hide();
			dataList.search();
			showMessageBarForMessage("role_save_success");
		},
//		dataType: 'json',
		type: 'POST'
	});
}

function getUserRoloInfo(){
	usersRoleInfo.length = 0;
	$(".dataList-div-body").find(".row").each(function(index){
		var employee = new Object();
		employee.employeeId = $(this).find(".Employee-ID").text();
		employee.employeeName = $(this).find(".Employee-Name").text();
		employee.itRole = $(this).find(".IT .itInRow #itInRowValue").val();
		employee.systemAdminRole = $(this).find(".System-Admin .adminInRow #adminInRowValue").val();
		employee.isDelete = $(this).find(".isDelete").val();
		usersRoleInfo.push(employee);
	});
}

var dataListInfo = {
	    columns : [],
	    criteria : criteria,
	    minHeight : 150,
	    pageSizes : [10, 20, 30, 50],
	    hasCheckbox : false,
	    pageItemSize : 5,
	    url : 'user/findUserRoleList',
	    updateShowField : {
	        url : 'searchCommon/column/updateColumns',
	        callback : function(data) {
	            $.ajax({
	                type : "POST",
	                contentType : "application/json",
	                url : "searchCommon/column/getColumns?category=user role",
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

	// call dataList
	function searchList() {
	    dataList = $(".dataList").DataList(dataListInfo);
	    dataList.criteria = setCriteria();
	    dataList.search();
	}


function setCriteria() {
	
	criteria.keyWord = $("#keyword").val();
	
	var isITRole = false;
	var isSystemAdminRole = false;
	
	$("#userRole").find(":checked").each(function() {
		if ("it" == $(this).val()) {
			isITRole = true;
		}
		
		if ("system_admin" == $(this).val()) {
			isSystemAdminRole = true;
		}
	});
    criteria.isITRole = isITRole;
    criteria.isSystemAdminRole = isSystemAdminRole;
    
    return criteria;
}