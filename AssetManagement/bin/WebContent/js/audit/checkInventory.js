var auditProcessTr = $("#dataTable tr:first-child").html().toString();
var auditDoneTr = $("#dataTable tr:nth-child(2)").html().toString();

$(document).ready(function(){
    
	refreshCheckInventoryData();
    
	$("#processBtn").click(function(){
	    $("#doneBtn").removeClass("left-control-actived");
	    $(this).addClass("left-control-actived");
	    showProcessingAudit = true;
	    showProcessBtnBlank(showProcessingAudit);
	    showAuditList(showProcessingAudit);
	});
	
    $("#doneBtn").click(function(){
        $("#processBtn").removeClass("left-control-actived");
        $(this).addClass("left-control-actived");
        showProcessingAudit = false;
        showProcessBtnBlank(showProcessingAudit);
        showAuditList(showProcessingAudit);
    });
});

function refreshCheckInventoryData(){
	var showProcessingAudit = true;
    showProcessBtnBlank(showProcessingAudit);
    showAuditList(showProcessingAudit);
    $("#processBtn").addClass("left-control-actived");
    
    $.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'auditFile/getAuditFilesCount',
        dataType : 'json',
        success : function(data) {
            $(".process-count").html("(" + data.auditFilesCount.processCount + ")");
            $(".done-count").html("(" + data.auditFilesCount.doneCount + ")");
        }
    });
}

$("#processFiles").delegate(".check-done-button", "click", function(){
	upToDone(this); 
});

$("#processFiles").delegate(".check-delete-button", "click", function(){
	removeAuditFile(this); 
});

//upto done
function upToDone(object) {
	ShowMsg("Are you sure to done this file?",function(yes){
	      if (yes) {
	    	  var auditFileName=$(object).parents(".process-panel").find("a").text();
	    	  var url = "auditFile/inventoryList";
	    	  upToDoneImpl(auditFileName, url);
	      }else{
	          return;
	      }
	    });
}

function removeAuditFile(object) {
	ShowMsg("Are you sure to remove this file?",function(yes){
	      if (yes) {
	    	  var auditFileName=$(object).parents(".process-panel").find("a").text();
	    	  var url = "auditFile/inventoryList";
	    	  removeAuditFileImpl(auditFileName, url);
	      }else{
	          return;
	      }
	    });
}

function initUpload(object){
	var auditFileName=$(object).parents(".process-panel").find("a").text();
	$(object).siblings("#auditFileName").val(auditFileName);
	checkInventory(object, "checkInventory");
}

function showProcessBtnBlank(showProcessingAudit) {
    if (showProcessingAudit) {
        $("#processBtn .processBtn-control-blank").addClass("processBtn-control-blank-actived");
        $("#doneBtn .processBtn-control-blank").removeClass("processBtn-control-blank-actived");
        $("#doneBtn span").addClass("left-control-padding");
        $("#processBtn span").removeClass("left-control-padding");
    } else {
        $("#doneBtn .processBtn-control-blank").addClass("processBtn-control-blank-actived");
        $("#processBtn .processBtn-control-blank").removeClass("processBtn-control-blank-actived");
        $("#processBtn span").addClass("left-control-padding");
        $("#doneBtn span").removeClass("left-control-padding");
    }
}

function showAuditList(showProcessingAudit) {
    if (showProcessingAudit) {
        $.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'auditFile/getProcessingAuditList',
            dataType : 'json',
            success : function(data) {
                showProcessAudits(data);
            }
        });
    } else {
        $.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'auditFile/getDoneAuditList',
            dataType : 'json',
            success : function(data) {
                showDoneAudits(data);
            }
        });
    }
}

function showProcessAudits(data) {
    $("#content-title-status").html("(Processing)");
    $("#dataTable").html("");
    var length = data.processAudits.length;
    $(".audit-file-no-data").html("");
    if (length == 0) {
        $(".audit-file-no-data").html("没有正在盘点的资产文件，请从  <a href='asset/allAssets'>资产列表</a> 选取生成。");
    } else {
        $("#dataTable .audit-process-tr").css("display", "block");
        for (var i = 0; i < length; i++) {
            $("#dataTable").append("<tr class='audit-process-tr process-" + i + "'>" + auditProcessTr + "</tr>");
            $(".process-" + i + " a").text(data.processAudits[i].fileName);
            $(".process-" + i + " a").attr("href", "audit/showAuditDetails?auditFileName=" + data.processAudits[i].fileName);
            if (i % 2 == 0) {
                $(".process-" + i).css("background-color", "white");
            }
            $(".process-" + i + " .process-bar").progressbar({
                value: data.processAudits[i].percentage / 100 * 394,
                max: 394
            });
            $(".process-" + i + " .percentResult").html(data.processAudits[i].percentage + "%");
        }
    }
}

function showDoneAudits(data) {
    $("#content-title-status").html("(Done)");
    $("#dataTable").html("");
    var length = data.doneAudits.length;
    $(".audit-file-no-data").html("");
    if (length == 0) {
        $(".audit-file-no-data").html("没有盘点完成的资产文件。");
    } else {
        $("#dataTable .audit-done-tr").css("display", "block");
        for (var i = 0; i < length; i++) {
            $("#dataTable").append("<tr class='audit-done-tr done-" + i + "'>" + auditDoneTr + "</tr>");
            $(".done-" + i + " a").text(data.doneAudits[i].fileName);
            $(".done-" + i + " a").attr("href", "audit/showAuditDetails?auditFileName=" + data.doneAudits[i].fileName);
            $(".done-" + i + " .done-operation-time").html(data.doneAudits[i].operationTime);
            $(".done-" + i + " .done-operator").html(data.doneAudits[i].operator);
            if (i % 2 == 0) {
                $(".done-" + i).css("background-color", "white");
            }
            $(".done-" + i + " .process-bar").progressbar({
                value: data.doneAudits[i].percentage / 100 * 394,
                max: 394
            });
            $(".done-" + i + " .percentResult").html(data.doneAudits[i].percentage + "%");
        }
    }
}

