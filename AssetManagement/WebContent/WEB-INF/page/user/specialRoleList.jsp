<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" href="css/common/autocomplete.css" type="text/css">
<link rel="stylesheet" href="css/user/roleList.css" type="text/css">
<link rel="stylesheet" href="css/user/specialRoleList.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="blank">
   <a href="home"><spring:message code="navigator.home"></spring:message></a>
   <b>&gt;</b>
   <a href="user/roleList"><spring:message code="navigator.system"></spring:message></a>
   <b>&gt;</b>
   <span><spring:message code="navigator.assign.special.role"></spring:message></span>
 </div>
<div id="bodyMinHight">
 <div class="roleListContent">
     <div class="roleAddContent">
     	<div class="customerName">
     		<label class="shortLeftText">
                <span class="propertyRequired">*</span>
                <span class="propertyText"><spring:message code="customer" /></span>
            </label>
            <input id="customerCode"  type="text" value="" placeholder="Please choose the customer"/>
            <input id="customerName" type="hidden" value="" />
        </div>
        <div id="autoText">
        	<label class="shortLeftText">
                <span class="propertyRequired">*</span>
                <span class="propertyText"><spring:message code="employee" /></span>
            </label>
            <input id="employeeName" name="employeeName" type="text" value="" readonly="readonly" />
        </div>
        <div class="addOperateButton">
            <input type="button" value=<spring:message code="add" />  id="addButton"></input>
        </div>
      </div>
	  <div class="employeeRoleInfoTemplate">
		 <div class="employeeRoleInfo" >
		 	 <input type="hidden" id ="isNew" value="" />
		     <div class="columnData sequenceElement"><span id="sequence"></span></div>
		     <div class="columnData employeeInfoElement"><span class="employeeIdInRow"></span></div>
		     <div class="columnData employeeInfoElement"><span class="employeeNameInRow"></span></div>
		     <div class="columnData departmentElement"><span class="departmentInRow"></span></div>
		     <div class="columnData customerElement"><span class="customerNameInRow"></span></div>
		     <div class="columnData managerElement"><span class="managerInRow"></span></div>
		     <div class="columnData removeElement">
		          <span class="deleteLink"></span>
		     </div>    
		 </div>
	  </div>
      <div class="roleDispaly">
	      <div class="rowHead" >
	          <div id="showError"></div>
	          <div class="columnElement sequenceElement"><spring:message code="sequence" /></div>
	          <div class="columnElement employeeInfoElement"><spring:message code="user.employeeId" /></div>
	          <div class="columnElement employeeInfoElement"><spring:message code="user.employeeName" /></div>
	          <div class="columnElement departmentElement"><spring:message code="user.department" /></div>
	          <div class="columnElement customerElement"><spring:message code="customer" /></div>
	          <div class="columnElement managerElement"><spring:message code="user.manager" /></div>
	          <div class="columnElement operateElement"><spring:message code="remove" /></div>    
	       </div>
	  </div>
	  
	  <div class="saveOperateButton">
            <input type="button" value=<spring:message code="save" />  id="saveButton"></input>
            <input type="button" value=<spring:message code="cancel" />  id="cancelButton"></input> 
        </div>
	  
	  <jsp:include page="userInfoTips.jsp" />
</div>
</div>
<jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>

<script type="text/javascript" src="js/common/validation.js"></script>
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="js/user/specialRoleList.js"></script>
</body>
</html>