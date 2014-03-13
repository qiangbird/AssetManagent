var customerNames = [];
var customerCodes = [];
var specialRoles = [];
var employeeInfo = [];
var usersRoleInfo =  [];
var isDelete =  [];
var employees = [];

$(document).ready(function() {
	findCustomers();
	findSpecialRoles();
	
	$(".roleListContent").delegate(".deleteLink", "click", function(){
		var parent = $(this).parents(".employeeRoleInfo");
		var index = $(this).parents(".employeeRoleInfo").index();
		
		//delete role
		if("deleteLink" == $(this).attr("class")){
	    	ShowMsg(i18nProp('message_confirm_rolelist_delete_role'),function(yes){
			      if (yes) {
						var isNew = parent.find("#isNew").text();
						if("true" == isNew){
							parent.remove();
							specialRoles.splice(index - 1, 1);
						}else{
							parent.css("display", "none");
							specialRoles[index - 1].isDelete = "true";
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
		$("#div-loader").show();
		saveOperation(specialRoles);
	});
	
	$("#cancelButton").click(function(){
		findSpecialRoles();
	});
	$("#bodyMinHight").delegate("#token-input-employeeName","blur",function(){
		if("" != $(".employeeName").val()){
			$(".token-input-list-facebook").clearValidationMessage();
		}
	});
	$("#main").delegate("#customerCode input:first", "blur", function(){
		doCustomerValidation();
	});
});

function findCustomers(){
	$.ajax({
	    type : 'GET',
	    contentType : 'application/json',
	    url : 'specialRole/findSpecialRoles',
	    dataType : 'json',
	    success : function(data) {
	    	var firstValue = "";
	    	if(data.customers != undefined && data.customers.length > 0){
	    		firstValue = data.customers[0].value;
	    	}
	    	var customerMs = initDropDownMap($("#customerCode"), data.customers, false, "label", "value", firstValue, 275);
            $("#customerCode").data("customerCode",customerMs);
            customerBindEvent();
	    }
	});
}

function customerBindEvent(){
	var customerCode = $("#customerCode").data("customerCode").getValue().join();
	
	if("" != customerCode){
		findEmployeesByCustomerCode(customerCode);
	}
	var customerMs = $("#customerCode").data("customerCode");
	$(customerMs).on('selectionchange', function(event, combo, selection){
		var customerCode = $("#customerCode").data("customerCode").getValue().join();
		var selectedItems = $("#customerCode").data("customerCode").getSelectedItems();
		
		if(0 < selectedItems.length){
			var customerName = selectedItems[0].label;
			$("#customerName").val(customerName);
		}else{
			$("#customerName").val("");
		}
		if(doCustomerValidation()){
			findEmployeesByCustomerCode(customerCode);
		}
    });
}

function doCustomerValidation(){
	var customerName = $("#customerName").val();
	return validateValueIsEmpty($("#customerCode"), customerName, i18nProp('message_warn_customer_is_null_or_not_exist'));
}

function getIndexInArr(Arr, ele) {
    for ( var i = 0; i < Arr.length; i++) {
        if (ele == Arr[i]) {
            return i;
        }
    }
    return -1;
}

function clearInputBox(){
	//delete the token by trigger the click event of the .token-input-delete-token-facebook
	//only do that '$("#employeeName").val("");' is invalid
	$("#autoText").find("ul").find(".token-input-delete-token-facebook").click();
}

function saveOperation(specialRoles){
	$.ajax({
		contentType : 'application/x-www-form-urlencoded',
		url: 'specialRole/saveOrUpdateSpecialRole',
		data: { 
			"specialRoles":JSON.stringify(specialRoles)
		},
		success: function(){
			$("#div-loader").hide();
			findCustomersAndSpecialRoles();
			showMessageBarForMessage("role_save_success");
		},
//		dataType: 'json',
		type: 'POST'
	});
}

function initAddProccess(){
	if(doCustomerValidation){
		checkEmployees();
	}else{
		return;
	}
}

function checkEmployees(){
	var employeeInput = $("#employeeName").val();
	var customerName = $("#customerName").val();
	var customerCode = $("#customerCode").data("customerCode").getValue.join();
	var listEmployees = employeeInput.split(",");
	var errorEmployeeNames = [];
	errorEmployeeNames.length = 0;
	
		for(var i=0;i<listEmployees.length;i++) {
			var employeeName = listEmployees[i].split("#")[0];
			var employeeId = listEmployees[i].split("#")[1];
/*			var department = listEmployees[i].split("#")[2];
			var manager = listEmployees[i].split("#")[3];*/
			if(employeeName == "") {
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
			for(var j = 0; j< specialRoles.length; j++) {
				// employee already exist and has already been delete
				if((employeeId == specialRoles[j].employeeId) && specialRoles[j].isDelete != "true") {
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
			employee.customerName = customerName;
			employee.customerCode = customerCode;
			employee.isNew = "true";
			employees.push(employee);
			}
		if(0 < errorEmployeeNames.length){
			var names = errorEmployeeNames.join(",");
			employees.length = 0;
			showMessageBarForOperationResultMessage(i18nProp('message_warn_role_illegal', names));
			return;
		}else{
			for(var m = 0; m < employees.length; m++){
				specialRoles.push(employees[m]);
			}
			displaySpecialRoleList(employees);
			employees.length = 0;
			clearInputBox();
		}
}

function displaySpecialRoleList(specialRoles){
	if(null != specialRoles){
		for(var i = 0; i < specialRoles.length; i++){
			specialRoles[i].isDelete = "false";
			$(".roleDispaly").append($(".employeeRoleInfoTemplate").html());
			var lastDivToAppend = $(".roleDispaly .employeeRoleInfo:last");
			lastDivToAppend.css("display", "block");
			var index = lastDivToAppend.index();
			lastDivToAppend.find("#sequence").text(index);
			lastDivToAppend.find("#isNew").text(specialRoles[i].isNew);
			lastDivToAppend.find(".employeeIdInRow").text(specialRoles[i].employeeId);
			lastDivToAppend.find(".employeeNameInRow").text(specialRoles[i].employeeName);
			lastDivToAppend.find(".departmentInRow").text(specialRoles[i].department);
			lastDivToAppend.find(".customerNameInRow").text(specialRoles[i].customerName);
			lastDivToAppend.find(".managerInRow").text(specialRoles[i].manager);
		}
	}
}

function findSpecialRoles(){
	$.ajax( {  
	    type : 'GET',  
	    contentType : 'application/json',  
	    url : 'specialRole/findSpecialRoles', 
	    dataType : 'json',  
	    success : function(data) { 
	    	specialRoles = data.specialRoles;
	    	if(0 < specialRoles.length){
		    	for(var i = 0; i < specialRoles.length; i++){
		    		specialRoles[i].isNew = "false";
	        	}
	        	$(".roleDispaly").find(".employeeRoleInfo").remove();
		    	displaySpecialRoleList(specialRoles);
		    }
	    }
	 });
}

function findEmployeesByCustomerCode(customerCode){
	var params = {
		    elementId : "employeeName"
	};
	$.ajax( {  
	    type : 'POST',  
	    url : 'specialRole/findEmployeesByCustomerCode',  
	    data: {
	    	"customerCode":customerCode
	    },
	    dataType : 'json',  
	    success : function(data) { 
	    	params.dataSource= data;
        	employeeInfo = data;
        	
        	// remove the ul generate before, otherwise it will
        	// produce repeat ul.
        	$("#autoText ul").remove();
        	
        	autoComplete(params);  
	    }
	});
}

function autoComplete(params) {
	var setting = {
		    minChars : 1,
		    width : 300,
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
