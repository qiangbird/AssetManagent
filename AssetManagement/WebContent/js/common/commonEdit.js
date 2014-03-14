function initDropDownMap($selector, sourceMap, isMultiple, displayField, valueField, currentValue, width){
    var temp;
    var currentWidth = 300;
    
    if(width != undefined){
    	currentWidth = width;
    }
    if(isMultiple){
         temp = $selector.magicSuggest({
             width: currentWidth,
             allowFreeEntries:false,
             maxDropHeight: 200,
             resultAsString: true,
             displayField: displayField,
             valueField: valueField
         });
    }else{
        temp = $selector.magicSuggest({
             width: currentWidth,
             allowFreeEntries:false,
             maxSelection: 1,
             maxSelectionRenderer: function(v){
                 return "";
             },
             maxDropHeight: 200,
             resultAsString: true,
             displayField: displayField,
             valueField: valueField
         });
    }
    if(sourceMap != undefined){
        temp.setData(sourceMap);
    }
    if(currentValue != undefined){
        var arrayValue = new Array();
        var tempValue = currentValue.split(",");
        $.each(tempValue,function(index,item){
            arrayValue.push(item);
        });
        temp.setValue(arrayValue);
    }
    return temp;
}
function initDropDownList($selector, sourceList, isMultiple, currentValue, width, freeEntries, isHideTrigger, height){
    var temp;
    var currentWidth = 458;
    var free = false;
    var allowHideTrigger = false;
    var maxHeight = 200;
    
    if(width != undefined){
    	currentWidth = width;
    }
    
    if(freeEntries != undefined){
    	free = freeEntries;
    }
    
    if(isHideTrigger != undefined){
    	allowHideTrigger = isHideTrigger;
    }
    
    if(height != undefined){
    	maxHeight = height;
    }
    
    if(isMultiple){
         temp = $selector.magicSuggest({
             width: currentWidth,
             allowFreeEntries:free,
             maxDropHeight: maxHeight,
             resultAsString: true,
             hideTrigger: allowHideTrigger,
             data: sourceList,
         });
    }else{
        temp = $selector.magicSuggest({
             width: currentWidth,
             allowFreeEntries:free,
             maxSelection: 1,
             maxSelectionRenderer: function(v){
                 return "";
             },
             maxDropHeight: maxHeight,
             resultAsString: true,
             hideTrigger: allowHideTrigger,
             data: sourceList,
         });
    }
    if(currentValue != undefined){
        var arrayValue = new Array();
        var tempValue = currentValue.split(",");
        $.each(tempValue,function(indext,item){
            arrayValue.push(item);
        });
        temp.setValue(arrayValue);
    }
    return temp;
}

$("body").delegate(".validation_fail","click",function(){
     $(this).clearValidationMessage();
});
function disableButton(){
    $buttons = $("#disable_button_div").siblings("div.content_button");
    $("#disable_button_div").css({
        "width": $buttons.width(),
        "height": $buttons.height(),
    })
    .removeClass("enable_button_div")
    .addClass("disable_button_div");
}
function enableButton(){
    $("#disable_button_div").css({
    	"width": 0,
        "height": 0,
    }).addClass("enable_button_div")
    .removeClass("disable_button_div");
}

Array.prototype.del = function() { 
	var a = {}, c = [], l = this.length; 
	for (var i = 0; i < l; i++) { 
		var b = this[i]; 
		var d = (typeof b) + b; 
		if (a[d] === undefined) { 
		c.push(b); 
		a[d] = 1; 
		} 
	} 
	return c; 
	}; 

	function validateValueIsEmpty($ele, value, msg) {
		if (value.trim() == "" || undefined == value) {
			addErrorStyle($ele, msg);
			return false;
		} else {
			removeErrorStyle($ele);
			return true;
		}
	}

	function addErrorStyle($ele, msg) {
		validation = "failed";
		$ele.addClass("input-text-error");
		$ele.poshytip({
			content: msg,
			className: 'tip-green',
			allowTipHover: false
		});
	}

	function removeErrorStyle($ele) {
		$ele.removeClass("input-text-error");
		$ele.poshytip("destroy");
		validation = "success";
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

	function getIndexInArr(Arr, ele) {
		    for ( var i = 0; i < Arr.length; i++) {
		        if (ele == Arr[i]) {
		            return i;
		        }
		    }
		    return -1;
		}
	//compare date
	function dateCompare(startTime, endTime) {
		
		 var startDate = new Date(startTime.replace(/-/g,"/"));  
	     var endDate = new Date(endTime.replace(/-/g,"/"));  
	     var m = (endDate.getTime()-startDate.getTime())/(1000*60*60);  
	     
	     if(0 <= m){
	    	 return true;
	     }else{
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


