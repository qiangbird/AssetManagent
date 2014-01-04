var customizedViews = [];

$(document).ready(function(){
	
	findUserCustomizedView();
	
	$("#customizedViewButton").mouseenter(function(){
		$("#viewUl").show();
	}).mouseleave(function(){
		$("#viewUl").hide();
	});
});

function findUserCustomizedView(){
	$.ajax({
		type: "POST",
		contentType: "application/json",
		dataType : "json",
		url: "customizedView/findCustomizedViewByUserForManu",
		success: function(data){
			customizedViews = data;
			
			if(0 < customizedViews.length){
				showCustomizedViewMenuList(customizedViews);
			}
		}
	});
}

function showCustomizedViewMenuList(customizedViews){
	for(var i = 0; i < customizedViews.length; i++){
		var appendLi = $("#viewUlTemplate").find(".existCustomizedView");
		appendLi.attr("id",customizedViews[i].customizedViewId);
		appendLi.text(customizedViews[i].viewName);
		$("#viewUl").append($("#viewUlTemplate").html());
	}
	
}