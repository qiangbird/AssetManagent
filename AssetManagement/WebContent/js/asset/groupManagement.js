$(document).ready(function(){
	$(".groupItem").mouseover(function(){
		$(this).css("background", "#23A5E3");
	});
	$("#dialog").dialog({
        autoOpen:false,
        closeOnEscape: true,
        draggable: false,
        height: 280,
        width: 500,
        show: "blind",
        hide: "blind",
        modal: true,
        position: "center",
        resizable: false,
        title: "Group operation",
        bgiframe: true
    });
	
    $("#addButton").click(function(){
//    	$("#groupId").empty();
        $("#dialog").dialog("open");
        $("#groupId").val("");
        $("#groupName").val("");
        $("#description").val("");

    });
    //edit group
    $(".editIcon").each(function(i){
    	$(this).click(function(){
    		var groupId = $(this).attr("id").trim();
    		  $.ajax({
    		       type : 'get',
    		       contentType : 'application/json',
    		       url : 'group/edit?id='+ groupId,
    		       dataType : 'json',
    		       success : function(data) {
    		    	   $("#dialog").dialog("open");
    		    	   $("#groupId").val(data.customerGroup.id);
                      $("#groupName").val(data.customerGroup.groupName);
              	      $("#description").val(data.customerGroup.description);
              	      $("#processType").val(data.customerGroup.processType);
    		          
    		       },
    		       error : function() {
    		          alert("error");
    		       }
    		    });
    	  });
    });
    //delete group
    $(".removeIcon").each(function(i){
    	$(this).click(function(){
    		var groupId = $(this).attr("id").trim();
    		$(this).parent().parent().remove();
    		console.log(groupId);
    		  $.ajax({
    		       type : 'get',
    		       contentType : 'application/json',
    		       url : 'group/delete?id='+ groupId,
    		       dataType : 'json',
    		       success : function(data) {
    		       },
    		       error : function() {
    		          alert("error");
    		       }
    		    });
    	  });
    });
    
    $(".groupName").each(function(){
    	$(this).click(function(){
    		groupId = $(this).parent().attr("id");
    		window.location.href = "group/manageGroupCustomer?id="+groupId;
    	});
    });
 });















