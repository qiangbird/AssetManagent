$(document).ready(function() {
    var ridioImgs = ["Radio_14x14_Checked.png", "Radio_14x14_Unchecked.png", "Radio_14x14_Checked_Disabled.png", "Radio_14x14_Unchecked_Disabled.png"];
    var tx = "";
    tx = i18nProp("createImport");
    $(".dialogBody").find("p").text(i18nProp("createImportMsg"));
    $("#create").click(function() {
        removeAllData();
        $("#update").css("background", "url('img/" + ridioImgs[1] + "') no-repeat");
        $("#create").css("background", "url('img/" + ridioImgs[0] + "') no-repeat");
        $("#flag").val("create");
    });
    $("#update").click(function() {
        removeAllData();
        $("#create").css("background", "url('img/" + ridioImgs[1] + "') no-repeat");
        $("#update").css("background", "url('img/" + ridioImgs[0] + "') no-repeat");
        
        $("#flag").val("update");
        tx = i18nProp("updateImport");
        $(".dialogBody").find("p").text(i18nProp("updateImportMsg"));
    });
    
    $(".start-button").click(function() {
        var inId = showCheckingNote(tx);
        $("#importForm").ajaxSubmit(
	        function(data) {
	        	closeCheckingNote(inId);
			    $("#allSize").text(data.all);
				$("#sucSize").text(data.success);
				$("#fiaSize").text(data.failure);
				$("#failureFileName").val(data.failureFileName);
					
				$(".start-button").css("background", "url('img/button/BTN_90x30_Secondary_Action_Disabled.png') no-repeat").css("color", "#BBBBBB").attr("disabled", "disabled");
				
				$(".import-text").val("");
				$(".import-file").val("");
				
				if($("#fiaSize").text() != "0") {
					alert(i18nProp("msg"));
				}
				
				if($("#fiaSize").text()!=0) {
	               $("#exportError").addClass("export-icon-active");
	            } else {
	               $("#exportError").removeClass("export-icon-active");
	            }
				
	            return false;
	        }
        );
        
        $("#exportError").click(function() {
            if($("#fiaSize").text()!=0) {
		        var fileName = $("#failureFileName").val();
		        location.href="asset/download?fileName=" + fileName;
            }
	    });
    });
    
});

function removeAllData() {
	$(".start-button").attr("disabled","disabled");
    $(".import-text").val("");
    $(".import-file").val("");
    $("#allSize").text("0");
    $("#sucSize").text("0");
    $("#fiaSize").text("0");
    $(".start-button").css("background", "url('img/button/BTN_90x30_Secondary_Action_Disabled.png') no-repeat").css("color", "#BBBBBB");
    $("#exportError").removeClass("export-icon-active");
}

function showCheckingNote(auditFileName) {
    $(".shady").css("display", "block");
       
    // chanage lis background
    var lis = $("#checkingInventoryNote").find("li");
    lis.css("background", "url('img/ICN_Checking_24x24.png') no-repeat");
    var inId = changeNoteLis(lis);

    $(".dialogTitle").find("h3").text(auditFileName);
    $("#checkingInventoryNote").css("display", "block");
    
    return inId;
}

function closeCheckingNote(inId) {
    clearInterval(inId);
    $("#checkingInventoryNote").css("display", "none");
    $(".shady").css("display", "none");
}

function changeNoteLis(lis) {
    i = 0;
    var inId = setInterval(function() {
        if (i == 0) {
            $(lis[i]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[i+1]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[i+2]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[12]).css("background", "url('img/ICN_Checking_24x24.png') no-repeat");
        } else if(i >= 1 && i <= 10) {
            $(lis[i]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[i+1]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[i+2]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[i-1]).css("background", "url('img/ICN_Checking_24x24.png') no-repeat");
        } else if (i == 11) {
            $(lis[11]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[12]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[0]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[10]).css("background", "url('img/ICN_Checking_24x24.png') no-repeat");
        } else if (i == 12) {
            $(lis[12]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[0]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[1]).css("background", "url('img/ICN_Checking-Active_24x24.png') no-repeat");
            $(lis[11]).css("background", "url('img/ICN_Checking_24x24.png') no-repeat");
            i = -1;
        }
        i++;  
    }, 500);
    return inId;
}


function showStartMsg(panel3) {
    $(".start-button").mouseenter(function() {
        panel3.fadeIn(200);
    });
    $(".start-button").mouseout(function() {
        panel3.fadeOut(200);
    });
    
}

function uploadFile(file) {
    var path = file.value;
    var fileName=path.split("C:\\fakepath\\")[1];
    var l = path.lastIndexOf(".");
    var len = path.length;
    var expand = path.substring(l, len);
    if(expand != ".xls") {
    	alert(i18nProp("excelError") + "（*.xls）。");
        return false;
    }
    $(".import-text").val(fileName);

    $(".start-button").css("background", "url('img/BTN_90x30_Secondary_Action_Normal.png') no-repeat").css("color", "#555555").removeAttr("disabled");
}

function creatHintUpPanel(obj, width, height, top, left, message, imgs) {
    var pWidth = width + 8;
    var pHeigh = height + 17; 
    
    var tWidth = (width - 14)/2;

    var panel = $("<div>").css("width", pWidth)
                          .css("height", pHeigh)
                          .css("display", "none")
                          .css("position", "absolute")
                          .css("top", top)
                          .css("left", left)
                          .css("z-index", 10)
                          .appendTo(obj);
                          
    var t_l =  $("<div>").css("width", 4)
                         .css("height", 5)
                         .css("position", "absolute")
                         .css("top", 0)
                         .css("left", 0)
                         .css("background", "url('img/" + imgs[0] + "') no-repeat")
                         .appendTo(panel);    
    var t_c =  $("<div>").css("width", width)
                         .css("height", 5)
                         .css("position", "absolute")
                         .css("top", 0)
                         .css("left", 4)
                         .css("background", "url('img/" + imgs[1] + "') repeat-x")
                         .appendTo(panel);                      
    var t_r =  $("<div>").css("width", 4)
                         .css("height", 5)
                         .css("position", "absolute")
                         .css("top", 0)
                         .css("left", width+4)
                         .css("background", "url('img/" + imgs[2] + "') no-repeat")
                         .appendTo(panel);                      
    var c_l =  $("<div>").css("width", 4)
                         .css("height", height)
                         .css("position", "absolute")
                         .css("top", 5)
                         .css("left", 0)
                         .css("background", "url('img/" + imgs[3] + "') repeat-y")
                         .appendTo(panel);                      
    var c_c =  $("<div>").css("width", width)
                         .css("height", height)
                         .css("position", "absolute")
                         .css("top", 5)
                         .css("left", 4)
                         .css("background", "#FFFFFF")
                         .appendTo(panel);   
    var c_r =  $("<div>").css("width", 4)
                         .css("height", height)
                         .css("position", "absolute")
                         .css("top", 5)
                         .css("left", width+4)
                         .css("background", "url('img/" + imgs[4] + "') repeat-y")
                         .appendTo(panel);                      
    var b_l =  $("<div>").css("width", 4)
                         .css("height", 17)
                         .css("position", "absolute")
                         .css("top", height+5)
                         .css("left", 0)
                         .css("background", "url('img/" + imgs[5] + "') no-repeat")
                         .appendTo(panel);                      
    var b_ll =  $("<div>").css("width", tWidth)
                         .css("height", 17)
                         .css("position", "absolute")
                         .css("top", height+5)
                         .css("left", 4)
                         .css("background", "url('img/" + imgs[6] + "') repeat-x")
                         .appendTo(panel);                      
    var b_rr =  $("<div>").css("width", tWidth)
                         .css("height", 17)
                         .css("position", "absolute")
                         .css("top", height+5)
                         .css("left", tWidth + 2 + 16)
                         .css("background", "url('img/" + imgs[6] + "') repeat-x")
                         .appendTo(panel);                      
    var b_r =  $("<div>").css("width", 4)
                         .css("height", 17)
                         .css("position", "absolute")
                         .css("top", height+5)
                         .css("left", width+4)
                         .css("background", "url('img/" + imgs[7] + "') no-repeat")
                         .appendTo(panel);                      
    var b_c =  $("<div>").css("width", 16)
                         .css("height", 17)
                         .css("position", "absolute")
                         .css("top", height+5)
                         .css("left", tWidth+4)
                         .css("background", "url('img/" + imgs[8] + "') no-repeat")
                         .appendTo(panel);  
    c_c.text(message); 
    return  panel;                                    
}
function showHelpTips(){
	$("#helpTips").toggle();
	
}

function hideHelpTips(){
	$("#helpTips").hide();
	
}
