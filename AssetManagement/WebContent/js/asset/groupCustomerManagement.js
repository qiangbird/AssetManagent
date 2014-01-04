$(document).ready(function(){
	 customerName=new Array();
	   customerCode=new Array();
	    $.ajax({
	       type : 'GET',
	       contentType : 'application/json',
	       url : 'asset/getCustomerInfo',
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
	    		   width:300, //default set 400
	    		   height:60, //default set 200
	    		   dropdownWidth:150, //下拉列表的宽度，default set 100
	    		   dropdownHeight:100,// 如果设置的下拉列表的高度，插件会自动的出现滚动条
	    		   maxRows:10, //显示的最大结果数
	    		   minChars:1, //当字符数达到minChars的时候，才会自动补全，default set 1
	    		   searchDelay:100, //延迟多少毫秒后，在进行搜索
	    		   tokenDelimiter:',', // 最终获取输入的值是以tokenDelimiter为分割的字符串
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














