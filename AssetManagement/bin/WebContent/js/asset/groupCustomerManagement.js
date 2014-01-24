$(document).ready(function(){
	 customerName=new Array();
	   customerCode=new Array();
	    $.ajax({
	       type : 'GET',
	       contentType : 'application/json',
	       url : 'base/getCustomerInfo',
	       dataType : 'json',
	       success : function(data) {
	    	   console.log(data);
	    	   customer = [];
	    	   for(i=0;i<data.customerList.length;i++){
	    		   customerName[i]=data.customerList[i].customerName;
	    		   customerCode[i]=data.customerList[i].customerCode;
	    		   customer.push({
	    			   value:data.customerList[i].customerCode,
	    			   label: data.customerList[i].customerName,
//	    			   value:data.customerList[i].customerName+ "#" +data.customerList[i].customerCodes
	    		   });
	    	   }
	    	   $("#customerName").autoComplete({
	    		   source:customer,
	    		   width:300, 
	    		   height:60, 
	    		   dropdownWidth:150, 
	    		   dropdownHeight:100,
	    		   maxRows:10, 
	    		   minChars:1, 
	    		   searchDelay:100, 
	    		   tokenDelimiter:',', 
	    		   extraParams:customerCode
	    		 });
            }
            });
	
	
	$("#saveButton").click(function(){
		alert($("#customerName").val());
	});

	
	
	
	
	$("#addButton").click(function(){
		codes = new Array();
		customerCodes="";
		codes = $("#customerName").val().split(",");
		for(i=0;i<codes.length;i++){
			if(checkInArr(customerCode,codes[i])){
//				$().
				customerCodes+=codes[i]+",";
			}
		}
		alert(customerCodes);
	});
	
	
	
	
	 function checkInArr(Arr, ele) {
	      console.log(Arr);
	      for ( var i = 0; i < Arr.length; i++) {
	         if (ele == Arr[i]) {
	            return true;
	         }
	      }
	      return false;
	   }
	
	
	
});














