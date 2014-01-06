<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
	  <div id="displayUserInfoTips">
	  	<div>
	  	 <span class="tipsTitle">Employee Id:</span>
	  	 <span id="employeeIdTips" class="tipsContent"></span>
	  	</div><div>
	  	 <span class="tipsTitle">Employee Name:</span>
	  	 <span id="employeeNameTips" class="tipsContent"></span>
	  	</div><div>
	  	 <span class="tipsTitle">Position:</span>
	  	 <span id="positionNameTips" class="tipsContent"></span>
	  	</div><div>
	  	 <span class="tipsTitle">Department:</span>
	  	 <span id="departmentNameTips" class="tipsContent"></span>
	  	</div><div>
	  	 <span class="tipsTitle">Report Manager:</span>
	  	 <span id="managerNameTips" class="tipsContent"></span>
	  	</div>
	  </div>
</body>
</html>