$(document).ready(function() {
   $(".eidtLink").click(function() {
	   var id = $(this).parents(".filterInfo").find("#id").val();
	   var href = "customizedView/getCustomizedViewDetail?customizedViewId=" + id;
	   window.location.href =  href;
   });
   
   $(".deleteLink").click(function() {
	   var id = $(this).parents(".filterInfo").find("#id").val();
	   var urlPath = "customizedView/deleteCustomizedView?customizedViewId=" + id;
	   $.ajax( {  
		    type : 'POST',  
		    contentType : 'application/json',  
		    url : urlPath,  
		    dataType : 'json',  
		    success : function(data) { 
		    	}
	   });
	   $(this).parents(".filterInfo").remove();
   });
   
   $("#newView").click(function() {
	   var href = "customizedView/goToNewCustomizedView";
	   window.location.href =  href;
   });
   
   $("#cancel").click(function(){
   	   window.history.back();
   });
});

	