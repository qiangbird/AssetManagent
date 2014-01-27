$(document).ready(function(){
	
	// get returned asset panel
	$.ajax({
		type : 'GET',
        contentType : 'application/json',
        dataType : 'json',
        url: "todo/viewReturnedAssetPanel",
        success: function(data){
        	var todoList = data.todoList;
        	for (var i = 0; i < todoList.length; i++) {
        		$(".returnedAssetPanel table").append("<tr><td><input class='returnedAssetId' type='checkbox' value=" 
        				+ todoList[i].id + " /></td><td>" + todoList[i].assetName + "</td>" + "<td>" 
        				+ todoList[i].projectName + "</td>" + "<td>" + todoList[i].returnedTime 
        				+ "</td></tr>");
        	}
        }
	});
	
	$("#returnedAsset").click(function(){
		$.ajax({
            type : 'GET',
            contentType : 'application/json',
            url : 'todo/confirmReturnedAsset',
            dataType : 'json',
            data: {
                ids: getActivedAssetIds(".returnedAssetId:checked"),
            },
            success : function(){
            	location = location;
            }
        });
	});
	
});

function getActivedAssetIds(className) {
    var assetIds = [];
    $(className).each(function(){
        assetIds.push(($(this).val()));
    });
    return assetIds.toString();
}