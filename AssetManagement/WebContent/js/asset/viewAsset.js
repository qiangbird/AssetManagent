$(document).ready(function() {  
   
   $("#editBtn").click(function(){
	   window.location.href="asset/edit/"+$("#id").val();
   });
   $("#copyBtn").click(function(){
	   window.location.href="asset/copy/"+$("#id").val();
   });
   $("#deleteBtn").click(function(){
	   window.location.href="asset/delete/"+$("#id").val();
   });
   $("#cancelBtn").click(function(){
	   javascript:history.go(-1);
   }); 
   
   if("true" == $("#fixed").val()){
		$("#isFixed").css("display", "block");
		$("#nonFixed").css("display", "none");
	}else{
		$("#nonFixed").css("display", "block");
		$("#isFixed").css("display", "none");
	}
});
