<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<html>
<head>
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="css/customize/customizedView/newCustomizedView.css" />
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
          <span class="viewNameText">View Name:</span>
          <input id="viewName" class="inTextForViewName" type="text" 
               value="${customizedView.viewName}" placeholder="Please input the view name" />
          <div class="error-box-viewName">
          	<div class="error-left-viewName"></div>
          	<div class="error-middle-viewName"></div>
          	<div class="error-right-viewName"></div>
          </div>
          <span id="viewNameImg"></span>
    </div>
    <div id="filterContent">
    <span class="filterSetText"> Filter set:</span>
    <div class="radioBoxes">
    	<span class="match-any">Match ANY of the followling (OR)</span><a class="radioCheckOff" name="or"></a>
    	<span class="match-all">Match ALL of the followling (AND)</span><a class="radioCheckOn" name="and"></a>
    </div><br>
    <div class="filterHead">
      <div class="columnElement columnNameTitleHead" >Column Name</div>
      <div class="columnElement criteriaTitle" >Criteria</div>
      <div class="columnElement valueTitle" >Value</div>
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
        <%-- <div class="valueInputHidden"><input type="text" id="valueInput" class="valueInput" value="${customizedViewItem.value}" /></div> --%>
        <!-- <div id="error-box-valueInput" class="error-box-valueInput">
          	<div class="error-left-valueInput"></div>
          	<div class="error-middle-valueInput"></div>
          	<div class="error-right-valueInput"></div>
          </div> -->
        <!-- <span id="valueInputImg"></span> </p></div>-->
        <div class="columnData deleteButton"><p class="deletePosition"><a class="deleteLink"><img src="<%=basePath%>/image/customize/customizedView/icon_delete_normal.png"></a></p></div>
        <div class="columnData editButton"><p class="editPosition"><a class="eidtLink"><img src="<%=basePath%>/image/customize/customizedView/icon_edit_normal.png"></a></p></div>
      </div>
    </c:forEach>
  </div>
  
  <div id="addView">
  <br>
  <br>
  
    <div class="filter">
    <span class="columnNameText">Advanced filtering:</span>
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
          <input id="addToFilter" type="button" value="Add to Filter" class="addToFilter-button" />
          <input id="updateToFilter" type="button" value="Update" class="addToFilter-button" />
    </div>
    </div>
    <br>
    <br>
    </div>
    <div class="saveOperation" >
  	<c:choose>  
	   <c:when test="${customizedView.viewName != null}">
	   	<input id="update" type="button" value="Update"  class="submit-save" />
	   </c:when>  
	   <c:otherwise>
	   	<input id="save" type="button" value="Save"  class="submit-save" />
	   </c:otherwise>  
	</c:choose>  
  	<input id="cancel" type="button" value="Cancel"  class="submit-cancel" />
  </div>
 </div>
 <jsp:include page="/WEB-INF/page/common/footer.jsp"></jsp:include>
 <script type="text/javascript" src="js/customize/customizedView/newCustomizedView.js"></script>
 <script type="text/javascript" src="js/common/validate.js"></script>
 </body>
 </html>
 