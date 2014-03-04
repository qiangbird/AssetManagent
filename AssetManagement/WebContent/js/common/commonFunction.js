
function handleException(data) {
	var isSuccess = true;
	var errorCode = data.errorCode;
	if(errorCode!=undefined && errorCode!="") {
		isSuccess = false;
		language = $('#i18nLan').val();
		$("#dialog_publish p").text(data.errorMessage);
		$('#dialog_publish').dialog( {
	        autoOpen : true, 
	        width : 350, 
	        modal : true, 
	        resizable : false,
	        draggable : false, 
	        title : msg.prop("Errordialog"),
	        buttons : [ {
				text : msg.prop("Sure"),
				id : 'submitPublish',
				click : function() {
	        	$(this).dialog('close');
			}
			} ]
	    });
	}
	return isSuccess;
}

function showTipMessage(element,message,offsetX,offsetY,time) {
	var count = 0;
	$(".tip-green").each(function(){
		count=count+1;
		if(count!=0) {
			$(this).remove();
		}
	});
	
	$(element).poshytip({
		content: message,
		showOn: 'none',
		alignTo: 'target',
		alignX: 'inner-right',
		alignY: 'inner-bottom',
		offsetX:offsetX,
		offsetY:offsetY,
		className : 'tip-green',
		timeOnScreen:time
	});
	$(element).poshytip("show");
	$(".tip-green").live("mouseover",function(){
		$(element).poshytip('clearTimeouts');
	}).live("mouseleave",function() {
		
		$(element).poshytip('gotoTimeout');
	});
}
