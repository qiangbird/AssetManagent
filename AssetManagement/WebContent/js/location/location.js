$(document).ready(
	function(){
		
		$("#aa").click(function(){
			alert("aa");
		});
		
		/*$("#addButton").click(function(){
			location.href = 'location/new';
		});*/
		$("delelteLocation").click(function(){
			
		});
		
		$(".employeeRoleInfo .items").mouseover(function() {
		      $(this).removeClass().addClass("hightLight");
		   });
		   $(".employeeRoleInfo .items").mouseout(function() {
		      $(this).removeClass().addClass("normalBg");
		   });
});


function deleteRow(locatinId, uri, obj){
    $.ajax( {
        url : uri + locatinId,
        type : "delete",
        processData : false,
        dataType : "json",
        contentType : "application/json; charset=utf-8",
        cache : false,
        success : function(data) {
            $(obj).parent().parent().remove();
        },
        error : function(e) {
            alert("Delete failed!");
        }
    });
}

//function deleteRow(locatinId, uri, obj) {
//	alert("ddddddddd");
//    $.ajax( {
//        url : uri,
//        type : "delete",
//        processData : false,
////        dataType : "json",
////        contentType : "application/json; charset=utf-8",
////        cache : false,
//        success : function(data) {
////            $(obj).parent().parent().remove();
//        },
//        error : function(e) {
//            alert("Delete failed!");
//        }
//    });
//}

//$(document).ready(function() {
//	alert("dddddddd");
//	$(".aa").click(function(){
//	alert("aa");
//});
//})

