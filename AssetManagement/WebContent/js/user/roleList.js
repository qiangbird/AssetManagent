var employeeNames = new Array();
var employeeIds =  new Array();
var isDelete =  new Array();
var usersRoleInfo =  new Array();
var employeeInfo = new Array();
var createOperation = "create";
var updateOperation = "update";
var employees = new Array();
$(document).ready(function() {
	getUserRoloInfo();
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
	$(".rowHead").find(".columnElement:last").css("background","#71B3D6");
	
	$(".roleAddContent").delegate(".roleCheckBoxOff, .roleCheckBoxOn", "click", function(){
		var attrId = "it";
		var itValueId = $(".roleAddContent").find("#itValue");
		var adminValueId = $(".roleAddContent").find("#adminValue");
		
		if("roleCheckBoxOff" == $(this).attr("class")){
			$(this).removeClass("roleCheckBoxOff");
			$(this).addClass("roleCheckBoxOn");
			setValueOfRole(this, "true", attrId, itValueId, adminValueId, null);
		}else{
			$(this).removeClass("roleCheckBoxOn");
			$(this).addClass("roleCheckBoxOff");
			setValueOfRole(this, "false", attrId, itValueId, adminValueId, null);
		}
	});
	
	$(".roleListContent").delegate(".roleCheckBoxInRowOff, .roleCheckBoxInRowOn, .deleteLink", "click", function(){
		var attrId = "itInRow";
		var parent = $(this).parents(".employeeRoleInfo");
		var itValueId = parent.find(".itInRow").find("#itInRowValue");
		var adminValueId = parent.find(".adminInRow").find("#adminInRowValue");
		var index = $(this).parents(".employeeRoleInfo").index();
		
		// update role
		if("roleCheckBoxInRowOff" == $(this).attr("class")){
			$(this).removeClass("roleCheckBoxInRowOff");
			$(this).addClass("roleCheckBoxInRowOn");
			setValueOfRole(this, "true", attrId, itValueId, adminValueId, index);
			showUnderLineByCheckIsNew(this);
		}else if("roleCheckBoxInRowOn" == $(this).attr("class")){
			$(this).removeClass("roleCheckBoxInRowOn");
			$(this).addClass("roleCheckBoxInRowOff");
			setValueOfRole(this, "false", attrId, itValueId, adminValueId, index);
			showUnderLineByCheckIsNew(this);
		}
		//delete role
		if("deleteLink" == $(this).attr("class")){
			var object = this;
	    	ShowMsg(i18nProp('message_confirm_rolelist_delete_role'),function(yes){
			      if (yes) {
						var flag = false;
//						var currentEmployeeId = $(object).parents(".employeeRoleInfo").find(".employeeIdInRow").text();
						flag = isAtLeastOneITAndAdmin();
						if(true == flag){
							var isNew = parent.find("#isNew").text();
							if("true" == isNew){
								parent.remove();
								usersRoleInfo.splice(index - 1, 1);
							}else{
								parent.css("display", "none");
								usersRoleInfo[index - 1].isDelete = "true";
							}
						}else{
							ShowMsg(i18nProp('none_IT_and_Admin'));
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
	
	$("#resetButton").click(function(){
		clearInputBox();
	});
	
	$("#saveButton").click(function(){
		var hasITAndAdmin = isAtLeastOneITAndAdmin();
		
		if(hasITAndAdmin){
			$("#div-loader").show();
			saveOperation(usersRoleInfo);
		}else{
			ShowMsg(i18nProp('none_IT_and_Admin'));
		}
	});
	
	$("#cancelButton").click(function(){
		getUserRoloInfo();
	});
});

function isAtLeastOneITAndAdmin(currentEmployeeId){
	var employeeRoleInfo = $(".roleDispaly").find(".employeeRoleInfo");
	var ITNumber = employeeRoleInfo.find(".itInRow").find(".roleCheckBoxInRowOn").size();
	var AdminNumber = employeeRoleInfo.find(".adminInRow").find(".roleCheckBoxInRowOn").size();
	
	if(0 < ITNumber && 0 < AdminNumber){
		return true;
	}else{
		return false;
	}
	
}

function showUnderLineByCheckIsNew(object){
	var parent = $(object).parents(".employeeRoleInfo");
	var underLine = $(object).parent(".operateCheckbox").find(".underLine");
	var currentValue = $(object).parent(".operateCheckbox").find("input:first").val();
	var originalValue = $(object).parent(".operateCheckbox").find("input:last").val();
	
	if(currentValue != originalValue){
		underLine.show();
	}else{
		underLine.hide();
	}
}

function clearInputBox(){
	//delete the token by trigger the click event of the .token-input-delete-token-facebook
	//only do that '$("#employeeName").val("");' is invalid
	$("#autoText").find("ul").find(".token-input-delete-token-facebook").click();
}

function setValueOfRole(objec, value, attrId, itValueId, adminValueId,index){
	if(attrId == $(objec).attr("id")){
		itValueId.val(value);
		if(null != index){
			usersRoleInfo[index - 1].itRole = value;
		}
	}else{
		adminValueId.val(value);
		if(null != index){
			usersRoleInfo[index - 1].systemAdminRole = value;
		}
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
	var isExist = false;
	var itRole = $("#itValue").val();
	var systemAdminRole = $("#adminValue").val();
	var errorEmployeeNames = [];
	errorEmployeeNames.length = 0;
	
	if("false" == itRole && "false" == systemAdminRole) {
		ShowMsg(i18nProp('message_warn_role_is_null', null));
	}
	else {
		for(var i=0;i<listEmployees.length;i++) {
			var employeeName = listEmployees[i].split("#")[0];
			var employeeId = listEmployees[i].split("#")[1];
			if(employeeName == "") {
				ShowMsg(i18nProp('message_warn_user_is_null', null));
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
				if(employeeId == usersRoleInfo[j].employeeId) {
					$("#autoText li").each(function() {
						var errorEmployeeName = $(this).find("p").text();
						if(employeeName == errorEmployeeName) {
							errorEmployeeNames.push(errorEmployeeName);
							$(this).css("background-color","#FFF58F");
							$(this).css("border", "1px solid red");
						}
					});
				}
			}
			var employee = new Object();
			employee.employeeId = employeeId;
			employee.employeeName = employeeName;
			employee.itRole = itRole;
			employee.systemAdminRole = systemAdminRole;
			employee.isNew = "true";
			employees.push(employee);
			}
		if(0 < errorEmployeeNames.length){
			var names = errorEmployeeNames.join(",");
			employees.length = 0;
			ShowMsg(i18nProp('message_warn_role_illegal', names));
			return;
		}else{
			for(var m = 0; m < employees.length; m++){
				usersRoleInfo.push(employees[m]);
			}
			displayUserRoleInfo(employees);
			employees.length = 0;
			clearInputBox();
		}
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
			getUserRoloInfo();
			showMessageBarForMessage("role_save_success");
		},
		dataType: 'json',
		type: 'POST'
	});
}

function getUserRoloInfo(){
	$.ajax({
		contentType : 'application/json',  
        url: "user/getUserRoleInfo",
        success: function(data){
        	usersRoleInfo.length = 0;
        	usersRoleInfo = data.userRoleInfo;
        	for(var i = 0; i < usersRoleInfo.length; i++){
        		usersRoleInfo[i].isNew = "false";
        	}
        	$(".roleDispaly").find(".employeeRoleInfo").remove();
        	displayUserRoleInfo(usersRoleInfo);
        },
        dataType: "json",
        type: "POST"
    });
}

function displayUserRoleInfo(usersRoleInfo){
	if(null != usersRoleInfo){
		for(var i = 0; i < usersRoleInfo.length; i++){
			usersRoleInfo[i].isDelete = "false";
			$(".roleDispaly").append($(".employeeRoleInfoTemplate").html());
			var lastDivToAppend = $(".roleDispaly .employeeRoleInfo:last");
			lastDivToAppend.css("display", "block");
			var index = lastDivToAppend.index();
			lastDivToAppend.find("#sequence").text(index);
			lastDivToAppend.find("#isNew").text(usersRoleInfo[i].isNew);
			lastDivToAppend.find(".employeeIdInRow").text(usersRoleInfo[i].employeeId);
			lastDivToAppend.find(".employeeNameInRow").text(usersRoleInfo[i].employeeName);
			checkRole(usersRoleInfo[i].itRole, "#itInRow", "#itInRowValue", "#itInRowOriginalValue");
			checkRole(usersRoleInfo[i].systemAdminRole, "#adminInRow", "#adminInRowValue", "#adminInRowriginalValue");
		}
		tooltips(".employeeNameInRow");
	}
}

function checkRole(role, classId, valueId, originalValueId){
	var last = $(".roleDispaly .employeeRoleInfo:last");
	var lastClassId = last.find(classId);
	if(true == role || "true" == role){
		lastClassId.removeClass("roleCheckBoxInRowOff");
		lastClassId.addClass("roleCheckBoxInRowOn");
		last.find(valueId).val("true");
		last.find(originalValueId).val("true");
	}else{
		lastClassId.removeClass("roleCheckBoxInRowOn");
		lastClassId.addClass("roleCheckBoxInRowOff");
		last.find(valueId).val("false");
		last.find(originalValueId).val("false");
	}
}
