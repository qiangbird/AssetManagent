function messageBarForItemId(itemIds, messages) {
	$("#messageBar").messageBar({
        responseMessage : messages,
        // itemId is just useful for execute that have id.
        // such as: create/update/delete
        itemId : itemIds,
        // top is length between the message bar and window top-board.
        top : 100,
        // height of message board
        height : 26,
        // width of message board
        width : 610,
        // isAutoHide used to define whether auto hide in system default time.
        isAutoHide : true,
        // autoHideTime redefine the system default time for hide.
        autoHideTime : "5000",
        // preposition id.
        isPrepositionId : true
    });
}

function showMessageBarForAssetList (errorCodes) {
	var itemIds = [];
	var messages = [];
	for (var i = 0; i < errorCodes.length; i++) {
		$('.row .dataList-checkbox-active').each(function(){
	        if ($(this).attr('pk') == errorCodes[i].id) {
	        	itemIds.push($(this).siblings(".Asset-Id").children("a").html());
	        	messages.push(i18nProp(errorCodes[i].errorCode));
	        }
	    });
	}
	messageBarForItemId(itemIds, messages);
}

function messageBarForMessage(messages) {
	$("#messageBar").messageBar({
        responseMessage : messages,
        // itemId is just useful for execute that have id.
        // such as: create/update/delete
        itemId : [],
        // top is length between the message bar and window top-board.
        top : 100,
        // height of message board
        height : 26,
        // width of message board
        width : 610,
        // isAutoHide used to define whether auto hide in system default time.
        isAutoHide : true,
        // autoHideTime redefine the system default time for hide.
        autoHideTime : "5000",
        // preposition id.
        isPrepositionId : true
    });
}

function showMessageBarForMessage (errorCode) {
	errorCodes = errorCode.split(" ");
	errorMessage = "";
	for(i=0;i<errorCodes.length;i++){
		errorMessage += "<span class='errorItmes'>";
		errorMessage += i18nProp(errorCodes[i]);
		errorMessage += "</span><br>";
	}
	var messages = [];
	messages[0] = errorMessage;
	messageBarForMessage(messages);
	$(".message_content").height(26*errorCodes.length);
	$(".message_board").css("top",100);
	window.scrollTo(0,0);
	
}
