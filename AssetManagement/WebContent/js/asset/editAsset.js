﻿$(document).ready(function() {
	if("true" == $("#fixed").val()){
		$("#false").attr("class","radioCheckOff");
		$("#true").attr("class","radioCheckOn");
	}else{
		$("#false").attr("class","radioCheckOn");
		$("#true").attr("class","radioCheckOff");
	}
   $("#selectedStatus").DropDownList({
       multiple : false,
       header : false
  });
   $("#ownership").click(
   function() {
      $.ajax({
	   type : 'GET',
	   contentType : 'application/json',
	   url : 'customer/getCustomerInfo',
	   dataType : 'json',
	   success : function(data) {
	      console.log(data.customerList);
	      length = data.customerList.length;
	      console.log(length);
	      custName=[];
	      for ( var i = 0; i < length; i++) {
	    	  custName[i] = data.customerList[i].customerName;
		      }
	      $("#ownership").autocomplete(
              {
                 source : custName
              });
	   }
	});
   });
   
   $("#selectedEntity").DropDownList({
       multiple : false,
       header : false,
       noneSelectedText: 'Select entity'
  });
   $("#selectedSite").DropDownList({
       multiple : false,
       header : false
  });
   $("#machineType").DropDownList({
	    multiple : false,
	    header : false,
	    noneSelectedText : 'Select machine type',
	});
   $(".showAsSelfDefine").delegate(".l-select","click",function(){
	   $(this).datepicker("option", "dateFormat", "ISO 8601 - yy-mm-dd" );
	 });
   var userArray = new Array();

   $(".showElementItems").click(
   function() {
      $("#customerName").val($(this).text().trim());
      DropdownMouseOutNormal("#customerName");
      // get the project
      $.ajax({
       type : 'GET',
       contentType : 'application/json',
       url : 'project/getProjectByCustomer?customerName='+ $(this).text().trim(),
       dataType : 'json',
       success : function(data) {
          console.log(data);
          length = data.projectList.length;
          projectNames = [];
          for ( var i = 0; i < length; i++) {
             projectNames[i] = data.projectList[i].projectCode;
          }
          console.log(projectNames);
       },
       error : function() {
          alert("error");
       }
    });

      $(".showElement").hide();
      if (customerNameStatus) {
         customerNameStatus = false;
      }
      checkCustomerNameAndAssetType();
   });

   $("#assetUser").click(function() {
      $.ajax({
       type : 'GET',
       contentType : 'application/json',
       url : 'user/getEmployeeDataSource',
       dataType : 'json',
       success : function(data) {
          console.log(data);
          length = data.employeeInfo.length;
          employeeName = [];
          employeeValue = [];
          for ( var i = 0; i < length; i++) {
             employeeName[i] = data.employeeInfo[i].label;
             employeeValue[i] = data.employeeInfo[i].value;
          }
          userArray = employeeName;
          $("#assetUser").autocomplete(
          {
             minLength: 0,
             source : employeeName,
             select : function() {
                 $("#assetUser").change(
                  function() {
                      if ($(this).val().trim() == "") {
                      TextMouseOutError(this);
                      }else{
                       if (!checkInArr(userArray, $("#assetUser").val())) {
                           TextMouseOutError(this);
                       }else{
                           TextMouseOutNormal(this);
                       }
                      }
                  }); 
                 $("#assetUser").blur(
                  function() {
                	  TextMouseOutNormal(this);
                      if ($(this).val().trim() == ""&&$("#selectedStatus").val()=="IN_USE") {
                      TextMouseOutError(this);
                      }else{
                       if (!checkInArr(userArray, $("#assetUser").val())) {
                           TextMouseOutError(this);
                       }else{
                           TextMouseOutNormal(this);
                       }
                      }
                  }); 
                 $("#assetUser").focus(function(){
                     TextMouseOutNormal(this);
                 });
              }
          });
          
       }
  });
});
   
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

   $("#deviceSubtypeSelect").click(
   function() {
      $.ajax({
       type : 'GET',
       contentType : 'application/json',
       url : 'deviceSubtype/getAllSubtypes',
       dataType : 'json',
       success : function(data) {
          console.log(data.deviceSubtypeList);
          subtypeNames = [];
          for (i = 0; i < data.deviceSubtypeList.length; i++) {
             subtypeNames[i] = data.deviceSubtypeList[i].subtypeName;
          }
          $("#deviceSubtypeSelect").autocomplete({
             source : subtypeNames
          });
       },
       error : function() {
          alert("error");
       }
    });
   });

   $("#submitForm").click(function() {
      var name = $("#assetName").val();
      var type = $("#assetType").val();
      var ownership = $("#ownership").val();
      var customerName = $("#customerName").val();
      var room = $("#selectedLocation").val();
      var selectedStatus = $("#selectedStatus").val();
      var selectedEntity = $("#selectedEntity").val();
      var machineType = $("#machineType").val();
      var maxUseNum = $("#maxUseNum").val();
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
      } else if (type == "software") {
         if (maxUseNum == ""|| !numberCheck(maxUseNum)) {
            $("#maxUseNum").addClass("l-select-error");
            flag = 1;
         }
      }
      if (name == "") {
         $("#assetName").css("background","url(img/IPX_300x30_Error.png) no-repeat");
         $("#assetName").unbind("click");
         flag = 3;
      }
//      if (ownership == "") {
//          $("#ownership").addClass("l-select-error");
//          flag = 6;
//       }else{
//       if(!checkInArr(custName, ownership)){
//    	   $("#ownership").addClass("l-select-error");
//    	   flag = 6;
//       }
//       }
      if (customerName == "") {
         $("#customerName").addClass("l-select-error");
         flag = 7;
      }
//      if (selectedLocation == "") {
//         $("#selectedLocation").addClass("l-select-error");
//         flag = 8;
//      }
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
      if ($("#assetUser").val() != "") {
    	  try 
    	   { 
    		  if (!checkInArr(employeeName, $("#assetUser").val())) {
    			  $("#assetUser").addClass("input-text-error");   
    			  flag = 17;
    	          }
    	   } 
    	catch(err) 
    	   { 
    		//because you did not click user, so employeeName will be undefined, this means use is unchange
    	   flag=0;
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

      console.log(flag);
      if (flag != 0) {
         return false;
      } else {
  		currentPropertie = new Array();
  		var selfDefinedNames = new Array();
  		var selfDefinedIds = new Array();
  		var selfDefinedValues = new Array();
  	    names="";
  		ids="";
  		values="";
  	//get self-defined names
  		$.each($(".showAsSelfDefine p .selfPropertyName"), function(i, item){
  			selfDefinedNames[i] = $(this).text();
  			names+=$(this).text()+",";
		});
  		$.each($(".showAsSelfDefine p .selfId"), function(i, item){
 	  	     selfDefinedIds[i] = $(this).val();
 	  	  ids+=$(this).val()+",";
 		});
  		//get self-defined values
  		$.each($(".showAsSelfDefine p .selfPropertyVlaue"), function(i, item){
  			if($(this).val()!=""){
	  	   selfDefinedValues[i] = $(this).val();
	  	 values+=$(this).val()+",";
  			};
		});
  		
  		 $.ajax({
  	         type : 'GET',
  	         contentType : 'application/json',
  	         url : 'customizedPropery/updateSelfDefinedProperties',
  	         dataType : 'json',
  	         data:{
  	        	 'assetId':$("#id").val(),
  	        	'selfDefinedNames' : names,
  	    		'selfDefinedIds' : ids,
  	    		'selfDefinedValues' : values
             },
  	         success : function(data) {
  	            console.log(data);
  	         }
  	      });
  		
//         $("#assetFrom").submit();
  		 $("#assetEditFrom").ajaxSubmit(
  			   {
  			       type : 'post',
  			       url : 'asset/update',
  			       data : $("#assetEditFrom")
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
  			    		   window.location.href="asset/allAssets";
  			    	   }
  			       }
  			       });
         return true;
      }
   });
   $("#assetName,#seriesNo,#barCode,#poNo,#manufacturer,#monitorVendor,#maxUseNum,#assetUser").click(function() {
	   TextMouseEnter(this);
	   });
   $("#seriesNo,#barCode,#poNo,#manufacturer,#monitorVendor").blur(function() {
	   TextMouseOutNormal(this);
	   });
   $("#assetName").blur(function() {
      if ($(this).val().trim() == "") {
         TextMouseOutError(this);
      } else {
         TextMouseOutNormal(this);
      }
   });
   
   $("#ownership,#customerName").click(function() {
	    DropdownMouseEnter(this);
	});
   
   $("#ownership").blur(function() {
	   if ($(this).val().trim() == "") {
	       	DropdownMouseOutError(this);
	       }else{
	       	 if (!checkInArr(custName, $("#ownership").val())) {
	         	   DropdownMouseOutError(this);
	            }else{
	              DropdownMouseOutNormal(this);
	            }
	       }
   });

   $("#customerName").blur(function() {
            if ($(this).val().trim() == "") {
                DropdownMouseOutError(this);
            }else{
                 if (!checkInArr(custName, $("#customerName").val())) {
                     DropdownMouseOutError(this);
                 }else{
                   DropdownMouseOutNormal(this);
                 }
            }
         });

   $("#maxUseNum").blur(
         function() {
            if ($(this).val() == ""|| !numberCheck($(this).val().trim())) {
               TextMouseOutError(this);
            } else {
               TextMouseOutNormal(this);
            }
         });
});
$(document).ready(function() {
   $("#assetUser").change(function(){
	   $("#userId").val("");
   });
   $(".dropDownSelect").DropDownList({
       multiple : false,
       header : false
  });
})
