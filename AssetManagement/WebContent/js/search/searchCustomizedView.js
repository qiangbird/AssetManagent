var customizedViews = [];

$(document).ready(function(){
	$("#customizedViewButton").mouseenter(function(){
		$("#viewUl").show();
	}).mouseleave(function(){
		$("#viewUl").hide();
	});
	
	$("#createView").click(function(){
		var categoryType = $("#categoryType").val();
		window.location.href = "customizedView/goToNewCustomizedView?categoryType=" + categoryType;
	});
});

function findUserCustomizedView(){
	var categoryType = $("#categoryType").val();
	
	$.ajax({
		type: "POST",
		dataType : "json",
		url: "customizedView/findCustomizedViewByUserForManu",
		data: {
			categoryType:categoryType
		},
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