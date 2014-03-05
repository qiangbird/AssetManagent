var allAssetPage = "allAssetPage";
var customerAssetPage = "customerAssetPage";
var transferLogPage = "transferLogPage";
var operationLogPage ="operationLogPage";

$(document).ready(function() {
	
	var prePage = $("#prePage").val();
    if(allAssetPage == prePage){
    	$(".allAssetPage").show();
    }else if(customerAssetPage == prePage){
    	$(".customerAssetPage").show();
    }else if(transferLogPage == prePage){
    	$(".transferLogPage").show();
    }else if(operationLogPage == prePage){
    	$(".operationLogPage").show();
    }
    
   $(".eidtLink").click(function() {
	   var id = $(this).parents(".filterInfo").find("#id").val();
	   var prePage = $("#prePage").val();
	   var href = "customizedView/getCustomizedViewDetail?customizedViewId=" + id
	   			+ "&prePage=" + prePage;
	   window.location.href =  href;
   });
   
   $(".deletePosition").click(function() {
	   var object = this;
	   ShowMsg(i18nProp('message_confirm_customizedView_delete_view'),function(yes){
		      if (yes) {
		    	   var id = $(object).parents(".filterInfo").find("#id").val();
			   	   var urlPath = "customizedView/deleteCustomizedView?customizedViewId=" + id;
			   	   $.ajax( {  
			   		    type : 'POST',  
			   		    contentType : 'application/json',  
			   		    url : urlPath,  
			   		    dataType : 'json',  
			   		    success : function(data) { 
			   		    	}
			   	   });
			   	   $(object).parents(".filterInfo").remove();
		      }else{
		          return;
		      }
		    });
   });
   
   $("#newView").click(function() {
	   var categoryType = $("#categoryType").val();
	   var prePage = $("#prePage").val();
	   var href = "customizedView/goToNewCustomizedView?categoryType=" + categoryType
	   		+ "&prePage=" + prePage;
	   window.location.href =  href;
   });
   
   $("#cancel").click(function(){
   	   window.history.back();
   });
});

	