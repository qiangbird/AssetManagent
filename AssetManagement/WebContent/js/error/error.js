$(document).ready(function(){
    
    var path = $("#path").val();
    
    $(".error_click_home").click(function(){
        
        window.location.href = path + "todo/redirectDashboard";
    });
});