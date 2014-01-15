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
	
	var i18n = $("#locale").val();
	jQuery.i18n.properties({
		name : 'jqueryi18n',
		path : 'i18n/',
		mode : 'map',
		language : i18n,
		callback : function() {
		}
	});
	
	$("#dialog-confirm").dialog({
		autoOpen : false,
		resizable : false,
		height : 170,
		width : 390,
		draggable: false,
		position : ["center",130],
		modal : true,
		title : i18nProp('operation_confirm'),
		buttons : [{
			text : i18nProp('yes'),
			click : function() {
					$(this).dialog("close");
					mDialogCallback(true);
				}
			},{
			text: i18nProp('no'),
			click:function() {
					$(this).dialog("close");
					mDialogCallback(false);
				}
			}
		]
	});
	//alert dialog
	$('#dialog-warning').dialog({
		autoOpen : false,
		height : 170,
		width : 390,
		modal : true,
		title : i18nProp('operation_warning'),
		draggable: false,
		resizable : false,
		position : ["center",130],
		buttons : [{
			text : $.i18n.prop('ok'),
			click : function() {
				$(this).dialog("close");
			}
		}]
	});
  });

function i18nProp(message, line) {
	if(line == "" || line == null){
		return $.i18n.prop(message);
	}else{
		if (line.lastIndexOf(",") != -1) {
			return $.i18n.prop(message, line.substring(0, line.length - 1));
		} else {
			return $.i18n.prop(message, line);
		}
	}
}

