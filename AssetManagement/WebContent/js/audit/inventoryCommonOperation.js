function upToDoneImpl(auditFileName, url){
    jQuery.post("auditFile/updateToDone", {auditFileName: auditFileName} ,
                function(data) {
    				window.location.href = url;
                }
    ); 
}

function removeAuditFileImpl(auditFileName, url) {
    jQuery.post("auditFile/deleteAuditFile", {auditFileName: auditFileName} ,
                function() {
    				window.location.href = url;
                }
    );  
}

function checkInventory(Obj, flag) {
    var p = Obj.value;
    var index = p.lastIndexOf(".");
    var newP = p.substring(index+1, p.length).toLowerCase();
    if(newP != "csv") {
//    	$.alert(msg.prop('Error'),msg.prop("uploadMess"));
        alert("文件格式错误");
        return false;
    }
    var tx = $(Obj).next().val();  
    var inId = showCheckingNote(tx);
    $(Obj).parent("form").ajaxSubmit(
        function(data) {
            closeCheckingNote(inId);
            
            if("checkInventory" == flag){
            	refreshCheckInventoryData();
            }
            if("inventoryDetails" == flag){
            	refreshInventoryDetailsData(data);
            }
            return false;
        }
    );
}

function showCheckingNote(auditFileName) {
    $(".shady").css("display", "block");
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
    var i = 0;
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
    }, 50);
    return inId;
}