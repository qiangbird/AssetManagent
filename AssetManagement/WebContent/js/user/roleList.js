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
        url: "base/getEmployeeDataSource",
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
			setValueOfRole(this, true, attrId, itValueId, adminValueId, null);
		}else{
			$(this).removeClass("roleCheckBoxOn");
			$(this).addClass("roleCheckBoxOff");
			setValueOfRole(this, false, attrId, itValueId, adminValueId, null);
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
						var currentEmployeeId = $(object).parents(".employeeRoleInfo").find(".employeeIdInRow").text();
						flag = isAtLeastOneITAndAdmin(currentEmployeeId);
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
							alert("There must at least two user has IT and System Admin role");
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
		//there is no current employee id
		isAtLeastOneITAndAdmin(null);
		
		saveOperation(usersRoleInfo);
	});
	
	$("#cancelButton").click(function(){
		getUserRoloInfo();
	});
});

function isAtLeastOneITAndAdmin(currentEmployeeId){
	var employeeIdForIT = "";
	var employeeIDFroAdmin = "";
	var hasBothRole = false;
	
	//check employeeIdForIT
	for(var i = 0; i < usersRoleInfo.length; i++){
		if(true == usersRoleInfo[i].itRole && 
				usersRoleInfo[i].employeeId != currentEmployeeId){
			
			employeeIdForIT = usersRoleInfo[i].employeeId;
			
			hasBothRole = checkBothRole(hasBothRole, usersRoleInfo[i].systemAdminRole);
			break;
		}
	}
	//check employeeIDFroAdmin
	for(var i = 0; i < usersRoleInfo.length; i++){
		if(true== usersRoleInfo[i].systemAdminRole &&
				usersRoleInfo[i].employeeId != currentEmployeeId &&
				employeeIdForIT != usersRoleInfo[i].employeeId){
			
			employeeIDFroAdmin = usersRoleInfo[i].employeeId;
			
			hasBothRole = checkBothRole(hasBothRole, usersRoleInfo[i].itRole);
			break;
		}
	}
	
	if(hasBothRole && ("" != employeeIDFroAdmin || "" != employeeIdForIT)){
		return true;
	}
	
	//compare employeeIdForIT with employeeIDFroAdmin and currentEmployeeId
	if(employeeIdForIT != employeeIDFroAdmin && 
			"" != employeeIDFroAdmin && 
			"" != employeeIdForIT){
		return true;
	}else{
		return false;
	}
}

function checkBothRole(hasBothRole, roleValue){
	
	if(true== roleValue){
		hasBothRole = true;
	}
	return hasBothRole;
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
		//showTipMessage($document.find("#showError"),msg.prop("User.eror.emplyeeAtLeastOneRole"),350,30,3000);
		alert("Role can't be null");
	}
	else {
		for(var i=0;i<listEmployees.length;i++) {
			var employeeName = listEmployees[i].split("#")[0];
			var employeeId = listEmployees[i].split("#")[1];
			if(employeeName == "") {
//				showTipMessage($document.find("#showError"),msg.prop("User.error.nullEmployeeNme"),350,30,3000);
				alert("User is null!");
				return;
			}else if(employeeId == "" || employeeId == undefined) {  // employee not exist
				$("#autoText li").each(function() {
					var errorEmployeeName = $(this).find("p").text();
					if(employeeName == errorEmployeeName) {
						$(this).css("background-color","#FFF58F");
						$(this).css("border", "1px solid red");
					}
				});
	/*			var message = msg.prop("User.error.notExistEmployee",employeeName);
				showTipMessage($document.find("#showError"),message,350,30,3000);*/
				alert(employeeName + " is not exist!");
				employees.length = 0;
				return;
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
//			employee.isDelete = "false";
			employee.employeeId = employeeId;
			employee.employeeName = employeeName;
			employee.itRole = itRole;
			employee.systemAdminRole = systemAdminRole;
			employee.isNew = "true";
			employees.push(employee);
			}
		if(0 < errorEmployeeNames.length){
			var names = errorEmployeeNames.join(",");
			alert(names + " already exist!");
			employees.length = 0;
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
			window.location.reload(true);
			alert("Save successfully!");
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
