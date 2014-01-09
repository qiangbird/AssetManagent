<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/customize/customizedView/newCustomizedView.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body>
<jsp:include page="../../common/header.jsp" />
<div class="customizedView">
    <div class="create-new-button">
        <input id="newView" type="button" value="Create" class="create-new" />
    </div>
    <div id="list-cancel">
  	<input id="cancel" type="button" value="Cancel"  class="list-cancel" />
    </div>
    <div id="filterContent">
    <span class="filterSetText"><spring:message code="customized.view"></spring:message></span><br>
    <div class="filterHead">
      <div class="columnElement viewNameTitleHead" ><spring:message code="customized.view.name"></spring:message></div>
      <div class="columnElement criteriaTitle" ><spring:message code="customized.created.by"></spring:message></div>
    </div>
    <c:forEach items="${customizedViews}" var="customizedView">
      <div class="filterInfo">
        <div class="columnData sequence"><p title="sequence"></p></div>
        <input type="hidden" id="id" value="${customizedView.id}"/>
        <div class="columnData viewNameTitle"><p>${customizedView.viewName}</p></div>
        <div class="columnData criteriaTitle"><p>${customizedView.creatorName}</p></div>
        <div class="columnData deleteButton"><p class="deletePosition"><a class="deleteLink"><img src="<%=basePath%>/image/customize/customizedView/icon_delete_normal.png"></a></p></div>
        <div class="columnData editButton"><p class="editPosition"><a class="eidtLink"><img src="<%=basePath%>/image/customize/customizedView/icon_edit_normal.png"></a></p></div>
      </div>
    </c:forEach>
  </div>
  </div>
  <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
  <script type="text/javascript" src="js/customize/customizedView/customizedViewList.js"></script>
   </body>
 </html>