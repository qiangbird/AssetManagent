function tooltips(toolFlag){
    $(toolFlag).poshytip({
        className: 'tip-green',
        allowTipHover: true,
        content: function(updateCallback){
            var nameTip = $(this).text();
            setTimeout(function(){
            	$.ajax({
            		url: "user/getUserInfoTips",
                    data: {"employeeName":nameTip},
                    success: function(data){
                    	var parent = $("#displayUserInfoTips");
                    	parent.find("#employeeIdTips").text(data.employeeEmployeeId);
                    	parent.find("#employeeNameTips").text(data.employeeName);
                    	parent.find("#positionNameTips").text(data.positionNameEn);
                    	parent.find("#departmentNameTips").text(data.departmentNameEn);
                    	parent.find("#managerNameTips").text(data.managerName);
                    	var contents = parent.html();
                    	updateCallback(contents);
                    },
                    dataType: "json",
                    type: "POST"
                });
            }, 1000);
            return "<font color='black'>loading</font>";
        }
        
    });
}