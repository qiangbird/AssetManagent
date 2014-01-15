﻿$(document).ready(function(){
//about i18n
	// for warning or confirm prop information
//	function i18nProp(message) {
//		return $.i18n.prop(message);
//	};
	
   function AddI18n(message) {
	      $("#label_" + message).html(msg(message));
   }
   var localeCode = $("#localeCode").val();
   if (localeCode == 'en') {
      i18n = 'en_US';
   } else {
      i18n = 'zh_CN';
   }
   jQuery.i18n.properties({
      name : 'message',
      path : 'i18n/',
      mode : 'map',
      language : i18n,
      callback : function() {
//         msg = jQuery.i18n.prop;
         /*AddI18n('Home');
         AddI18n('CreateAsset');
         AddI18n('CommonProperty');
         AddI18n('AssetId');
         AddI18n('AssetName');
         AddI18n('AssetType');
         AddI18n('AssetBarCode');
         AddI18n('AssetGenerateBarCode');
         AddI18n('AssetOwnerShip');
         AddI18n('AssetUsedBy');
         AddI18n('AssetUser');
         AddI18n('AssetLocation');
         AddI18n('AssetStatus');
         AddI18n('AssetSeriesNo');
         AddI18n('AssetPoNo');
         AddI18n('AssetManufacture');
         AddI18n('AssetPhoto');
         AddI18n('AssetEntity');
         AddI18n('AssetCheckInDate');
         AddI18n('AssetCheckOutDate');
         AddI18n('AssetWarranty');
         AddI18n('AssetVendor');
         AddI18n('AssetMemo');
         AddI18n('AssetMachineDetails');
         AddI18n('AssetMachineSubtype');
         AddI18n('AssetMachineSpeification');
         AddI18n('AssetMachineAddtionalConfig');
         AddI18n('AssetMachineMacAddress');
         AddI18n('AssetMonitorDetails');
         AddI18n('AssetMonitorSize');
         AddI18n('AssetMonitrDetails');
         AddI18n('AssetDeviceDetails');
         AddI18n('AssetDeviceSubtype');
         AddI18n('AssetDeviceConfiguration');
         AddI18n('AssetSoftwareDetails');
         AddI18n('AssetSoftwareVersion');
         AddI18n('AssetSoftwareLicenseKey');
         AddI18n('AssetSoftwareMaxUserNum');
         AddI18n('AssetSoftwareAdditionalInfo');
         AddI18n('AssetNonItAssetDetails');
         AddI18n('AssetNonItAssetDetail');
         AddI18n('AssetSelfDefine');
         AddI18n('BatchCreate');
         AddI18n('Project');
         AddI18n('Keeper');
         AddI18n('CopyAsset');
         AddI18n('EditAsset');
         AddI18n('ViewAsset');
         AddI18n('Edit');
         AddI18n('Copy');
         AddI18n('Delete');
         AddI18n('Cancel');*/
         
         
      }
   });
   
//change language
   $("#goChinese").click(function() {
   $.ajax({
   type : 'GET',
   contentType : 'application/json',
   url : 'home/changeLanguage?newlanguage=zh',
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
   url : 'home/changeLanguage?newlanguage=en',
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
	   
   //upload image
   $("#file").change(
	   function() {
	   $("#uploadForm").ajaxSubmit(
	   {
	       type : 'post',
	       url : 'asset/uploadFile',
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
   $("#batchCreate").click(
		   function() {
		   if ($("#batchCreate").is(":checked")) {
		   $("#showBatch").removeClass().addClass("showBatchNormal");
		   $("#showBatch").show();
		   } else {
		       $("#showBatch").removeClass().addClass("showBatchNormal");
		   $("#showBatch").val("1").hide();
		   }
		   });
   $("#showBatch").click(function() {
	    $("#showBatch").removeClass().addClass("showBatchHovered");
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
		   
   //submit and validate
 $("#submitForm").click(function() {
  var name = $("#assetName").val();
  var type = $("#assetType").val();
  var ownership = $("#ownership").val();
  var customerName = $("#customerName").val();
  var selectedLocation = $("#selectedLocation").val();
  var selectedStatus = $("#selectedStatus").val();
  var selectedEntity = $("#selectedEntity").val();
  var machineType = $("#machineType").val();
  var maxUseNum = $("#maxUseNum").val();
  var user = $("#assetUser").val();
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
     $("#assetName").addClass("l-text-error");
     $("#assetName").unbind("click");
     flag = 3;
  }
  if (ownership == "") {
      $("#ownership").addClass("l-select-error");
      flag = 6;
   }
  if (customerName == "") {
     $("#customerName").addClass("l-select-error");
     flag = 7;
  }
  if (selectedLocation == "") {
     $("#selectedLocation").addClass("l-select-error");
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
  if(selectedStatus=="AVAILABLE"&&user!=""){
	  $("#assetUser").empty();
  }
  if(user!=""&&selectedStatus!="IN_USE"){
	  $("#selectedStatus").val("IN_USE");
  }
  
  if (flag != 0) {
     return false;
  } else {
     $("#assetFrom").submit();
         return true;
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
	    console.log(Arr);
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
    $(id).addClass("l-text").removeClass("l-text-hover l-text-error");
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
