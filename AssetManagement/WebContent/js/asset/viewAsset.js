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
   
   
});
