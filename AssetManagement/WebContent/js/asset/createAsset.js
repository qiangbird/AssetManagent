$(document).ready(function() {
// get the needed data from background
	
$("#assetType").DropDownList({
    multiple : false,
    header : false
});
$(".dropDownList_div:eq(0)").click(function() {
var currentType = $(".dropDownList_text_select:eq(0)").text();
if (currentType.trim() == 'MACHINE') {
    $("#machineDetails").show().siblings().hide();
} else if (currentType.trim() == 'MONITOR') {
    $("#monitorDetails").show().siblings().hide();
} else if (currentType.trim() == 'DEVICE') {
    $("#deviceDetails").show().siblings().hide();
} else if (currentType.trim() == 'SOFTWARE') {
    $("#softwareDetails").show().siblings().hide();
} else if (currentType.trim() == "OTHERASSETS") {
    $("#otherAssetsDetails").show().siblings().hide();
}
});

$("#machineType").DropDownList({
    multiple : false,
    header : false,
    noneSelectedText : 'Select machine type',
});
$("#selectedStatus").DropDownList({
    multiple : false,
    header : false,
    noneSelectedText : 'Select status',
});
$("#selectedEntity").DropDownList({
    multiple : false,
    header : false,
    noneSelectedText : 'Select entity',
});
$("#selectedSite").DropDownList({
    multiple : false,
    header : false
});
//$("#selectedLocation").DropDownList({
//    multiple : false,
//    header : false
//});
$("#ownership").click(function() {
    $.ajax({
    type : 'GET',
    contentType : 'application/json',
    url : 'customer/getCustomerInfo',
    dataType : 'json',
    success : function(data) {
        length = data.customerList.length;
        custName = [];
        for ( var i = 0; i < length; i++) {
            custName[i] = data.customerList[i].customerName;
        }
        $("#ownership").autocomplete({
            source : custName
        });
     }
  });
});

$("#customerName").blur(function() {
    if ($("#project").val() == "") {
        $("#projectCode").val("");
    }
});

$("#customerName").click(function() {
$("#keeperSelect").empty();
custCode = [];
$.ajax({
    type : 'GET',
    contentType : 'application/json',
    url : 'customer/getCustomerInfo',
    dataType : 'json',
    success : function(data) {
    console.log(data.customerList);
    length = data.customerList.length;
    custName = [];
    for ( var i = 0; i < length; i++) {
        custName[i] = data.customerList[i].customerName;
        custCode[i] = data.customerList[i].customerCode;
    }
    custArray = custName;
    $("#customerName").autocomplete({
        minLength : 0,
        source : custName,
        select : function(e,ui) {
        console.log(custCode[getIndexInArr(custName,ui.item.value)]);
        $("#customerCode").val(custCode[getIndexInArr(custName,ui.item.value)]);
        $("#project").val("");
        customerCode = custCode[getIndexInArr(custName,ui.item.value)];
        $.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'project/getProjectByCustomerCode?customerCode='+ customerCode,
            dataType : 'json',
            success : function(data) {
                console.log(data);
                console.log(data.projectList);
                length = data.projectList.length;
                projectName = [];
                projectCode = [];
                projectManagerEmployeeId = [];
                projectManagerName = [];
                projectManagerNames = "";
                projectManagerCodes = "";
                for ( var i = 0; i < length; i++) {
                    projectName[i] = data.projectList[i].projectName;
                    projectCode[i] = data.projectList[i].projectCode;
                    projectManagerEmployeeId[i] = data.projectList[i].projectManagerEmployeeId;
                    projectManagerName[i] = data.projectList[i].projectManagerName;

                    projectManagerNames += data.projectList[i].projectManagerName + ";";
                    projectManagerCodes += data.projectList[i].projectManagerEmployeeId + ";";
                }
                if ($("#customerName").val() == "Augmentum China") {
                    $("#keeperSelect").val("Ping Zhou");
                    $("#keeperCode").val("T00001");
                } else {
                    $("#keeperSelect").val(projectManagerNames);
                    $("#keeperCode").val(projectManagerCodes);
                }
            },
            error : function() {
            	showMessageBarForOperationResultMessage("error");
            }
        });
        }
    });
  }
});
$("#customerCode").val(custCode);
});

$("#assetUser").click(function() {
    $.ajax({
    type : 'GET',
    contentType : 'application/json',
    url : 'user/getEmployeeDataSource',
    dataType : 'json',
    success : function(data) {
        console.log(data);
        length = data.employeeInfo.length;
        employeeName = [];
        employeeValue = [];
        for ( var i = 0; i < length; i++) {
            employeeName[i] = data.employeeInfo[i].label;
            employeeValue[i] = data.employeeInfo[i].value;
        }
        userArray = employeeName;
        $("#assetUser").autocomplete({
            minLength : 0,
            source : employeeName,
            select : function() {
            $("#assetUser").change(
                function() {
                if ($(this).val().trim() == "") {
                    TextMouseOutError(this);
                } else {
                    if (!checkInArr(userArray,$("#assetUser").val())) {
                        TextMouseOutError(this);
                    } else {
                        TextMouseOutNormal(this);
                    }
                }
                });
            $("#assetUser").blur(
                function() {
                if ($(this).val().trim() == "") {
                	if($("#selectedStatus").val()=="IN_USE"){
                		TextMouseOutError(this);
                	}
                	TextMouseOutNormal(this);
                } else {
                    if (!checkInArr(userArray,$("#assetUser").val())) {
                        TextMouseOutError(this);
                    } else {
                    	$("#selectedStatus").val("IN_USE");
                    	$("#selectedStatusList .dropDownList_text_select").text("IN_USE");
                        TextMouseOutNormal(this);
                    }
                }
                });
            $("#assetUser").focus(
                function() {
                TextMouseOutNormal(this);
                    });
            }
        });

    },
    });
});

//// below is about location
//$("#selectedLocation").click(function(){
//	currentSite = $("#selectedSite").val();
//	 $.ajax({
//		    type : 'GET',
//		    contentType : 'application/json',
//		    url : 'location/getLocationRoom?currentSite='+currentSite.replace("_"," "),
//		    dataType : 'json',
//		    success : function(data) {
//		        console.log(data);
//		        length = data.locationRoomList.length;
//		        $("#selectedLocation").autocomplete({
//		            source : data.locationRoomList
//		        })
//		        
//		    }
//});
//});

//$("#selectedLocation").blur(
//        function() {
//        if ($(this).val().trim() == "") {
//            TextMouseOutError(this);
//        } else {
//            if (!checkInArr(rooms,$(this).val())) {
//            	console.log(rooms);
//                TextMouseOutError(this);
//            } else {
//                TextMouseOutNormal(this);
//            }
//        }
//        });

$("#deviceSubtypeSelect").click(function() {
    $.ajax({
    type : 'GET',
    contentType : 'application/json',
    url : 'deviceSubtype/getAllSubtypes',
    dataType : 'json',
    success : function(data) {
        console.log(data.deviceSubtypeList);
        subtypeNames = [];
        for (i = 0; i < data.deviceSubtypeList.length; i++) {
            subtypeNames[i] = data.deviceSubtypeList[i].subtypeName;
        }
        $("#deviceSubtypeSelect").autocomplete({
            source : subtypeNames
            });
    }
});
});
$("#assetName").blur(function() {
if ($(this).val().trim() == "") {
        TextMouseOutError(this);
    } else {
        TextMouseOutNormal(this);
    }
});

$("#ownership,#customerName").click(function() {
    DropdownMouseEnter(this);
});
$("#customerName").blur(
function() {
    if ($(this).val().trim() == "") {
    DropdownMouseOutError(this);
} else {
    if (!checkInArr(custName,
    $("#customerName").val())) {
            DropdownMouseOutError(this);
        } else {
            DropdownMouseOutNormal(this);
        }
    }
 });

$("#ownership").blur(
function() {
    if ($(this).val().trim() == "") {
    DropdownMouseOutError(this);
} else {
    if (!checkInArr(custName, $("#ownership").val())) {
        DropdownMouseOutError(this);
    } else {
        DropdownMouseOutNormal(this);
    }
}
});

$("#memo").click(function() {
    $(this).addClass("l-textarea-hover").removeClass("l-textarea");
        });
$("#memo").blur(function() {
    $(this).addClass("l-textarea").removeClass("l-textarea-hover");
        });

$("#maxUseNum").blur(function() {
    if ($(this).val() == ""|| !numberCheck($(this).val().trim())) {
        TextMouseOutError(this);
    } else {
        TextMouseOutNormal(this);
    }
});

$("#project").click(function() {
    if ($("#customerName").val() == "") {
    return;
}
$("#project").empty();
projectManagerEmployeeId = [];
projectManagerName = [];
var customerCode = "";
customerCode = $("#customerCode").val();
$.ajax({
    type : 'GET',
    contentType : 'application/json',
    url : 'project/getProjectByCustomerCode?customerCode='+ customerCode,
    dataType : 'json',
    success : function(data) {
        console.log(data);
        console.log(data.projectList);
        length = data.projectList.length;
        projectName = [];
        projectCode = [];
        for ( var i = 0; i < length; i++) {
            projectName[i] = data.projectList[i].projectName;
            projectCode[i] = data.projectList[i].projectCode;
            projectManagerEmployeeId[i] = data.projectList[i].projectManagerEmployeeId;
            projectManagerName[i] = data.projectList[i].projectManagerName;

        }
        projectNameArr = projectName;
        $("#project").autocomplete({
            minLength : 0,
            autoFocus : true,
            source : projectName,
            select : function(e,ui) {
                $("#projectCode").val(projectCode[getIndexInArr(projectNameArr,ui.item.value)]);
                if ($("#customerName").val() == "Augmentum China") {
                    $("#keeperSelect").val("Ping Zhou");
                    $("#keeperCode").val("T00001");
                } else {
                    $("#keeperSelect").val(projectManagerName[getIndexInArr(projectNameArr,ui.item.value)]);
                    $("#keeperCode").val(projectManagerEmployeeId[getIndexInArr(projectNameArr,ui.item.value)]);
                }
            },
            create : function(event,ui) {
                $(this).bind("click",
                    function() {
                        var active = $(this).data("autocomplete").menu.active;
                        if (!active) {
                            $(this).autocomplete("search","");
                        }
                    });
            }
        })
            }
        });
    });

});

$(document).ready(function() {
if ($("#project").val() == "") {
$("#projectCode").val("");
}
$("#assetUser").change(function() {
$("#userId").val("");
    });

})
