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
<link rel="stylesheet" href="css/common/jquery-ui.css" type="text/css">
<link rel="stylesheet" href="autocomplete/css/autocomplete.css">
<link rel="stylesheet" type="text/css" href="css/customize/customizedView/createCustomizedView.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<jsp:include page="../../common/header.jsp" />
<div id="blank">
   <a href="home"><spring:message code="navigator.home"></spring:message></a>
   <b>&gt;</b>
   <a class="allAssetPage" href="asset/allAssets"><spring:message code="navigator.asset.list"></spring:message></a>
   <b class="allAssetPage">&gt;</b>
   <a class="customerAssetPage" href="customerAsset/listAllCustomerAssets"><spring:message code="navigator.customser.assets"></spring:message></a>
   <b class="customerAssetPage">&gt;</b>
   <a class="transferLogPage" href="transferLog/list"><spring:message code="navigator.transfer.log"></spring:message></a>
   <b class="transferLogPage">&gt;</b>
   <a class="operationLogPage" href="operationLog/list"><spring:message code="navigator.operation.log"></spring:message></a>
   <b class="operationLogPage">&gt;</b>
   <span><spring:message code="createView"/></span>
</div>

<form name="newView" action="customizedView/newCustomizedView" method="post">
	<input type="hidden" id="categoryType" name="categoryType" value="${customizedView.categoryType}">
	<input type="hidden" id="prePage" name="prePage" value="${prePage}">
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
<div id="bodyMinHight">
 <div class="customizedView">
 	<div class="viewName">
          <span class="viewNameText"><spring:message code="customized.view.name"/>:</span>
          <input id="viewName" class="inTextForViewName" type="text" 
               value="${customizedView.viewName}" placeholder=<spring:message code="customized.view.name.tip.message"/> />
    </div>
    <div id="addView">
    <div class="filter">
    <span class="columnNameText"><spring:message code="customized.view.advanced.filtering"/>:</span>
    <div class="customizedViewItem">
          <input id="columnName" class="inText select-type" type="text" value="" placeholder=<spring:message code="customized.view.column.tip.message"/> />
          <input id="columnType" type="hidden" value="" />
          <input id="searchColumn" type="hidden" value="" />
          <input id="realTable" type="hidden" value="" />
    </div>
    <div class="customizedViewItem marginLeft searchCondition">
          <input id="searchCondition" class="inText select-type" type="text"  readonly="readonly"
               value=""  placeholder=<spring:message code="customized.view.criteria.tip.message"/> />
    </div>
    <div class="customizedViewItem marginLeft value">
          <input id="value" class="inText valueInput" type="text" value="" placeholder=<spring:message code="customized.view.value.tip.message"/> />
          <input id="datepic" class="inText datepic" type="text"  readonly="readonly"
               value="" placeholder=placeholder=<spring:message code="customized.view.date.tip.message"/> />
    </div>
    <div class="addToFilter marginLeft" >
          <input id="addToFilter" type="button" value=<spring:message code="customized.view.add.to.filter"/> class="addToFilter-button" />
          <!-- <input id="updateToFilter" type="button" value=<spring:message code="update"/> class="addToFilter-button" /> -->
    </div>
    </div>
    <br>
    <br>
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
      <div class="columnElement valueTitle" ><spring:message code="navigator.operation"/></div>
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
        <div class="columnData criteriaTitle criteriaInColumn">
	        <input class="inText select-type eidtCriteriaInput" type="text" readonly="readonly">
	        <p>${customizedViewItem.searchCondition }</p>
	    </div>
        <div class="columnData valueTitle valueInColumn">
            <input class="inText eidtValueInput" type="text" >
            <input id="datepic" class="inText editDatepic" type="text"  readonly="readonly"
               value="" />
            <p>${customizedViewItem.value}</p>
        </div>
        <div class="columnData deleteButton"><p class="deletePosition"><a class="deleteLink"><%-- <img src="<%=basePath%>/image/customize/customizedView/icon_delete_normal.png"> --%></a></p></div>
        <div class="columnData editButton"><p class="editPosition"><a class="eidtLink"><%-- <img src="<%=basePath%>/image/customize/customizedView/icon_edit_normal.png"> --%></a></p></div>
      </div>
    </c:forEach>
  </div>
  
  <div id="filterInfoTemplate">
      <div class="filterInfo">
        <div class="columnData sequence itemIdInColumn"><p>
        <input type="hidden" id="itemId" class='itemId' value="" />
        <input type="hidden" id="isDelete" class='isDelete' value="no" />
        <input type="hidden" class='isEdit' value="no" /></p></div>
        <div class="columnData columnNameTitle nameInColumn"><p></p></div>
        <div class="columnData columnNameTitle typeInColumn"><p></p></div>
        <div class="columnData columnNameTitle searchColumnInColumn"><p></p></div>
        <div class="columnData columnNameTitle realTableInColumn"><p></p></div>
        <div class="columnData criteriaTitle criteriaInColumn">
	        <input class="inText select-type eidtCriteriaInput" type="text" readonly="readonly">
	        <p></p>
	    </div>
        <div class="columnData valueTitle valueInColumn">
            <input class="inText eidtValueInput" type="text" >
            <input id="datepic" class="inText editDatepic" type="text"  readonly="readonly"
               value="" />
            <p></p>
        </div>
        <div class="columnData deleteButton"><p class="deletePosition"><a class="deleteLink"></a></p></div>
        <div class="columnData editButton"><p class="editPosition"><a class="eidtLink"></a></p></div>
      </div>
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
 </div>
 <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
 <script type="text/javascript" src="js/common/validation.js"></script>
 <script type="text/javascript" src="js/customized/customizedView/createCustomizedView.js"></script>
 <script type="text/javascript" src="js/common/validate.js"></script>
 </body>
 </html>
 