var customers = [];
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
	
	$("#resetButton").click(function(){
		clearInputBox();
	});
	
	$("#saveButton").click(function(){
		$("#div-loader").show();
		saveOperation(specialRoles);
	});
	
	$("#cancelButton").click(function(){
		findSpecialRoles();
	});
	$("#bodyMinHight").delegate("#customerName, #token-input-employeeName","blur",function(){
		if("" != $(".employeeName").val()){
			$(".token-input-list-facebook").clearValidationMessage();
		}
		if("" != $("#customerName").val()){
			$("#customerName").clearValidationMessage();
		}
	});
	
	$("#customerName").click(function() {
	    for(var i = 0; i < customers.length; i++){
	    		customerNames[i] = customers[i].customerName;
	    		customerCodes[i] = customers[i].customerCode;
	    }
	    $("#customerName").autocomplete({
	         source : customerNames,
	         select : function(e,ui) {
	                $("#customerCode").val(customerCodes[getIndexInArr(customerNames,ui.item.value)]);
	                $("#project").val("");
	                var customerCode = customerCodes[getIndexInArr(customerNames,ui.item.value)];
	                findEmployeesByCustomerCode(customerCode);
	         }
	   });
	});
});

function findCustomers(){
	$.ajax({
	    type : 'GET',
	    contentType : 'application/json',
	    url : 'specialRole/findSpecialRoles',
	    dataType : 'json',
	    success : function(data) {
	    	customers = data.customers;
	    }
	});
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
		dataType: 'json',
		type: 'POST'
	});
}

function initAddProccess(){
	if("success" == checkCustomer()){
		checkEmployees();
	}else{
		return;
	}
}

function checkCustomer(){
	var validations = new Array();
    var validation = "success";
    var customerNameInput = $("#customerName").val();
    var existCustomer = false;
    
    if("" == customerNameInput){
    	$("#customerCode").val("");
    }else{
    	for(var i = 0; i < customers.length; i++){
    		customerName = customers[i].customerName;
    		if(customerName == customerNameInput){
    			$("#customerCode").val(customers[i].customerCode);
    			existCustomer = true;
    			break;
    		}
    	}
    }
    if(false == existCustomer){
    	$("#customerCode").val("");
    }
    var customerCode = $("#customerCode").val();
    
    validations.push($("#customerName")
    		.validateNull(customerCode,i18nProp('message_warn_customer_is_null_or_not_exist')));
    
    validation = recordFailInfo(validations);
    
    return validation;
}

function checkEmployees(){
	var employeeInput = $("#employeeName").val();
	var customerName = $("#customerName").val();
	var customerCode = $("#customerCode").val();
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
               .css("left", "78px")
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

function getType(o)
{
    var _t;
    return ((_t = typeof(o)) == "object" ? o==null && "null" || Object.prototype.toString.call(o).slice(8,-1):_t).toLowerCase();
}
function deepCopyArray(destination,source)
{
    for(var p in source)
    {
        if(getType(source[p])=="array"||getType(source[p])=="object")
        {
            destination[p]=getType(source[p])=="array"?[]:{};
            arguments.callee(destination[p],source[p]);
        }
        else
        {
            destination[p]=source[p];
        }
    }
}
