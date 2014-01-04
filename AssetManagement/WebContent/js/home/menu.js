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
        url: "home/getTimeOffset",
        data: "timeOffset=" + timeOffset
    });
    
    // Get current browser language
    $.ajax({
        dataType: 'json',
        contentType: 'application/json',
        type: "POST",
        url: "home/getLanguage"
    });
    
    // about i18n
    $("#goChinese").click(function() {
       $.ajax({
          type : 'GET',
          contentType : 'application/json',
          url : 'home/changeLanguage?newlanguage=zh',
          dataType : 'json',
          success : function(data) {
             location.reload();
          },
          error : function() {
             alert("change language error");
          }
       });
    });

    $("#goEnglish").click(function() {
       $.ajax({
          type : 'GET',
          contentType : 'application/json',
          url : 'home/changeLanguage?newlanguage=en',
          dataType : 'json',
          success : function(data) {
             location.reload();
          },
          error : function() {
             alert("change language error");
          }
       });
    });
}); 