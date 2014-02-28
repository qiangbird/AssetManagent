var dataList;
var criteria = {};

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
 	$("#dialog").dialog({
         autoOpen:false,
         closeOnEscape: true,
         draggable: false,
         height: 220,
         width: 400,
         show: "blind",
         hide: "blind",
         modal: true,
         position: "center",
         resizable: false,
         title: i18nProp("Location_Management"),
         bgiframe: true
     });
 	
 	$("#addButton").click(function(){
 		$("#site").removeClass("site-error");
 		$("#room").removeClass("site-error");
 		$("#dialog").dialog("open");
 		$("#location_id").val("");
 		$("#site").val("");
 		$("#room").val("");
 	});
 	
 	$("#sites").DropDownList({
 	    multiple : false,
 	    header : false,
 	    noneSelectedText : 'Select site',
 	});
 	
 	$("#site").click(function(){
 		$("#site").removeClass("site-error");
 		  $.ajax({
 			    type : 'GET',
 			    contentType : 'application/json',
 			    url : 'location/getLocationSites',
 			    dataType : 'json',
 			    success : function(data) {
 			    	console.log(data.siteList);
 			    	length = data.siteList.length;
 			    	for(var i = 0;i< length; i++){
 			    		sites[i] = data.siteList[i].siteNameEn.replace(",","");
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
 	});
 	//edit group
 	$(".dataList").delegate(".editLocationIcon","click",function(){
 		$("#site").removeClass("site-error");
 		$("#room").removeClass("site-error");
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
     //delete group
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
     		if(inputSite.trim()==""){
     			$("#site").addClass("site-error");
     			flag = 1;
     		}
     		if(inputRoom.trim()==""){
     			$("#room").addClass("site-error");
     			flag = 2;
     		}
     		if(flag==0){
     		if(checkInArr(sites,$("#site").val())){
     			$("#dialog").submit();
     		}else{
     			if(sites==""){
     				$("#dialog").submit();
     			}else{
     			$("#site").addClass("site-error");
     			return;
     			}
     		}
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
    updateShowField : {
        url : 'searchCommon/column/updateColumns',
        callback : function(data) {
            $.ajax({
                type : "POST",
                contentType : "application/json",
                url : "searchCommon/column/getColumns?category=location",
                dataType : "json",
                success : function(data) {
                    dataList.opts.columns = data.columns;
                    dataList.setShow(data.showFields);
                    dataList.search();
                }
            });
        }
    },
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