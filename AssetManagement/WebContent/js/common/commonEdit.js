function initDropDownMap($selector, sourceMap, isMultiple, displayField, valueField, currentValue){
    var temp;
    if(isMultiple){
         temp = $selector.magicSuggest({
             width: 300,
             allowFreeEntries:false,
             maxDropHeight: 200,
             resultAsString: true,
             displayField: displayField,
             valueField: valueField
         });
    }else{
        temp = $selector.magicSuggest({
             width: 300,
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
function initDropDownList($selector, sourceList, isMultiple, currentValue, width){
    var temp;
    var currentWidth = 458;
    if(width != undefined){
    	currentWidth = width;
    }
    if(isMultiple){
         temp = $selector.magicSuggest({
             width: currentWidth,
             allowFreeEntries:false,
             maxDropHeight: 200,
             resultAsString: true,
             data: sourceList,
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

