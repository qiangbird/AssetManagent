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