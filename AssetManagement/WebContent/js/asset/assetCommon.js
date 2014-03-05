$(document).ready(function(){
	//below is about front page validation
	$("#assetName,#ownership,#customerName,#assetUser,#maxUseNum,#selectedLocation").delegate(this,"blur",function(){
		//delete the tips when input data exists
		if( "" != $(this).val()){
		$(this).clearValidationMessage();
		}
	});
	 window.entityType = "Asset";
	
   var localeCode = $("#localeCode").val();
   if (localeCode == 'en') {
      i18n = 'en_US';
   } else {
      i18n = 'zh_CN';
   }
   
//change language
   $("#goChinese").click(function() {
   $.ajax({
   type : 'GET',
   contentType : 'application/json',
   url : 'changeLanguage?newlanguage=zh',
   dataType : 'json',
   success : function(data) {
       console.log(data);
       location.reload();
   }
       });
   });

   $("#goEnglish").click(function() {
   $.ajax({
   type : 'GET',
   contentType : 'application/json',
   url : 'changeLanguage?newlanguage=en',
   dataType : 'json',
   success : function(data) {
       console.log(data);
       location.reload();
   }
       });
   });
   
//about datepicker
   $(".l-date").datepicker({
	      changeMonth : true,
	      changeYear : true,
	      dateFormat : "yy-mm-dd"
		   });
   
   $(".showAsSelfDefine").find(".asset-input-panel").each(function(){
	  $(this).find(".selfPropertyName").each(function(){
		  var length;
		  if ($(this).html().charCodeAt(0) > 255) {
			  length = 10;
		  } else {
			  length = 20;
		  }
		  if ($(this).html().length > length) {
			  $(this).poshytip({
				  className: 'tip-green',
				  allowTipHover: true,
				  content: $(this).html()
			  });
		  }
	   });
   });
   
   $(".fixedCheckBox").click(function(){
   	if("radioCheckOff" == $(this).find("a").attr("class")){
   		$(this).parents(".radioBoxes").find(".radioCheckOn").attr("class","radioCheckOff");
   		$(this).find("a").attr("class","radioCheckOn");
   		$("#fixed").val($(this).find("a").attr("id"));
   	}
   });
	   
   //upload image
   $("#file").change(
	   function() {
	   $("#uploadForm").ajaxSubmit(
	   {
	       type : 'post',
	       url : 'upload/image',
	       data : $("#uploadForm")
	               .formSerialize(),
	       success : function(data) {
	           console.log(data);
	           $("#aphoto").attr("src", data);
	           $("#photoPath").val(data);
	           return false;
	       },
	       error : function(data,
	           XmlHttpRequest, textStatus,
	           errorThrown) {
	           console.log(data);
	           return false;
	               }
	       });
	   });
   
   //Compare check-in and check-out time
   $("#checkedOutTime").change(function() {
		if ($("#checkedInTime").val() != "") {
		   checkIn = $("#checkedInTime").val();
		   checkOut = $(this).val();
		   if (!dateCompare(checkOut, checkIn)) {
		       $("#checkedOutTime").addClass("l-date-error").removeClass("l-date");
		   } else {
		       $(this).removeClass("l-date-error").addClass("l-date");
		          }
		     }
		});
	
  //Batch create
   $(".batchCheckBoxOff").click(function(){
	   if("batchCheckBoxOff" == $(this).attr("class")){
			$(this).removeClass("batchCheckBoxOff");
			$(this).addClass("batchCheckBoxOn");
		}else{
			$(this).removeClass("batchCheckBoxOn");
			$(this).addClass("batchCheckBoxOff");
		}
	   $("#showBatch").removeClass().addClass("showBatchHovered");
	   if ("batchCheckBoxOn" == $("#batchCreate").attr("class")) {
		   $("#showBatch").removeClass().addClass("showBatchNormal");
		   batchNumber = $("#batchNumber").val();
		   if( batchNumber != ""){
			   $("#showBatch").val(batchNumber);
		   }
		   $("#showBatch").show();
	   } else {
	       $("#showBatch").removeClass().addClass("showBatchNormal");
	       $("#showBatch").val("1").hide();
	   }
   });
   $(".visibleCheckBoxOff").click(function(){
	   if("visibleCheckBoxOff" == $(this).attr("class")){
			$(this).removeClass("visibleCheckBoxOff");
			$(this).addClass("visibleCheckBoxOn");
			
			$("#visible").attr("value",true);
		}else{
			$(this).removeClass("visibleCheckBoxOn");
			$(this).addClass("visibleCheckBoxOff");
			
			$("#visible").attr("value",false);
		}
   });
	$("#showBatch").blur(function() {
	    if ($(this).val() == ""|| !numberCheck($(this).val().trim())) {
	$("#showBatch").removeClass("showBatchNormal showBatchHovered").addClass("showBatchError");
	} else {
	    $("#showBatch").removeClass().addClass("showBatchNormal");
	}
	});

   //show as type
   currentType = $("#assetType").val();
   if (currentType.trim() == 'MACHINE') {
       $("#machineDetails").show().siblings().hide();
    } else if (currentType.trim() == 'MONITOR') {
       $("#monitorDetails").show().siblings().hide();
    } else if (currentType.trim() == 'DEVICE') {
       $("#deviceDetails").show().siblings().hide();
    } else if (currentType.trim() == 'SOFTWARE') {
       $("#softwareDetails").show().siblings().hide();
    } else if (currentType.trim() == "OTHERASSETS") {
       $("#otherAssetsDetails").show().siblings().hide();
    }
// below is about location
   $("#selectedLocation").click(function(){
   	currentSite = "Augmentum "+$("#selectedSite").val();
   	 $.ajax({
   		    type : 'GET',
   		    contentType : 'application/json',
   		    url : 'location/getLocationRoom?currentSite='+currentSite,
   		    dataType : 'json',
   		    success : function(data) {
   		        console.log(data);
   		        rooms = [];
   		        length = data.locationRoomList.length;
   		        for ( var i = 0; i < length; i++) {
   		        	rooms[i] = data.locationRoomList[i];
    	        }
   		     
   		        $("#selectedLocation").autocomplete({
   		            source : data.locationRoomList
   		        });
   		        
   		    }
   });
   });
   //submit and validate
 $("#submitForm").click(function() {
	 
	 
	 if ("success" == validateAssetForm()) {
	       	disableButton();
	           var routinePath = null;
	           var rotationFormVo = getRotationFormVo(rotationType);

	           if ("Rotation" === rotationType){
	               routinePath = "rotationRequest/rotation";
	           } else if ("Assignment" === rotationType) {
	               routinePath = "rotationRequest/assignment";
	           } else if ("Rotation Out" === rotationType) {
	               routinePath = "rotationRequest/rotationOut";
	           }
	           
	           $.ajax({
	               url : path + routinePath,
	               dataType : 'json',
	               data : rotationFormVo,
	               type : 'POST',
	               success : function(data){
	                  window.location.href = path;
	                  enableButton();
	               },
	               error : function(data){
	                  var j = data.responseJSON.error;
	                  var placeholderMessage = data.responseJSON.placeholderMessage;
	                  
	                  //Garrett modified.
	                  if(j.errorCode != undefined && j.errorCode != ""){
	                      $("#errorCode").val(j.errorCode);
	                      var params = new Array();
	                      if(placeholderMessage != undefined && placeholderMessage != ""){
	                          params = placeholderMessage.split(",");
	                      }
	                      switchLanguage(j.errorCode,params);
	                  } else{
	                      window.location.href = $("#basePath").val()+"serverError";
	                  }
	                  
	                  enableButton();
	              }, 
	              complete : function(){
	           	   enableButton();
	              }
	           });
	       }
	 
 $("#selectedLocation").blur(
    function() {
    if ($(this).val().trim() == "") {
        TextMouseOutError(this);
    } else {
        if (!checkInArr(rooms,$(this).val())) {
        	console.log(rooms);
            TextMouseOutError(this);
        } else {
            TextMouseOutNormal(this);
        }
    }
    });
	 
  var name = $("#assetName").val();
  var type = $("#assetType").val();
  var ownership = $("#ownership").val();
  var customerName = $("#customerName").val();
  var room = $("#selectedLocation").val();
  var selectedSite = $("#selectedSite").val();
  var selectedStatus = $("#selectedStatus").val();
  var selectedEntity = $("#selectedEntity").val();
  var machineType = $("#machineType").val();
//  var maxUseNum = $("#maxUseNum").val();
  var user = $("#assetUser").val();
  var checkedInTime = $("#checkedInTime").val();
  var checkedOutTime = $("#checkedOutTime").val();
  var flag = 0;
  if (type == "") {
     $("#assetType").addClass("l-select-error");
     flag = 1;
  } else if (type == "machine"&& machineType == "") {
     $("#machineType").addClass("l-select-error");
     flag = 1;
  } /*else if (type == "software") {
     if (maxUseNum == ""|| !numberCheck(maxUseNum)||maxUseNum=="0") {
        $("#maxUseNum").addClass("l-select-error");
        flag = 1;
     }
  }*/
  if (name == "") {
     $("#assetName").addClass("l-text-error");
     $("#assetName").unbind("click");
     flag = 3;
  }
  if (ownership == "") {
      $("#ownership").addClass("l-select-error");
      flag = 6;
   }else{
	   try{
   if(!checkInArr(custName, ownership)){
	   $("#ownership").addClass("l-select-error");
	   flag = 6;
   }
	   }catch (e) {
		flag = 0;
	}
   }
  if (customerName == "") {
     $("#customerName").addClass("l-select-error");
     flag = 7;
  }
  if (selectedSite == "") {
     $("#selectedSite").addClass("l-select-error");
     flag = 8;
  }
  if (selectedStatus == "") {
     $("#selectedStatus").addClass("l-select-error");
     flag = 9;
  } else {
	    if (selectedStatus == "IN_USE"&& $("#assetUser").val() == "") {
	        $("#assetUser").addClass("l-text-error");
	        flag = 9;
	    }
	}
  if (selectedEntity == "") {
     $("#selectedEntity").addClass("l-select-error");
     flag = 13;
  }
  if (!(($("#showBatch").is(":visible") && numberCheck($(
        "#showBatch").val().trim())) || ($("#showBatch").is(":hidden")))) {
	  $("#showBatch").addClass("showBatchError");
     flag = 16;
  }
  if ($("#assetUser").val() != "") {
	 try{
	    console.log(employeeName);
     if (!checkInArr(employeeName, $("#assetUser").val())) {
    	$("#assetUser").addClass("l-text-error");
        flag = 17;
     }
	   } 
  	catch(err) 
  	   { 
  		//because you did not click user, so employeeName will be undefined, this means user is unchange
  		if(flag==0){
  	   flag=0;
  		}
  	   } 
  }
  
 //relationship about status and user
  if(selectedStatus=="IN_USE"&&user==""){
	$("#assetUser").addClass("input-text-error");
    flag = 18;
  }
  if("" != checkedInTime && "" != checkedOutTime){
	  if(dateCompare(checkedInTime, checkedOutTime)){
		  flag = 19;
	  }
  }
  if(selectedStatus=="AVAILABLE"&&user!=""){
	  $("#assetUser").empty();
  }
  if(user!=""&&selectedStatus!="IN_USE"){
	  $("#selectedStatus").val("IN_USE");
  }
  
  if (room == ""){
	  $("#selectedLocation").addClass("l-text-error");
	     flag = 20;
  }else{
	  try{
		  if(!checkInArr(rooms, room)){
			  $("#selectedLocation").addClass("l-text-error");
	    	     flag = 20;
		  }
	  }catch (e) {
		  if(flag==0){
		  flag = 0;
		  }
	}
  }
// flag = 0;
  if (flag == 0) {
	  if($("#assetUser").val()==""){
		  $("#selectedStatus").val("AVAILABLE");
	  }
	  $("#assetFrom").ajaxSubmit(
			   {
			       type : 'post',
			       url : $("#action").val(),
			       data : $("#assetFrom")
			               .formSerialize(),
			       success : function(data) {
			    	   console.log(data==null);
			    	   console.log(data=="");
			    	   console.log(data.error==undefined);
			    	   if(data.error != undefined){
			    	   var errorCode = data.error.toString();
			    	    console.log(errorCode);
			    	   console.log(typeof(errorCode));
			           showMessageBarForMessage(errorCode);
			           return false;
			    	   }else{
			    		   if($("#showBatch").val()!=1){
			    		   window.location.href="asset/allAssets?tips=Batch create "+$("#showBatch").val()+" items asset success!";
			    		   }else{
			    			   window.location.href="asset/allAssets?tips=Create asset "+$("#assetId").val()+" success!";
			    		   }
			    		   
			    		   
			    	   }
			       }
			       });
	  
	      }
  
   });
   

$("#assetName,#seriesNo,#barCode,#poNo,#manufacturer,#monitorVendor," +
   		"#maxUseNum,#assetUser").click(function() {
		TextMouseEnter(this);
		});
$("#seriesNo,#barCode,#poNo,#manufacturer,#monitorVendor").blur(function() {
			TextMouseOutNormal(this);
		});
});

//common method
function checkInArr(Arr, ele) {
	    for ( var i = 0; i < Arr.length; i++) {
	        if (ele == Arr[i]) {
	            return true;
	        }
	    }
	    return false;
	}

function getIndexInArr(Arr, ele) {
	    for ( var i = 0; i < Arr.length; i++) {
	        if (ele == Arr[i]) {
	            return i;
	        }
	    }
	    return -1;
	}
//compare date
function dateCompare(d1, d2) {
    var da1 = d1.split("-");
if ("" == d2) {
    d2 = GetNowTime();
}
var da2 = d2.split("-");

    var dy1 = da1[0];
    var dy2 = da2[0];
    var dm1 = da1[1];
    var dm2 = da2[1];
    var dd1 = da1[2];
    var dd2 = da2[2];

    if (dy1 > dy2) {
        return true;
    } else if (dy1 == dy2) {
        if (dm1 > dm2) {
            return true;
        } else if (dm1 == dm2) {
            if (dd1 > dd2) {
                return true;
            } else if (dd1 == dd2) {
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    } else {
        return false;
    }
}
//number check
function numberCheck(num) {
    var n = /^([12]\d{4}|\d{0,4})$/; // 0~20000
    if (!n.test(num)) {
        return false;
    } else {
        return true;
    }
}
//common validate effect
function TextMouseEnter(id) {
    $(id).addClass("l-text-hover").removeClass("l-text l-text-error");
}
function TextMouseOutNormal(id) {
    $(id).addClass("l-text").removeClass("l-text-hover l-text-error input-text-error");
}
function TextMouseOutError(id) {
    $(id).addClass("l-text-error").removeClass("l-text-hover l-textr");
}

function DropdownMouseEnter(id) {
    $(id).addClass("l-select-hover").removeClass("l-select-normal l-select-error");
}
function DropdownMouseOutNormal(id) {
    $(id).addClass("l-select-normal").removeClass("l-select-hover l-select-error");
}
function DropdownMouseOutError(id) {
    $(id).addClass("l-select-error").removeClass("l-select-normal l-select-hover");
}
