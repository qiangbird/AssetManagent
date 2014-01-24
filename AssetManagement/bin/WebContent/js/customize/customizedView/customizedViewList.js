$(document).ready(function() {
   $(".eidtLink").click(function() {
	   var id = $(this).parents(".filterInfo").find("#id").val();
	   var href = "customizedView/getCustomizedViewDetail?customizedViewId=" + id;
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
	   var href = "customizedView/goToNewCustomizedView";
	   window.location.href =  href;
   });
   
   $("#cancel").click(function(){
   	   window.history.back();
   });
});

	