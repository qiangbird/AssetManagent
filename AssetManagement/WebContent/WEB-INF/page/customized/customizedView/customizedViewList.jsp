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
<link rel="stylesheet" type="text/css" href="css/customize/customizedView/createCustomizedView.css" />
<link rel="stylesheet" type="text/css" href="css/customize/customizedView/customizedViewList.css" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

</head>
<body>
<jsp:include page="../../common/header.jsp" />
<input type="hidden" id="categoryType" name="categoryType" value="${customizedView.categoryType}">
<input type="hidden" id="prePage" name="prePage" value="${prePage}">
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
   <span><spring:message code="manageView"/></span>
</div>
<div id="bodyMinHight">
<div class="customizedView">
    <div class="create-new-button">
        <input id="newView" type="button" value=<spring:message code="create" /> class="create-new" />
    </div>
    <div id="list-cancel">
  	<input id="cancel" type="button" value=<spring:message code="cancel" />  class="list-cancel" />
    </div>
    <div id="filterContent">
    <span class="filterSetText"><spring:message code="customized.view"></spring:message></span><br>
    <div class="filterHead">
      <div class="columnElement viewNameTitleHead" ><spring:message code="customized.view.name"></spring:message></div>
      <div class="columnElement criteriaTitle" ><spring:message code="customized.created.by"></spring:message></div>
      <div class="columnElement operation" ><spring:message code="operation"></spring:message></div>
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
  </div>

  <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
  <script type="text/javascript" src="js/customized/customizedView/customizedViewList.js"></script>
   </body>
 </html>