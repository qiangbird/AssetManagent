$(document).ready(function(){
	$(".menuLi,.headerNavigationEnd").mouseover(function() {
		$(this).find("ul").show();
	}).mouseout(function() {
		$(this).find("ul").hide();
	});
	
	// Get timeOffset between current client date time and UTC time, return string 
    var getTimeOffset = function() {
        var timeOffset = new Date().getTimezoneOffset();
        var hour = parseInt(timeOffset / 60);
        var munite = timeOffset % 60;
        var prefix = "-";
        if (hour < 0 || munite < 0) {
            prefix = "";
            hour = -hour;
            if (munite < 0) {
                munite = -munite;
            }
        }
        hour += ",";
        return prefix + hour + prefix + munite;
    };
    var timeOffset = getTimeOffset();
    
    // When load page(head.jsp), timeOffset will be transfered to background and saved in session
    $.ajax({
    	dataType: 'json',
        type: "POST",
        url: "getTimeOffset",
        data: "timeOffset=" + timeOffset
    });
    
    // when user login, judge if userCustomColumns is null. If yes, initialize column.
    $.ajax({
    	dataType: 'json',
        type: "POST",
        url: "initUserCustomColumn"
    });
    
    $(".i18n-set-step").click(function(){
        var locale = $(this).attr("name");
         $.ajax({
             url: $("#basePath").val() + "changeLocale?locale=" + locale,
             success: function(){
                 window.location.reload(true);
             }
         });
    });
}); 
