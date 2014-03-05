var dataList;
var criteria = {};
var actionFlag;

$(document).ready(function() {
    
    // categoryFlag = 7, it means category is 'location'
    initCriteria(7);
    findDataListInfo("location");
    
    $(".filterDiv input[type='checkBox']").each(function(){
    	if ($(this).val() != "all") {
    		$(this).attr("content", $(this).siblings("label").html());
    	}
    });
    
    $(".filterDiv").filterBox({});
    
    $("#searchButton").click(function() {
        
        setCriteria();
        criteria.pageNum = 1;
        dataList.criteria = criteria;
        dataList.search();
    });
    
    // add keypress event for search feature
    $("#keyword").keydown(function() {
        if(event.keyCode == 13) {
            setCriteria();
            criteria.pageNum = 1;
            dataList.criteria = criteria;
            dataList.search();
        }
    });
    
     // add place holder event for keyword
     removePlaceholderForKeyWord();
     
  // drop down operation for asset list
     $(".operation_assets_list").find("ul").hide();
     $(".dialogBody").hide();
     
     $(".operation_assets_list").mouseover(function() {
         $(this).find("ul").show();
     }).mouseout(function() {
         $(this).find("ul").hide();
     });
     
     
 	sites = [];
 	
 	$("#addButton").click(function(){
 		$("#site").removeClass("site-error");
 		$("#room").removeClass("site-error");
 		actionFlag = 'create';
 		
 		$("#dialog").dialog({
 	         autoOpen:false,
 	         closeOnEscape: true,
 	         draggable: false,
 	         height: 220,
 	         width: 400,
 	         modal: true,
 	         position: "center",
 	         resizable: false,
 	         bgiframe: true,
 	         title: i18nProp("location_create_dialog_title", "")
 	     });
 		$("#dialog").dialog("open");
 		
 		$("#location_id").val("");
 		$("#site").val("");
 		$("#room").val("");
 	});
 	
 	$("#site").click(function(){
 		$("#site").removeClass("site-error");
 		$("#site").poshytip("destroy");
 		
 		  $.ajax({
 			    type : 'GET',
 			    contentType : 'application/json',
 			    url : 'location/getLocationSites',
 			    dataType : 'json',
 			    success : function(data) {
 			    	length = data.siteList.length;
 			    	for(var i = 0;i< length; i++){
 			    		sites[i] = "Augmentum " + data.siteList[i];
 			    	}
 			    	$("#site").autocomplete({
 			            minLength : 0,
 			            source : sites
 			        });
 			     }
 			  });
 	});
 	$("#room").click(function(){
 		$("#room").removeClass("site-error");
 		$("#room").poshytip("destroy");
 	});
 	
 	//edit location
 	$(".dataList").delegate(".editLocationIcon","click",function(){
 		$("#site").removeClass("site-error");
 		$("#room").removeClass("site-error");
 		$("#site").poshytip("destroy");
 		$("#room").poshytip("destroy");
 		actionFlag = 'update';
 		
 		$("#dialog").dialog({
 	         autoOpen:false,
 	         closeOnEscape: true,
 	         draggable: false,
 	         height: 220,
 	         width: 400,
 	         modal: true,
 	         position: "center",
 	         resizable: false,
 	         bgiframe: true,
 	         title: i18nProp("location_edit_dialog_title", "")
 	     });
 		
 		var pk = $(this).parents(".row").find(".dataList-div-checkbox").attr("pk");
 		$.ajax({
 		    type : 'GET',
 		    contentType : 'application/json',
 		    url : 'location/edit/' + pk,
 		    dataType : 'json',
 		    success : function(data) {
 				$("#dialog").dialog("open");
 				
 				$("#location_id").val(data.location.id);
 				$("#site").val(data.location.site);
 				$("#room").val(data.location.room);
 		     }
 		  });
 	});
 	
     //delete location
 	$(".dataList").delegate(".deleteLocationIcon","click",function(){
 		var pk = $(this).parents(".row").find(".dataList-div-checkbox").attr("pk");
 		
 		ShowMsg(i18nProp('operation_confirm_message'),function(yes){
			 if (yes) {
				 $.ajax({
		    		    type : 'DELETE',
		    		    contentType : 'application/json',
		    		    url : 'location/delete/' + pk,
		    		    dataType : 'json',
		    		    data:{
		    		    	 _method: 'DELETE',
		    		    },
		    		    success : function(data) {
		    		    	dataList.search();
		    		     }
		    	});
            }else{
            	return;
            }
		});
 	});
     	
 	//submit
 	$("#submitLocation").click(function(){
 	    flag = 0;
 		inputSite = $("#site").val();
 		inputRoom = $("#room").val();
 		if(inputSite.trim() == ""){
 			$("#site").addClass("site-error");
			$("#site").poshytip({
				className: 'tip-green',
				allowTipHover: true,
				content: i18nProp("location_site_validate_empty", "")
			});
 			flag = 1;
 		}
 		if(inputRoom.trim() == ""){
 			$("#room").addClass("site-error");
			$("#room").poshytip({
				className: 'tip-green',
				allowTipHover: true,
				content: i18nProp("location_room_validate_empty", "")
			});
 			flag = 2;
 		}
 		if(flag==0){
 			checkLocationAndSubmit();
 		}
 	});
});

// init dataList information for search list
var dataListInfo = {
    columns : 
    [{ EN : 'Site', ZH : '地点', sortName : 'site', width : 200, headerId: 1, isMustShow : true },
    { EN : 'Room', ZH : '房间号', sortName : 'room', width : 200, headerId : 11 },
    { EN : 'Operation', ZH : '操作', sortName : 'operation', width : 300, headerId : 12 }],
    criteria : criteria,
    minHeight : 150,
    pageSizes : [10, 20, 30, 50],
    hasCheckbox : true,
    pageItemSize : 5,
    url : 'location/searchLocation',
    updateShowSize : {
        url : 'searchCommon/pageSize/updatePageSize',
        callback : function() {
        }
    },
    contentHandler : function(str) {
        return resultContentHandle(str);
    }
};

// call dataList
function searchList() {
    dataList = $(".dataList").DataList(dataListInfo);
    dataList.search();
}

//set Criteria(search conditions) for search feature
function setCriteria() {

    criteria.keyWord = $("#keyword").val();
    
    // set search fields
    var searchFields = "";
    $("#searchFields").find(":checked").each(function() {
        if (searchFields == null || searchFields == "") {
            searchFields = this.value;
        } else {
            searchFields = searchFields + "," + this.value;
        }
    });
    criteria.searchFields = searchFields;
    
    return criteria;
}

// get value according to index sequence
function getIndexInArr(Arr, ele) {
    for ( var i = 0; i < Arr.length; i++) {
       if (ele == Arr[i]) {
          return i;
       }
    }
    return -1;
}

//common method
function checkInArr(Arr, ele) {
    for ( var i = 0; i < Arr.length; i++) {
        if (ele == Arr[i]) {
            return true;
        }
    }
    return false;
}

// check if repeated location 
function checkLocationAndSubmit() {
	var id = $("#location_id").val();
	var site = $("#site").val();
	var room = $("#room").val();
	
	if(!checkInArr(sites, $("#site").val()) && actionFlag == 'update'){
	   $("#site").addClass("site-error");
	   $("#site").poshytip({
		   	className: 'tip-green',
		   	allowTipHover: true,
			content: i18nProp("location_site_validate_invalid", "")
	   });
  	} else {
  		
  		$.ajax({
  			type : 'GET',
  			contentType : 'application/json',
  			url : 'location/checkLocation',
  			dataType : 'json',
  			data: {
  				id: id,
  				site: site,
  				room: room
  			},
  			success : function(data) {
  				
  				if (data.newLocation == null) {
  					$("#dialog").submit();
  				} else {
  					if (data.oldLocation != null) {
  						
  						if (data.oldLocation.site == site
  								&& data.oldLocation.room == room) {
  							$("#dialog").submit();
  						}
  					} 
					$("#room").addClass("site-error");
					$("#room").poshytip({
						className: 'tip-green',
						allowTipHover: true,
						content: i18nProp("location_room_validate_repeated", "")
					});
					return;
  				}
  			}
  		});
  	}
}