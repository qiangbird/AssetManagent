<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
        + request.getServerName() + ":" + request.getServerPort()
        + path + "/"; 
%>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Location Management</title>
<link rel="stylesheet" type="text/css" href="css/asset/locationList.css">
<link rel="stylesheet" type="text/css" href="css/common/filterBox.css">
<!-- <link rel="stylesheet" type="text/css" href="css/common/dataList.css"> -->
<link rel="stylesheet" type="text/css" href="css/common/search.css">
<link rel="stylesheet" type="text/css" href="css/common/jquery-ui.css">
<link rel="stylesheet" href="css/asset/locationManagement.css" type="text/css">
<script type="text/javascript" src="js/common/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/common/jquery-ui-1.8.18.custom.min.js"></script>

</head>
<body>
<jsp:include page="../home/head.jsp"></jsp:include>


 <div class="locationListContent">
     <div class="roleAddContent">
        <span id="autoText">
            <input id="employeeName" name="employeeName" type="text"/>
        </span>
        <span id="operateButton">
            <input type="button" value="Add" id="addButton"></input>
            <input type="button" value="Save" ng-click="saveEmployeeRole()" id="saveButton"></input>
            <input type="button" value="Reset" ng-click="resetEmployeeRole()" id="resetButton"></input> 
        </span>
      </div>
      <div class="rowHead">
          <div id="showError"></div>
          <span class="columnElement sequenceElement">ID</span>
          <span class="columnElement employeeInfoElement">Name</span>
     </div>
     
     <div ng-controller="rowContentCtrl">
         <div class="employeeRoleInfo">
     <c:forEach items="${assetList }" var="asset" >
     <div class="items">
            <span class="columnData employeeInfoElement">${asset.id }</span>
            <span class="columnData employeeInfoElement">${asset.assetName }</span>
          <span class="columnData employeeInfoElement">
          <a href="asset/edit/${asset.id }">Edit</a>/
          <a href="asset/copy/${asset.id }">Copy</a>/
          <a href="asset/view/${asset.id }">View Detail</a>
          </span>
          </div>
          </c:forEach>
        </div>
   </div>
</div>






<script type="text/javascript" src="js/location/location.js"></script>
</body>
</html>