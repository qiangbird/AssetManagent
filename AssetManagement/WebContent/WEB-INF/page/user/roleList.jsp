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
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="css/common/tip-green/tip-green.css" type="text/css">
<link rel="stylesheet" href="css/user/roleList.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../common/header.jsp" />
<div id="bodyMinHight">
 <div class="roleListContent">
     <div class="roleAddContent">
        <div id="autoText">
            <input id="employeeName" name="employeeName" type="text" value="" />
        </div>
        <div id="checkboxOperate">
        	<input type="hidden" id="itValue" value="false" >
        	<input type="hidden" id="adminValue" value="false" />
            <a id="it" class="roleCheckBoxOff" ><span><spring:message code="user.IT" /></span></a>
    		<a id="admin" class="roleCheckBoxOff" ><span><spring:message code="user.system.admin" /></span></a>
        </div>
        <div class="addOperateButton">
            <input type="button" value=<spring:message code="add" />  id="addButton"></input>
            <input type="button" value=<spring:message code="reset" />  id="resetButton"></input> 
        </div>
      </div>
	  <div class="employeeRoleInfoTemplate">
		 <div class="employeeRoleInfo" >
		 	 <input type="hidden" id ="isNew" value="" />
		     <div class="columnData sequenceElement"><span id="sequence"></span></div>
		     <div class="columnData employeeInfoElement"><span class="employeeIdInRow"></span></div>
		     <div class="columnData employeeInfoElement"><span class="employeeNameInRow"></span></div>
		     <div class="columnData operateCheckbox itInRow">
		         <a id="itInRow" class="roleCheckBoxInRowOff" ></a>
		         <a class="underLine"></a>
		         <input type="hidden" id="itInRowValue" value="">
		         <input type="hidden" id="itInRowOriginalValue" value="">
		     </div>
		     <div class="columnData operateCheckbox adminInRow">
		         <a id="adminInRow" class="roleCheckBoxInRowOff" ></a>
		         <a class="underLine"></a>
		         <input type="hidden" id="adminInRowValue" value="">
		         <input type="hidden" id="adminInRowriginalValue" value="">
		     </div>
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
	          <div class="columnElement operateElement"><spring:message code="user.IT" /></div>
	          <div class="columnElement operateElement"><spring:message code="user.system.admin" /></div>
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
<script type="text/javascript" src="js/user/userInfoTips.js"></script>
<script type="text/javascript" src="js/user/roleList.js"></script>
<script type="text/javascript" src="js/common/autocomplete.js"></script>
<script type="text/javascript" src="jquery.poshytip/js/jquery.poshytip.js"></script>
</body>
</html>