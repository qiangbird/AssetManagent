$(document).ready(function() {
/*	$("#exportIconAll").click(function() {
        var fileName = $("#fielLabel").text();
        location.href="exportAuditFile.action?auditFileName="+fileName;
    });*/
	refreshData();
	$(".viewInventoryAsset").hide();
 });

function showDataListDialog() {
	
	$("#viewMoreDetails").dialog({
		autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
		width : 974, // set dialog's width
		modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
		resizable : false, // set false, dialog's size can't change.
		draggable : false, // set false, dialog can't move.
		title : $("#viewMoreDialog_title").val(),
		resizable:false,
		draggable:true,
		position : ['center',60],
		height:620,
		close: function() {
			location = location;
		}
	});
	
	$("#viewMoreDetails").dialog("open");
}


function refreshData(){
	var auditFileName=$("#fileName").val();
	var percentNum=$("#percentage").val();
	
	loadStatusBar(percentNum);
	
	jQuery.getJSON("audit/getUnAuditedAssetsCount", {auditFileName: auditFileName},
            function(inventoryCount) {
                $(".u").find("strong").text("(" + inventoryCount + ")");
            }
	);
	jQuery.getJSON("audit/getAuditedAssetsCount", {auditFileName: auditFileName},
            function(inventoryCount) {
                $(".a").find("strong").text("(" + inventoryCount + ")");
            }
	);
	jQuery.getJSON("inconsistent/getInconsistentAssetsCount", {auditFileName: auditFileName},
            function(inventoryCount) {
			$("#iconsSize").text("(" + inventoryCount + ")");
     }
	);
	showAudited("#aa");  
	showIncons(auditFileName);

}

function showIncons(auditFileName){
	$("#inconsTableContent").children().remove();
	AddInconsTable();
	
	$("#iconsView").bind("click", function(){
    	showDataListDialog();
       	$("#viewMoreDetails").load("audit/inventoryAsset?flag=inconsistent" + "&auditFileName=" + auditFileName);
    });
	
    $("#inconsTable").dataTable({
        "bServerSide":true,
    	"bProcessing":true,
    	"bScrollInfinite": true,
    	"sScrollY":"360px",
    	"bScrollCollapse":true,
    	"bFilter":false,
    	"iDisplayLength":20,
    	"bSort":false,
    	"oLanguage":{
	    	"sEmptyTable":"No data availabe in table",
			"sProcessing":"DataTables is currrently loading...",
			"sInfo": "Showing _START_  to _END_  of _TOTAL_ entries",
			"sInfoEmpty": "No entries to show"
    	},
    	"aoColumns":[
                        { 
                        "sWidth":"50px"
                         },
                        { 
                        "sWidth":"150px"
                         },
                        {
                         "sWidth":"150px"
                         },
                        {
                         "sWidth":"100px"
                         },
                    ],
                    
    	 "fnRowCallback": function(){
    		$(".dataTables_scrollBody").css("overflow-x","hidden");
    	},
    	
    	"sAjaxSource":"inconsistent/findInconsistentAssets?auditFileName="+auditFileName
    });
/*    $("#iconsView").bind("click", function(){
    	$("#viewMoreDetails").dialog('open');
    	$("#viewMoreDetails").load("viewInconsistent?flag=incons&fileName="+auditFileName);
    });*/
}
function showAudited(a) {
    var auditFileName = $(a).parents(".dialogBody").find(".process-panel").find("label").text();
	$("#tableContent").children().remove();
    AddTable("audit");
    var aLink = $("#auditFilePanel").find(".a");
    aLink.find("a").css("color", "#555555").css("text-decoration", "none");
    var uLink = $("#auditFilePanel").find(".u");
    uLink.find("a").css("color", "#23A5E4").css("text-decoration", "underline");
   
    $("#auditView").unbind("click");
    $("#auditView").bind("click", function(){
    	showDataListDialog();
       	$("#viewMoreDetails").load("audit/inventoryAsset?flag=audited" + "&auditFileName=" + auditFileName);
    });
    
    $("#auditTable").dataTable({
        "bServerSide":true,
    	"bProcessing":true,
    	"bScrollInfinite": true,
    	"sScrollY":"360px",
    	"bScrollCollapse":true,
    	"bFilter":false,
    	"bSort":false,
    	"oLanguage":{
/*	    	"sEmptyTable":msg.prop("sEmptyTable"),
			"sProcessing":msg.prop("sProcessing"),
			"sInfo": msg.prop("sInfo"),
			"sInfoEmpty": msg.prop("sInfoEmpty")*/
    		"sEmptyTable":"No data availabe in table",
			"sProcessing":"DataTables is currrently loading...",
			"sInfo": "Showing _START_  to _END_  of _TOTAL_ entries",
			"sInfoEmpty": "No entries to show"
    	},
    	"aoColumns":[
                   { 
                   "sWidth":"50px"
                    },
                   { 
                   "sWidth":"150px"
                    },
                   {
                    "sWidth":"150px"
                    },
                   {
                    "sWidth":"100px"
                    },
               ],
                    
    	 "fnRowCallback": function(){
    		$(".dataTables_scrollBody").css("overflow-x","hidden");
    	},
         "bJQueryUI":false,
    	"iDisplayLength":20,
    	"sAjaxSource":"audit/findAuditedAssets?auditFileName="+auditFileName
    });
}

//show unaudited file
function showUnAudited(a) {
	$("#tableContent").children().remove();
    AddTable("unAudit");
    var uLink = $("#auditFilePanel").find(".u");
    uLink.find("a").css("color", "#555555").css("text-decoration", "none");
    var aLink = $("#auditFilePanel").find(".a");
    aLink.find("a").css("color", "#23A5E4").css("text-decoration", "underline");
    var auditFileName = $(a).parents(".dialogBody").find(".process-panel").find("label").text();
    var fileName = $("#fielLabel").text().replace(/\s/ig,'');
    
    $("#auditView").unbind("click");
    $("#auditView").bind("click", function(){
    	showDataListDialog();
       	$("#viewMoreDetails").load("audit/inventoryAsset?flag=unaudited" + "&auditFileName=" + auditFileName);
    });
    
    $("#unauditTable").dataTable({
        "bServerSide":true,
    	"bProcessing":true,
    	"bScrollInfinite": true,
    	"sScrollY":"360px",
    	"bScrollCollapse":true,
    	"bFilter":false,
    	"bSort":false,
    	"iDisplayLength":20,
    	"oLanguage":{
/*	    	"sEmptyTable":msg.prop("sEmptyTable"),
			"sProcessing":msg.prop("sProcessing"),
			"sInfo": msg.prop("sInfo"),
			"sInfoEmpty": msg.prop("sInfoEmpty")*/
    		"sEmptyTable":"No data availabe in table",
			"sProcessing":"DataTables is currrently loading...",
			"sInfo": "Showing _START_  to _END_  of _TOTAL_ entries",
			"sInfoEmpty": "No entries to show"
    	},
	   	"aoColumns":[
	                  { 
	                  "sWidth":"50px"
	                   },
	                  { 
	                  "sWidth":"150px"
	                   },
	                  {
	                   "sWidth":"150px"
	                   },
	                  {
	                   "sWidth":"100px"
	                   },
	              ],
                    
    	 "fnRowCallback": function(){
    		$(".dataTables_scrollBody").css("overflow-x","hidden");
    	},
    	"sAjaxSource":"audit/findUnAuditedAssets?auditFileName="+auditFileName
    });
    
}

function loadStatusBar(percentNum) {
	$(".process-bar").progressbar({
        value: percentNum / 100 * 394,
        max: 394
    });
	$(".percentResult").html(percentNum + "%");
}

function AddTable(flagTable){
	var contentPanel=$("#tableContent");
	contentPanel.append("<table cellspacing='0' frame='void' class='table' id='unauditTable')></table>");
	var table=contentPanel.find("table");
	if(flagTable==="audit"){
		table.attr("id","auditTable");
	}
	
	else if(flagTable==="unAudit"){
		table.attr("id","unauditTable");
	}
	table.append($("#tableTitleTemplate").html());
	table.append("<tbody ></tbody>");
}
function AddInconsTable(){

	var contentPanel=$("#inconsTableContent");
	contentPanel.append("<table cellspacing='0' frame='void' class='table' id='unauditTable')></table>");
	var table=contentPanel.find("table");
	table.attr("id","inconsTable");
	table.append($("#tableTitleTemplate").html());
	table.append("<tbody ></tbody>");
}

//up to done
function upToDone(object) {
	ShowMsg("Are you sure to done this file?",function(yes){
	      if (yes) {
	    	  var auditFileName=$("#fileName").val();
	    	  var url = "audit/showAuditDetails?auditFileName=" + auditFileName;
	    	  upToDoneImpl(auditFileName, url);
	      }else{
	          return;
	      }
	    });
}

function removeAuditFile(object) {
	ShowMsg("Are you sure to remove this file?",function(yes){
	      if (yes) {
	    	  var auditFileName=$("#fileName").val();
	    	  var url = "auditFile/inventoryList";
	    	  removeAuditFileImpl(auditFileName, url);
	      }else{
	          return;
	      }
	    });
}

function refreshInventoryDetailsData(data){
	percentNum = data;
    $("#percentage").val(percentNum);
    refreshData();
}
