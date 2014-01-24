<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/customize/customizedView/createCustomizedView.css" />
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../../common/header.jsp" />
<form name="newView" action="customizedView/newCustomizedView" method="post">
	<input type="hidden" name="customizedViewId" value="${customizedView.id}">
	<input type="hidden" name="viewName" id="formViewName">
	<input type="hidden" name="columns" id="formColumns">
	<input type="hidden" name="columnTypes" id="formColumnTypes">
	<input type="hidden" name="searchConditions" id="formSearchConditions">
	<input type="hidden" name="values" id="formValues">
	<input type="hidden" name="customizedViewItemIds" id="customizedViewItemIds">
	<input type="hidden" name="isDeletes" id="formIsDeletes">
	<input type="hidden" name="operator" id="operator" value="and">
	<input type="hidden" name="searchColumns" id="searchColumns">
	<input type="hidden" name="realTables" id="formRealTables">
</form>
 <div class="customizedView">
 	<div class="viewName">
          <span class="viewNameText"><spring:message code="customized.view.name"/>:</span>
          <input id="viewName" class="inTextForViewName" type="text" 
               value="${customizedView.viewName}" placeholder=<spring:message code="customized.view.name.tip.message"/> />
          <div class="error-box-viewName">
          	<div class="error-left-viewName"></div>
          	<div class="error-middle-viewName"></div>
          	<div class="error-right-viewName"></div>
          </div>
          <span id="viewNameImg"></span>
    </div>
    <div id="filterContent">
    <span class="filterSetText"> <spring:message code="customized.view.filter.set"/>:</span>
    <div class="radioBoxes">
    	<span class="match-any"><spring:message code="customized.view.and"/></span><a class="radioCheckOff" name="or"></a>
    	<span class="match-all"><spring:message code="customized.view.or"/></span><a class="radioCheckOn" name="and"></a>
    </div><br>
    <div class="filterHead">
      <div class="columnElement columnNameTitleHead" ><spring:message code="customized.view.column.name"/></div>
      <div class="columnElement criteriaTitle" ><spring:message code="customized.view.criteria"/></div>
      <div class="columnElement valueTitle" ><spring:message code="customized.view.value"/></div>
    </div>
	<c:forEach items="${customizedViewItems}" var="customizedViewItem">
      <div class="filterInfo">
        <div class="columnData sequence itemIdInColumn"><p>
        <input type="hidden" id="itemId" class='itemId' value="${customizedViewItem.id }" />
        <input type="hidden" id="isDelete" class='isDelete' value="no" />
        <input type="hidden" class='isEdit' value="no" /></p></div>
        <div class="columnData columnNameTitle nameInColumn"><p>${customizedViewItem.columnName }</p></div>
        <div class="columnData columnNameTitle typeInColumn"><p>${customizedViewItem.columnType }</p></div>
        <div class="columnData columnNameTitle searchColumnInColumn"><p>${customizedViewItem.searchColumn }</p></div>
        <div class="columnData columnNameTitle realTableInColumn"><p>${customizedViewItem.realTable }</p></div>
        <div class="columnData criteriaTitle criteriaInColumn"><p>${customizedViewItem.searchCondition }</p></div>
        <div class="columnData valueTitle valueInColumn"><p><p>${customizedViewItem.value}</p></div>
        <div class="columnData deleteButton"><p class="deletePosition"><a class="deleteLink"><img src="<%=basePath%>/image/customize/customizedView/icon_delete_normal.png"></a></p></div>
        <div class="columnData editButton"><p class="editPosition"><a class="eidtLink"><img src="<%=basePath%>/image/customize/customizedView/icon_edit_normal.png"></a></p></div>
      </div>
    </c:forEach>
  </div>
  
  <div id="addView">
  <br>
  <br>
  
    <div class="filter">
    <span class="columnNameText"><spring:message code="customized.view.advanced.filtering"/>:</span>
    <div class="customizedViewItem">
          <input id="columnName" class="inText select-type" type="text"  readonly="readonly"
               value="" />
          <input id="columnType" type="hidden" value="" />
          <input id="searchColumn" type="hidden" value="" />
          <input id="realTable" type="hidden" value="" />
    </div>
    <div class="customizedViewItem marginLeft searchCondition">
          <input id="searchCondition" class="inText select-type" type="text"  readonly="readonly"
               value=""  />
    </div>
    <div class="customizedViewItem marginLeft value">
          <input id="value" class="inText select-type" type="text"  readonly="readonly"
               value=""/>
          <input id="datepic" class="inText datepic" type="text"  readonly="readonly"
               value="" placeholder="Select date"/>
    </div>
    <div class="addToFilter marginLeft" >
          <input id="addToFilter" type="button" value=<spring:message code="customized.view.add.to.filter"/> class="addToFilter-button" />
          <input id="updateToFilter" type="button" value=<spring:message code="update"/> class="addToFilter-button" />
    </div>
    </div>
    <br>
    <br>
    </div>
    <div class="saveOperation" >
  	<c:choose>  
	   <c:when test="${customizedView.viewName != null}">
	   	<input id="update" type="button" value=<spring:message code="update"/>  class="submit-save" />
	   </c:when>  
	   <c:otherwise>
	   	<input id="save" type="button" value=<spring:message code="save"/>  class="submit-save" />
	   </c:otherwise>  
	</c:choose>  
  	<input id="cancel" type="button" value=<spring:message code="cancel"/>  class="submit-cancel" />
  </div>
 </div>
 <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
 <script type="text/javascript" src="js/customize/customizedView/createCustomizedView.js"></script>
 <script type="text/javascript" src="js/common/validate.js"></script>
 </body>
 </html>
 