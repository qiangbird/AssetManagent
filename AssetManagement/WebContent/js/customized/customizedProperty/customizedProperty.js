var customers = [];
var customerNames = [];
var customerCodes = [];
var assetTypes = [];
var properties = [];
var propertiesFromServer = [];
var propertyItems = [];
var properties = [];

$(document).ready(function() {
	
	getCustomerAndAssetType();
	
	$("#cancel").click(function(){
//		checkCustomerNameAndAssetType();
		window.history.back();
	});
	$("#save").click(function(){
		initSaveOperation();
	});
	$("#addProperty").click(function(){
		initCreateProperty();
		if("block" == $(".showProperty").css("display")){
			$(".showProperty").css("display","none");
		}else{
			$(".showProperty").css("display","block");
		}
		$(".sortable").css("display","block");
	});
	$(".rowDiv").delegate("#submitProperty, #editProperty","click", function(){
		if("edit" == $(this).val()){
			$("#submitProperty").show();
			$("#editProperty").hide();
		}
		createOrUpdateProperty();
	});
	$("#cancleProperty").click(function(){
		$(".showProperty").css("display","none");
	});
	$(".propertyTypeLi").click(function(){
		$("#selectedType").val($(this).attr("id"));
		$(".propertyTypeLi").removeClass("propertyTypeLiSelect");
		$(this).addClass("propertyTypeLiSelect");
		$(".rightClass").find(".rowDiv").css("display","none");
		$(".rightClass").find("." + $(this).attr("id")).css("display","block");
	});
    $(".radioBoxes").delegate(".radioCheckOff, .radioCheckOn", "click", function(){
    	if("radioCheckOff" == $(this).attr("class")){
    		$(this).parents(".radioBoxes").find(".radioCheckOn").attr("class","radioCheckOff");
    		$(this).attr("class","radioCheckOn");
    		$("#propertyRequired").val($(this).attr("id"));
    	}
    });
    $("#addItem").click(function(){
    	var propertyPropertyItem = $("#propertyPropertyItem").val();
    	if ("failed" == $("#propertyPropertyItem").push($("#propertyPropertyItem")
    			.validateNull(propertyPropertyItem,i18nProp('property_error_chineseNameNull')))) {
    		return;
    	} 
    	$(".addSelectItem").append($("#itemTemplate").html());
    	$("#propertyPropertyItem").val("");
    	$(".itemContent:last").find("#itemValue").val(propertyPropertyItem);
    	$(".itemContent:last").css("display", "block");
    	propertyItems.push(propertyPropertyItem);
    });
    $(".addSelectItem").delegate(".deleteItem", "click", function (){
    	var object = this;
    	ShowMsg(i18nProp('message_confirm_customizedView_delete_property'),function(yes){
		      if (yes) {
		    	  var index = $(object).parents(".itemContent").index();
		      	  propertyItems.splice(index, 1);
		      	  $(object).parent().remove();
		      }else{
		          return;
		      }
		});
    });
    $(".properties").delegate(".editProperty", "click", function (index){
    	$("#submitProperty").hide();
    	$("#editProperty").show();
    	doEditOpertion(this);
    });
    $(".properties").delegate(".deleteProperty", "click", function (index){
    	var object = this;
    	ShowMsg(i18nProp('message_confirm_customizedView_delete_property'),function(yes){
		      if (yes) {
		    	    var id = $(object).parents(".dragDiv").find("input:hidden[id='id']").val();
			      	var isNew = $(object).parents(".dragDiv").find("input:hidden[id='isNew']").val();
			      	if("no" == isNew){
			      		for(var i = 0; i< properties.length; i++){
			      			if(id == properties[i].id){
			      				properties[i].isDelete = true;
			      			}else{
			      				properties[i].isDelete = false;
			      			}
			      		}
			      	}else{
			      		for(var i = 0; i< properties.length; i++){
			      			if(id == properties[i].id){
			      				properties.splice(i, 1);
			      			}else{
			      				properties.splice(i, 1);
			      			}
			      		}
			      	}
			      	$(object).parents(".dragDiv").remove();
		      }else{
		          return;
		      }
		});
    });
    $("#sortableLeft, #sortableRight").sortable({
	    tolerance : "pointer",
	    handler : "li",
	    cursor : 'move',
	    placeholder: "sortable-placeholder",
	    connectWith : ".sortable",
	    revert : true,
	    start : function(event, ui) {
		    $(ui.item).css("border", "2px solid #fecf00");
	    },
	    stop : function(event, ui) {
		    $(ui.item).css("border", "1px solid #d3d3d3");
	    }
	});
	// add datepicker for each l-date class
	$(".createDate").datepicker({
	    changeMonth : true,
	    changeYear : true,
	    dateFormat : "yy-mm-dd"
	});
	$("#propertyZhName,#propertyEnName").delegate(this,"blur",function(){
		//delete the tips when input data exists
		if("" != $(this).val()){
			$(this).clearValidationMessage();
		}
	});
});

function doEditOpertion(object){
	$("#addPropertyTitle").text("Edit Property");
	var id = $(object).parents(".dragDiv").find("#id").val();
	$("#editId").val(id);
	
	for(var i = 0; i < properties.length; i++){
		if(id == properties[i].id){
			initEditedProperty(properties[i]);
		}
	}
}

function initRadioBoxes(){
	$("#true").removeClass("radioCheckOff radioCheckOn");
	$("#false").removeClass("radioCheckOff radioCheckOn");
}

function createOrUpdateProperty() {
	
	var enName = $("#propertyEnName").val();
	var zhName = $("#propertyZhName").val();
	var isCreateProperty = true;
	
	if("failed" == nullZnAndEnName(zhName, enName)){
		return;
	}

	if("" != $("#editId").val()){
		isCreateProperty = false;
		for ( var i = 0; i < properties.length; i++) { //edit
			if (properties[i].id == $("#editId").val()) {
				properties[i].enName = $("#propertyEnName").val();
				properties[i].zhName = $("#propertyZhName").val();
				properties[i].propertyType = $("#selectedType").val();
				properties[i].required = $("#propertyRequired").val();
				properties[i].propertyDescription = $("#propertyDescription").val();
				setObjectValue(properties[i], $("#selectedType").val());

				$(".properties").find("#" + properties[i].position).children(" li").each(function(index) {
					var id = $(this).find("input:hidden[id='id']").val();
					var propertyText = $(this).find(".propertyText");
					if(id == $("#editId").val()){
						propertyText.text(properties[i].enName);
						setPropertyValueInSortTable($(this), properties[i]);
					}
				});
				break;
			}
		}
	}
	if (isCreateProperty) { //create
		var property = new Object;
		property.id = Math.uuid();
		property.isDelete = false;
		property.isNew = true;
		property.propertyType = $("#selectedType").val();
		property.required = $("#propertyRequired").val();
		dicidePosition(property);
		setObjectValue(property, property.propertyType);
		property.enName = enName;
		property.zhName = zhName;
		property.propertyDescription = $("#propertyDescription").val();
		properties.push(property);
		var propertyArray = new Array();
		propertyArray.push(property);
		showPropertyValueOnSide(propertyArray);
	}
	initCreateProperty();
}

function nullZnAndEnName(zhName, enName){
	var validations = new Array();
    var validation = "success";
    
    validations.push($("#propertyZhName")
			.validateNull(zhName,i18nProp('property_error_chineseNameNull')));
    validations.push($("#propertyEnName")
    		.validateNull(enName,i18nProp('property_error_englishNameNull')));
    
    validation = recordFailInfo(validations);
    
    return validation;
}

function setObjectValue(object, type){
	if("inputType" == type){
		object.value = $("#propertyInputValue").val();
		return;
	}
	if("selectType" == type){
		object.value = new Array();
		deepCopyArray(object.value, propertyItems);
		return;
	}
	if("dateType" == type){
		object.value = $("#propertyDateTypeValue").val();
		return;
	}
	if("textAreaType" == type){
		object.value = $("#propertyTextAreaTypevalue").val();
		return;
	}
}

function initEditedProperty(property){
	setEditedPropertyNameAndTypeInEditPage(property);
	setEditedPropertyRequiredInEditPage(property);
	$("#propertyDescription").val(property.propertyDescription);
	$(".rightClass").find(".rowDiv").css("display","none");
	$(".rightClass").find("." + property.propertyType).css("display","block");
	setEditedPropertyValueInEditPage(property);
}

function setEditedPropertyNameAndTypeInEditPage(property){
	$(".showProperty").css("display","block");
	$(".propertyTypeLi").removeClass("propertyTypeLiSelect");
	$(".propertyType").find("#" + property.propertyType)
	.addClass("propertyTypeLiSelect");
	$("#selectedType").val(property.propertyType);
	$("#propertyZhName").val(property.zhName);
	$("#propertyEnName").val(property.enName);
}

function setEditedPropertyRequiredInEditPage(property){
	if("false" == property.required){
		initRadioBoxes();
		$("#true").addClass("radioCheckOff");
		$("#false").addClass("radioCheckOn");
		$("#propertyRequired").val("false");
	}else{
		initRadioBoxes();
		$("#true").addClass("radioCheckOn");
		$("#false").addClass("radioCheckOff");
		$("#propertyRequired").val("true");
	}
}

function setEditedPropertyValueInEditPage(property){
	if("inputType" == property.propertyType){
		$("#propertyInputValue").val(property.value);
		return;
	}
	if("selectType" == property.propertyType){
		var value = new Array();
		deepCopyArray(value, property.value);
		propertyItems.length = 0;
		$(".addSelectItem").find(".itemContent").remove();
		for(var i = 0; i < value.length; i++){
			$(".addSelectItem").append($("#itemTemplate").html());
	    	$(".itemContent:last").find("#itemValue").val(value[i]);
	    	$(".itemContent:last").css("display", "block");
	    	propertyItems.push(value[i]);
		}
		return;
	}
	if("dateType" == property.propertyType){
		$("#propertyDateTypeValue").val(property.value);
		return;
	}
	if("textAreaType" == property.propertyType){
		$("#propertyTextAreaTypevalue").val(property.value);
		return;
	}
}

function setPropertyValueInSortTable(parent, object){
	
	var required = parent.find(".propertyRequired");
	
	if("false" == object.required){
		required.hide();
	}else{
		required.show();
	}
	parent.find(".inputText").val("").css("display", "none");
	parent.find(".inputSelectType").val("").css("display", "none")
	.find(".select-panel").remove();
	parent.find(".inputDate").val("").css("display", "none");
	parent.find(".inputTextarea").val("").css("display", "none");
	
	if("inputType" == object.propertyType){
		var inputText = parent.find(".inputText");
		inputText.val(object.value);
		inputText.css("display", "block");
		return;
	}
	if("selectType" == object.propertyType){
		var inputSelect = parent.find(".inputSelectType");
		inputSelect.val(object.value[0]);
		inputSelect.css("display", "block");
		if(true == object.isNew){
			changeToDropDownList(object.position, object.value, null);
		}else{
			inputSelect.next().remove();
			changeToDropDownList(object.position, object.value, inputSelect);
		}
		return;
	}
	if("dateType" == object.propertyType){
		var inputText = parent.find(".inputDate");
		inputText.val(object.value);
		inputText.css("display", "block");
		
		if("propertyTime" == inputText.attr("id")){
			//Generate a unique id to bind the datepicker event,
			//otherwise the new generate element to bind the 
			//datepicker can't work well.
			var date = new Date().getTime();
			inputText.attr("id", "propertyTime" + date);
			
			$("#propertyTime" + date).datepicker({
				changeMonth : true,
				changeYear : true,
				dateFormat : "yy-mm-dd"
			});
		}
		return;
	}
	if("textAreaType" == object.propertyType){
		var inputTextarea = parent.find(".inputTextarea");
		inputTextarea.val(object.value);
		inputTextarea.css("display", "block");
		return;
	}
} 

function dicidePosition(property){ 
		var leftLength = 0;
		var rightLength = 0;
		$(".properties").find("#sortableLeft").children("li").each(function(){
			leftLength = checkTextArea(this, leftLength);
		});
		$(".properties").find("#sortableRight").children("li").each(function(){
			rightLength =checkTextArea(this, rightLength);
		});
		if(parseInt(leftLength) > parseInt(rightLength)){
			property.position = "sortableRight";
		}else{
			property.position = "sortableLeft";
		}
} 

function checkTextArea(object, length){
	if("block" == $(object).find(".inputTextarea").css("display")){
		length = length + 2;
	}else{
		length++;
	}
	return length;
}

function showPropertyValueOnSide(propertyArray){
	var len = propertyArray.length;
	for(var i = 0; i < len; i++){
		if(true == propertyArray[i].isNew){
			$("#sortableTemplate").find("#isNew").val("yes");
		}else{
			$("#sortableTemplate").find("#isNew").val("no");
		}
		$("#sortableTemplate").find("#id").val(propertyArray[i].id);
		$("#sortableTemplate").find(".propertyText").text(propertyArray[i].enName);
		var lastSortTable = null;
		
		if(propertyArray[i].position == "sortableRight"){
			$("#sortableRight").append($("#sortableTemplate").find(".sortable").html());
			lastSortTable = $("#sortableRight").find(".dragDiv:last");
		}else{
			$("#sortableLeft").append($("#sortableTemplate").find(".sortable").html());
			lastSortTable = $("#sortableLeft").find(".dragDiv:last");
		}
		setPropertyValueInSortTable(lastSortTable, propertyArray[i]);
	}
}

function initCreateProperty() {
	$("#editId").val("");
	$("#addPropertyTitle").text("Add Property");
	$("#selectedType").val("inputType");
	$(".propertyTypeLi").removeClass("propertyTypeLiSelect");
	$("#inputType").addClass("propertyTypeLiSelect");
	$("#propertyZhName").val("");
	$("#propertyEnName").val("");
	initRadioBoxes();
	$("#true").addClass("radioCheckOn");
	$("#false").addClass("radioCheckOff");
	$("#propertyRequired").val("true");
	$("#propertyDescription").val("");
	$("#propertyInputValue").val("");
	$(".addSelectItem").find(".itemContent").remove();
	propertyItems = [];
	$("#propertyDateTypeValue").val("");
	$("#propertyTextAreaTypevalue").val("");
	$(".rightClass").find(".rowDiv").css("display","none");
	$(".rightClass").find(".inputType").css("display","block");
}

function changeToDropDownList(position, itemsList, existInputSelectType){
	var inputSelectType = null;
	if(null != existInputSelectType){
		inputSelectType = existInputSelectType;
	}else{
		inputSelectType = $(".properties").find("#" + position).find(".inputSelectType:last");
	}
    var inputSelectTypeMS = initDropDownList(inputSelectType, itemsList, false, undefined, 284);
    inputSelectType.data("inputSelectType",inputSelectTypeMS);
}

function getCustomerAndAssetType(){
	$.ajax({
        type : 'GET',
        contentType : 'application/json',
        url : 'self/getCustomerAndAssetType',
        dataType : 'json',
        success : function(data) {
        	var assetTypeMS = initDropDownList($("#assetType"), data.assetTypes, false, undefined, 286);
        	$("#assetType").data("assetType",assetTypeMS);
        	
            var customerMs = initDropDownMap($("#customerCode"), data.customers, false, "label", "value");
            $("#customerCode").data("customerCode",customerMs);
            
            assetTypeBindEvent();
            customerBindEvent();
         }
      });
}

function assetTypeBindEvent(){
	var assetTypeMS = $("#assetType").data("assetType");
	$(assetTypeMS).on('selectionchange', function(event, combo, selection){
		doAssetTypeValidation();
		checkCustomerNameAndAssetType();
    });
}

function customerBindEvent(){
	var customerMs = $("#customerCode").data("customerCode");
	$(customerMs).on('selectionchange', function(event, combo, selection){
		var selectedItems = $("#customerCode").data("customerCode").getSelectedItems();
		
		if(0 < selectedItems.length){
			var customerName = selectedItems[0].label;
			$("#customerName").val(customerName);
		}else{
			$("#customerName").val("");
		}
		doCustomerNameValidation();
		checkCustomerNameAndAssetType();
    });
}

function checkCustomerNameAndAssetType(){
	var customerCode = $("#customerCode").data("customerCode").getValue().join();
	var assetType = $("#assetType").data("assetType").getValue().join();
	
    if("" != customerCode && "" != assetType){
    	getPropertyTemplates(assetType);
    }
}

function initSaveOperation(){
	var createProperties = new Array();
	deepCopyArray(createProperties,properties);
	
	if(!doValidation()){
		return;
	}
	$(".properties").find(".sortable").children("li").each(function(index) {
		var id = $(this).find("input:hidden[id='id']").val();
		var position = $(this).parent("ul").attr("id");
		for ( var i = 0; i < createProperties.length; i++) {
			if (createProperties[i].id == id) {
				createProperties[i].sequence = index;
				createProperties[i].position = position;
				if (createProperties[i].propertyType == 'selectType') {
					createProperties[i].value = createProperties[i].value.join("#");
				}
				break;
			}
		}
	});
	saveProperties(createProperties);
}

function doValidation(){
	var assetTypeNotNull = doAssetTypeValidation();
	var customerNameNotNull = doCustomerNameValidation();
	
    if(assetTypeNotNull && customerNameNotNull){
    	return true;
    }else{
    	return false;
    }
}

function doAssetTypeValidation(){
	var assetType = $("#assetType").data("assetType").getValue().join();
	return validateValueIsEmpty($("#assetType"), assetType, i18nProp('message_warn_assetType_is_null', null));
}

function doCustomerNameValidation(){
	var customerName = $("#customerName").val();
	return validateValueIsEmpty($("#customerCode"), customerName, i18nProp('message_warn_customerName_is_null', null));
}

function saveProperties(properties){
	var customerCode = $("#customerCode").data("customerCode").getValue().join();
	var assetType = $("#assetType").data("assetType").getValue().join();
	$("#div-loader").show();
	$.ajax( {  
	    type : 'POST',  
	    contentType: 'application/x-www-form-urlencoded',
	    url : 'self/saveSelfProperty',  
	    data : {
	    	"selfProperties":JSON.stringify(properties),
	    	"customerCode":customerCode,
	    	"assetType":assetType
	    	},
	    success : function() { 
	    	$("#div-loader").hide();
	    	getCustomerAndAssetType();
	    	showMessageBarForMessage("message_customizedView_save_success");
	    }
	});
}

function getPropertyTemplates(assetType){
	var assetType = $("#assetType").data("assetType").getValue().join();
	var customerCode = $("#customerCode").data("customerCode").getValue().join();
	$.ajax( {  
	    type : 'POST',  
	    contentType : 'application/json',  
	    url : 'self/getPropertyTemplates?customerCode=' + customerCode + '&assetType=' + assetType,  
	    dataType : 'json',  
	    success : function(data) { 
	    	properties = data.propertyTemplates;
	    	$("#properties").val(properties);
	    	var len = properties.length;
	    	$(".properties").find("#sortableLeft").find("li").remove();
	    	$(".properties").find("#sortableRight").find("li").remove();
	    	
	    	if(0 < len){
	    		for(var i = 0; i < len; i++){
	    			properties[i].isDelete = false;
	    			properties[i].isNew = false;
	    			if(true == properties[i].required){
	    				properties[i].required = "true";
	    			}else{
	    				properties[i].required = "false";
	    			}
	    		}
	    		showPropertyValueOnSide(properties);
	    		$(".properties").find(".sortable").css("display", "block");
	    	}else{
	    		$(".properties").find(".sortable").css("display", "none");
	    	}
	    }
	});
}

function getType(o)
{
    var _t;
    return ((_t = typeof(o)) == "object" ? o==null && "null" || Object.prototype.toString.call(o).slice(8,-1):_t).toLowerCase();
}
function deepCopyArray(destination,source)
{
    for(var p in source)
    {
        if(getType(source[p])=="array"||getType(source[p])=="object")
        {
            destination[p]=getType(source[p])=="array"?[]:{};
            arguments.callee(destination[p],source[p]);
        }
        else
        {
            destination[p]=source[p];
        }
    }
}