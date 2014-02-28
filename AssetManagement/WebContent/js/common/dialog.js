$(function() {
	// delete btn dialog
	$('#dialog_assign').dialog( {
		autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
		width : 600, // set dialog's width
		modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
		resizable : false, // set false, dialog's size can't change.
		draggable : false, // set false, dialog can't move.
//		title : msg.prop("assignAssets"),Assign Asset
		title : "Assign Asset",
		minHeight:283,
		height:335
	});
	$('#dialog_employeeAssign').dialog( {
		autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
		width : 600, // set dialog's width
		modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
		resizable : false, // set false, dialog's size can't change.
		draggable : false, // set false, dialog can't move.
//		title : msg.prop("assignAssets"),
		title : "Assign Asset",
		minHeight:250,
		height:250
	});
	$('#dialog_generate').dialog( {
		autoOpen : false,
		width : 400,
		modal : true,
		resizable : false,
		draggable : false,
//		title : msg.prop("Confirm"),CONFIRM
		title : "CONFIRM",
		buttons : [ {
//			text : msg.prop("yes"),yes
			text : "YES",
			id : 'generateButton',
			click : function() {
			// here you can add your callback
			$(this).dialog('close');
		}
		}, {
//			text : msg.prop("no"),
			text : "NO",
			id : 'grayButton',
			click : function() {
				$(this).dialog('close');
			}
		} ]
	});
	$('#generate_btn').click(function() {
		$('#dialog_generate').dialog('open');
		return false;
	});
	
	
	//save role confirm bar
	
	$('#cancel_Btn').click(function() {
		$('#dialog_cancel').dialog('open');
		return false;
	});
	
	$('#dialog_statusWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text : "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
    $('#dialog_selectOneWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
    
    
	$('#dialog_selectWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	$('#dialog_barCodeWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	$('#dialog_removeBarCodeWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	$('#dialog_noSelect').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	$('#dialog_noMatch').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	
	$('#dialog_FixedAssetsStatusWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text :msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	
	$('#dialog_returnAssetsWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
    
    $('#dialog_returnAssetsToProjectWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
 
    $('#dialog_returnAssetsToProjectWarning_owner').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
       
	$('#dialog_takeOverWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
    
    $('#dialog_takeOverOwnerWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
        
	 $('#dialog_ownershipWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
	 $('#dialog_managerAssgin_Warning').dialog( {
	        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
	        width : 350, // set dialog's width
	        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
	        resizable : false, // set false, dialog's size can't change.
	        draggable : false, // set false, dialog can't move.
//	        title : msg.prop("Warning"),
	        title : "WARNING",
	        buttons : [ {
//	            text : msg.prop("okButton"),
	        	text: "OK",
	            id : 'warningButton',
	            click : function() {
	                // here you can add your callback
	                $(this).dialog('close');
	                return false;
	            }
	        } ]
	    });
	 
	 	$('#dialog_employeeAssgin_Warning').dialog( {
	        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
	        width : 350, // set dialog's width
	        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
	        resizable : false, // set false, dialog's size can't change.
	        draggable : false, // set false, dialog can't move.
//	        title : msg.prop("Warning"),
	        title : "WARNING",
	        buttons : [ {
//	            text : msg.prop("okButton"),
	        	text: "OK",
	            id : 'warningButton',
	            click : function() {
	                // here you can add your callback
	                $(this).dialog('close');
	                return false;
	            }
	        } ]
	    });

	 	$('#dialog_employeeAssgin_Warning_status').dialog( {
	        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
	        width : 350, // set dialog's width
	        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
	        resizable : false, // set false, dialog's size can't change.
	        draggable : false, // set false, dialog can't move.
//	        title : msg.prop("Warning"),
	        title : "WARNING",
	        buttons : [ {
//	            text : msg.prop("okButton"),
	        	text: "OK",
	            id : 'warningButton',
	            click : function() {
	                // here you can add your callback
	                $(this).dialog('close');
	                return false;
	            }
	        } ]
	    });
	    	    
	 $("#viewMoreDetails").dialog({
	        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
	        width : 974, // set dialog's width
	        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
	        resizable : false, // set false, dialog's size can't change.
	        draggable : false, // set false, dialog can't move.
//	        title : msg.prop("assetDetails"),
	        title : "Assets Details",
	        //position:[180,140],
	        resizable:false,
	        draggable:true,
	        height:600,
	        position:'center'
	        
	        	
	 });
        
		$('#dialog_permissionsWarning').dialog( {
        autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
        width : 350, // set dialog's width
        modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
        resizable : false, // set false, dialog's size can't change.
        draggable : false, // set false, dialog can't move.
//        title : msg.prop('Warning'),
        title : "WARNING",
        buttons : [ {
//            text : msg.prop("okButton"),
        	text: "OK",
            id : 'warningButton',
            click : function() {
                // here you can add your callback
                $(this).dialog('close');
                return false;
            }
        } ]
    });
		
		$('#dialog_createLocation').dialog( {
			autoOpen : false, // set autoOpen false, dialog will stay hidden until.dialog ('open') is called on it.
			width : 600, // set dialog's width
			modal : true, // set true, the dialog will have modal behavior; other items on the page will be disabled.
			resizable : false, // set false, dialog's size can't change.
			draggable : false, // set false, dialog can't move.
//			title : msg.prop("createLocation"),
			title: "Create Location dialog",
			minHeight:283,
			height:335
		});
});
