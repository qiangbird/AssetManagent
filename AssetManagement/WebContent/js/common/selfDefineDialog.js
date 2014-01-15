//call confirm and alter dialog
var mDialogCallback;
function ShowMsg(msg, callback) {
	if (callback == null) {
		$('#warning-message-body').html(msg);
		$('#dialog-warning').dialog('open');
	} else {
		mDialogCallback = callback;
		$('#confirm-message-body').html(msg);
		$('#dialog-confirm').dialog('open');
	}
};

$(function() {
	//confirm  dialog
	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		height : 170,
		width : 390,
		draggable: false,
		position : ["center",130],
		modal : true,
		title : $.i18n.prop('Operation_Confirm'),
		buttons : {
			"Yes" : function() {
				$(this).dialog("close");
				mDialogCallback(true);
			},
			"No" : function() {
				$(this).dialog("close");
				mDialogCallback(false);
			}
		}
	});
	//alert dialog
	$('#dialog-warning').dialog({
		autoOpen : false,
		height : 170,
		width : 390,
		modal : true,
		title : $.i18n.prop('Operation_Warning'),
		draggable: false,
		resizable : false,
		position : ["center",130],
		buttons : {
			"OK" : function() {
				$(this).dialog("close");
			}
		}
	});
  });

function i18nProp(message,line) {
	if(line==""||line==null){
		return $.i18n.prop(message);
	}else{
		return $.i18n.prop(message,line.substr(0,line.length-1));
	}
}

$(function(){
	
    var i18n = $("#language").val();
    jQuery.i18n.properties({
       name : 'message',
       path : 'i18n/',
       mode : 'map',
       language : i18n,
       callback : function() {
       }
    });
	
});